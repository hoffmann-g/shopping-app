package com.hoffmann_g.order_service.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoffmann_g.order_service.config.CartService;
import com.hoffmann_g.order_service.config.CouponService;
import com.hoffmann_g.order_service.config.ProductService;
import com.hoffmann_g.order_service.config.StockService;
import com.hoffmann_g.order_service.controllers.exceptions.EmptyCartException;
import com.hoffmann_g.order_service.controllers.exceptions.ItemOutOfStockException;
import com.hoffmann_g.order_service.controllers.exceptions.ResourceNotFoundException;
import com.hoffmann_g.order_service.dtos.OrderRequest;
import com.hoffmann_g.order_service.dtos.OrderResponse;
import com.hoffmann_g.order_service.entities.Order;
import com.hoffmann_g.order_service.entities.OrderItem;
import com.hoffmann_g.order_service.entities.enums.OrderStatus;
import com.hoffmann_g.order_service.events.OrderPlacedEvent;
import com.hoffmann_g.order_service.mappers.OrderMapper;
import com.hoffmann_g.order_service.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductService productService;
    private final CouponService couponService;
    private final StockService stockService;
    private final CartService cartService;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Transactional
    public OrderResponse placeOrder(Long userId, String userEmail, OrderRequest request) {
        Map<Long, Integer> productQuantities = cartService.getCartQuantities(userId);

        if (productQuantities.size() < 1)
            throw new EmptyCartException("Cannot place order from an empty cart");

        List<Long> availableProducts = stockService.isCartInStock(orderMapper.mapToStringMap(productQuantities));

        for (Long productId : productQuantities.keySet()) {
            if (!availableProducts.contains(productId)) {
                throw new ItemOutOfStockException("Item " + productId + " is out of stock");
            }
        }

        Map<Long, Long> prices = productService.getPrices(availableProducts);
        Long totalPrice = calculateTotalPrice(prices, productQuantities);
        String coupon = cartService.getCartCoupon(userId);

        if (coupon != null) {
            Integer discountPercentage = couponService.getCouponDiscount(coupon);
            totalPrice -= (totalPrice * discountPercentage) / 100;
        }

        Long shipmentCost = 0L;
        totalPrice += shipmentCost;
        List<OrderItem> orderItems = createOrderItems(productQuantities, prices);

        Order order = new Order(
                null,
                userId,
                OrderStatus.PENDING,
                totalPrice,
                shipmentCost,
                request.paymentType(),
                LocalDateTime.now(),
                orderItems);

        stockService.reserveStock(orderMapper.mapToStringMap(productQuantities));
        order = orderRepository.save(order);
        cartService.clearCart(userId);

        kafkaTemplate.send("paymentRequestTopic",
                new OrderPlacedEvent(null, userEmail, request.paymentType(), totalPrice));

        return orderMapper.mapToOrderResponse(order);
    }

    @Transactional
    // @KafkaListener(topics = "paymentStatusTopic")
    public void updateOrder(Map<Long, String> orderResponse) {
        Long orderId = -1L;
        String status = "";

        for (Entry<Long, String> entry : orderResponse.entrySet()) {
            orderId = entry.getKey();
            status = entry.getValue().toLowerCase();
            break;
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not get order."));

        Map<Long, Integer> orderedItemsMap = new HashMap<>();

        for (OrderItem i : order.getOrderItemList()) {
            orderedItemsMap.put(i.getProductId(), i.getQuantity());
        }

        if (status == "approved") {
            order.setOrderStatus(OrderStatus.valueOf(status));
            stockService.decreaseReservedStock(orderMapper.mapToStringMap(orderedItemsMap));
            log.info("PAYMENT APPROVED");
            log.debug("PAYMENT APPROVED");
        }

        if (status != "approved") {
            order.setOrderStatus(OrderStatus.valueOf(status));
            stockService.unreserveStock(orderMapper.mapToStringMap(orderedItemsMap));
            log.info("PAYMENT NOT APPROVED");
            log.debug("PAYMENT NOT APPROVED");
        }

        orderRepository.save(order);
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not get order."));

        return orderMapper.mapToOrderResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(x -> orderMapper.mapToOrderResponse(x)).toList();
    }

    public List<OrderResponse> getOrdersByUser(Long id) {
        return orderRepository.findByCustomerId(id).stream().map(orderMapper::mapToOrderResponse).toList();
    }

    private Long calculateTotalPrice(Map<Long, Long> prices, Map<Long, Integer> quantities) {
        Long totalAmount = 0L;

        for (Entry<Long, Long> entry : prices.entrySet()) {
            totalAmount += entry.getValue() * quantities.get(entry.getKey());
        }

        return totalAmount;
    }

    private List<OrderItem> createOrderItems(Map<Long, Integer> productQuantities, Map<Long, Long> prices) {
        return productQuantities.entrySet().stream()
                .map(entry -> new OrderItem(
                        null,
                        entry.getKey(),
                        entry.getValue(),
                        prices.get(entry.getKey()),
                        null))
                .collect(Collectors.toList());
    }

}

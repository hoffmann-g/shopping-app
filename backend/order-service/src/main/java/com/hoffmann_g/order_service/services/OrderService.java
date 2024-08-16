package com.hoffmann_g.order_service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hoffmann_g.order_service.mappers.OrderMapper;
import com.hoffmann_g.order_service.repositories.OrderRepository;

@Service
@Transactional
public class OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private StockService stockService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        // get cart items and quantities
        Map<Long, Integer> productQuantities = cartService.getCartQuantities(request.customerId());

        if (productQuantities.size() < 1) throw new EmptyCartException("Cannot place order from an empty cart"); 

        // get list of available products in stock
        List<Long> availableProducts = stockService.isCartInStock(orderMapper.mapToStringMap(productQuantities));

        // check for items that are out of stock
        for (Long productId : productQuantities.keySet()) {
            if (!availableProducts.contains(productId)) {
                throw new ItemOutOfStockException("Item " + productId + " is out of stock");
            }
        }

        // get product prices
        Map<Long, Long> prices = productService.getPrices(availableProducts);

        // calculate total price of the order
        Long totalPrice = calculateTotalPrice(prices, productQuantities);

        // get coupon if it exists
        String coupon = cartService.getCartCoupon(request.customerId());

        // if coupon exists, recalculates price
        if (coupon != null) {
            Integer discountPercentage = couponService.getCouponDiscount(coupon);
            totalPrice -= (totalPrice * discountPercentage) / 100;
        }

        // get shipment cost for the items
        Long shipmentCost = 0L;

        // get order items
        List<OrderItem> orderItems = createOrderItems(productQuantities, prices);

        // create order
        Order order = new Order(
                null,
                request.customerId(),
                OrderStatus.PENDING,
                totalPrice,
                shipmentCost,
                request.paymentType(),
                LocalDateTime.now(),
                orderItems);

        // reserve stock
        stockService.reserveStock(orderMapper.mapToStringMap(productQuantities));

        // save the order to the database
        order = orderRepository.save(order);

        // clear cart
        cartService.clearCart(request.customerId());

        // send message to payment service to process payment passing the payment info
        // --- credit/debit card: card number, cvv, name, etc, amount
        // --- pix: name, amount 

        // send email to user saying that a purchase was made

        return orderMapper.mapToOrderResponse(order);
    }

    @Transactional
    public OrderResponse updateOrder(Order request) {
        // watch for the payment server event response

        // if confirmed, set status to "CONFIRMED"

        // remove reserved items from stock

        // send email to user saying that purchase was confirmed

        // if cancelled, set status to "cancelled"

        // remove reserved items from stock

        // send email to user saying that purchase was confirmed
        
        return null;
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

    private Long calculateTotalPrice(Map<Long, Long> prices, Map<Long, Integer> quantities) {
        Long totalAmount = 0L;

        for (Entry<Long, Long> entry : prices.entrySet()){
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

    public List<OrderResponse> getOrdersByUser(Long id) {
        return orderRepository.findByCustomerId(id).stream().map(orderMapper::mapToOrderResponse).toList();
    }

}

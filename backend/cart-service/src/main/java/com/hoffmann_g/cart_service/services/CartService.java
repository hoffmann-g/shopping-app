package com.hoffmann_g.cart_service.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoffmann_g.cart_service.config.CouponService;
import com.hoffmann_g.cart_service.config.ProductService;
import com.hoffmann_g.cart_service.config.StockService;
import com.hoffmann_g.cart_service.controllers.exceptions.DatabaseMismatchException;
import com.hoffmann_g.cart_service.controllers.exceptions.InvalidArgumentException;
import com.hoffmann_g.cart_service.controllers.exceptions.ResourceNotFoundException;
import com.hoffmann_g.cart_service.dtos.CartItemRequest;
import com.hoffmann_g.cart_service.dtos.CartResponse;
import com.hoffmann_g.cart_service.entities.Cart;
import com.hoffmann_g.cart_service.entities.CartItem;
import com.hoffmann_g.cart_service.mappers.CartMapper;
import com.hoffmann_g.cart_service.repositories.CartRepository;

import feign.FeignException;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CartMapper cartMapper;

    @Transactional
    public CartResponse addItemToCart(Long customerId, CartItemRequest request) {
        validateCartItem(request);

        Cart cart = cartRepository.findByCustomerId(customerId).orElse(new Cart(customerId));

        cart.getProductList().add(cartMapper.mapToCartItem(request));

        updatePrices(cart);

        return cartMapper.mapToCartResponse(cartRepository.save(cart));
    }

    @Transactional
    public CartResponse clearCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart could not be found"));

        cart.getProductList().clear();
        cart.setTotalPrice(0L);
        cart.setDiscountedPrice(0L);
        cart.setCoupon(null);

        return cartMapper.mapToCartResponse(cartRepository.save(cart));
    }

    @Transactional
    public CartResponse updateCartItem(Long customerId, CartItemRequest request) {
        validateCartItem(request);

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart could not be found"));

        for (CartItem ci : cart.getProductList()) {
            if (ci.getProductId().equals(request.productId())) {
                ci.setQuantity(request.quantity());
                break;
            }
        }

        updatePrices(cart);

        return cartMapper.mapToCartResponse(cartRepository.save(cart));
    }

    @Transactional(readOnly = true)
    public Map<Long, Integer> getCartQuantities(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart could not be found"));

        return cart.getProductList().stream().collect(Collectors.toMap(CartItem::getProductId, CartItem::getQuantity));
    }

    @Transactional(readOnly = true)
    public String getCartCoupon(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart could not be found"));

        if (cart.getCoupon() == null)
            return null;

        return cart.getCoupon();
    }

    @Transactional
    public CartResponse applyCoupon(Long customerId, String coupon) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart could not be found"));

        validateCoupon(coupon);

        cart.setCoupon(coupon);

        updatePrices(cart);

        return cartMapper.mapToCartResponse(cartRepository.save(cart));
    }

    @Transactional(readOnly = true)
    public CartResponse getCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId).orElse(new Cart(customerId));

        updatePrices(cart);

        return cartMapper.mapToCartResponse(cartRepository.save(cart));
    }

    @Transactional
    private void updatePrices(Cart cart) {
        if (cart.getProductList().isEmpty()) {
            cart.setTotalPrice(0L);
            cart.setDiscountedPrice(0L);
            return;
        }

        List<Long> productsIds = cart.getProductList().stream().map(CartItem::getProductId).toList();
        Map<Long, Long> priceCatalog = productService.getPrices(productsIds);

        

        Long totalPrice = 0L;

        for (CartItem i : cart.getProductList()) {
            Long productPrice = priceCatalog.get(i.getProductId());

            if (productPrice == null) {
                throw new DatabaseMismatchException("Product " + i.getProductId() + " is present in stock database, but not present in product database");
            }

            Long cartItemPrice = productPrice * i.getQuantity();

            i.setPrice(cartItemPrice);
            totalPrice += cartItemPrice;
        }

        cart.setTotalPrice(totalPrice);

        if (cart.getCoupon() != null) {
            Integer discountPercentage = couponService.getCouponDiscount(cart.getCoupon());

            Long discountedPrice = totalPrice - ((totalPrice * discountPercentage) / 100);

            cart.setDiscountedPrice(discountedPrice);
            return;
        }

        cart.setDiscountedPrice(totalPrice);
    }

    private void validateCartItem(CartItemRequest request) {
        try {
            if (!stockService.isProductInStock(request.productId(), request.quantity())) {
                throw new InvalidArgumentException("This product is not available in this quantity");
            }
        } catch (FeignException badRequest) {
            String errorMessage = extractErrorMessage(badRequest);
            throw new ResourceNotFoundException(errorMessage);
        }
    }

    public void validateCoupon(String coupon) {
        try {
            couponService.getCouponDiscount(coupon);

        } catch (FeignException.NotFound notFound) {
            throw new ResourceNotFoundException("Coupon " + coupon + " could not be found");

        } catch (FeignException.BadRequest badRequest) {
            throw new ResourceNotFoundException("Coupon " + coupon + " is expired");
        }

    }

    @Transactional
    public void deleteCart(Long customerId) {
        if (!cartRepository.existsByCustomerId(customerId))
            throw new ResourceNotFoundException("Cart could not be found");

        cartRepository.deleteByCustomerId(customerId);
    }

    private String extractErrorMessage(FeignException feignException) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String responseBody = feignException.contentUTF8();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.path("message").asText("Unknown error occurred");
            
        } catch (Exception e) {
            return "Error parsing response";
        }
    }

}

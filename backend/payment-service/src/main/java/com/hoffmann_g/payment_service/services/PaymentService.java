package com.hoffmann_g.payment_service.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.hoffmann_g.payment_service.events.OrderPlacedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentService {

    @KafkaListener(topics = "paymentRequestTopic")
	public void handlePayment(OrderPlacedEvent orderPlacedEvent){
        log.info("PAYMENT APPROVED");
        log.debug("PAYMENT APPROVED");

	}
    
}

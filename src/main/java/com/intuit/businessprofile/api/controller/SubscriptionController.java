package com.intuit.businessprofile.api.controller;

import com.intuit.businessprofile.api.SubscriptionApi;
import com.intuit.businessprofile.api.fallback.SubscriptionFallback;
import com.intuit.businessprofile.dto.request.SubscriptionRequest;
import com.intuit.businessprofile.service.SubscriptionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionApi {

    private final SubscriptionService subscriptionService;
    private final SubscriptionFallback subscriptionFallback;

    @CircuitBreaker(name = "businessProfileCircuitBreakerConfig", fallbackMethod = "fallbackForSubscribe")
    @Override
    public ResponseEntity<?> subscribe(SubscriptionRequest request) {
        log.info("Request to subscribe to a business-profile :: {}", request);
        subscriptionService.createSubscription(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getSubscription(String businessProfileId) {
        return new ResponseEntity<>(subscriptionService.getSubscription(businessProfileId), HttpStatus.OK);
    }

    @CircuitBreaker(name = "businessProfileCircuitBreakerConfig", fallbackMethod = "fallbackForUnsubscribe")
    @Override
    public ResponseEntity<?> unsubscribe(String businessProfileId,
                                         String externalProductId) {
        subscriptionService.removeSubscription(businessProfileId, externalProductId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

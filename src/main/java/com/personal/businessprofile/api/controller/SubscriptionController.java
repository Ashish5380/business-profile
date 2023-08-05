package com.personal.businessprofile.api.controller;

import com.personal.businessprofile.api.SubscriptionApi;
import com.personal.businessprofile.api.fallback.SubscriptionFallback;
import com.personal.businessprofile.dto.request.SubscriptionRequest;
import com.personal.businessprofile.dto.response.SubscriptionResponse;
import com.personal.businessprofile.mapper.SubscriptionMapper;
import com.personal.businessprofile.service.SubscriptionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<SubscriptionResponse> responseList = subscriptionService
          .getSubscription(businessProfileId).stream().map(SubscriptionMapper::mapToResponse).toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @CircuitBreaker(name = "businessProfileCircuitBreakerConfig", fallbackMethod = "fallbackForUnsubscribe")
    @Override
    public ResponseEntity<?> unsubscribe(String businessProfileId,
                                         String externalProductId) {
        subscriptionService.removeSubscription(businessProfileId, externalProductId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

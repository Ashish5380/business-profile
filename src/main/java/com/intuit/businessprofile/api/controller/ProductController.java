package com.intuit.businessprofile.api.controller;

import com.intuit.businessprofile.api.ProductApi;
import com.intuit.businessprofile.api.fallback.ProductFallback;
import com.intuit.businessprofile.dto.request.ProductRequest;
import com.intuit.businessprofile.mapper.ProductMapper;
import com.intuit.businessprofile.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;
    private final ProductFallback productFallback;

    @CircuitBreaker(name = "businessProfileCircuitBreakerConfig", fallbackMethod = "fallbackForCreateProduct")
    @Override
    public ResponseEntity<?> createProduct(final ProductRequest request) {
        log.info("Request for creating product :: {}", request);
        return new ResponseEntity<>(ProductMapper.mapToResponse(productService.createProduct(request)),
          HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<?> getProducts(final String externalProductId) {
        log.info("Request for fetching product with externalProductId :: {}", externalProductId);
        return new ResponseEntity<>(ProductMapper.mapToResponse(productService.getProduct(externalProductId)),
          HttpStatus.OK);
    }


}

package com.intuit.businessprofile.api;

import com.intuit.businessprofile.dto.request.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/product")
public interface ProductApi {
    @PostMapping("/create")
    ResponseEntity<?> createProduct(@RequestBody ProductRequest request);

    @GetMapping("/{externalProductId}")
    ResponseEntity<?> getProducts(@PathVariable("externalProductId") String externalProductId);
}

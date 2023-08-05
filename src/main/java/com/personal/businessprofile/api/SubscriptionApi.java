package com.personal.businessprofile.api;


import com.personal.businessprofile.dto.request.SubscriptionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/subscription")
public interface SubscriptionApi {

    @PostMapping("/create")
    ResponseEntity<?> subscribe(@RequestBody SubscriptionRequest request);

    @GetMapping("/{businessProfileId}")
    ResponseEntity<?> getSubscription(@PathVariable("businessProfileId") String businessProfileId);

    @PutMapping("/edit")
    ResponseEntity<?> unsubscribe(@RequestParam("businessProfileId") String businessProfileId,
                                  @RequestParam("externalProductId") String externalProductId);
}

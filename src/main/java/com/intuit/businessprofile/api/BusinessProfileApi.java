package com.intuit.businessprofile.api;

import com.intuit.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.intuit.businessprofile.dto.request.BusinessProfileUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/business-profile")
public interface BusinessProfileApi {

    @PostMapping("/create")
    ResponseEntity<?> createBusinessProfile(@RequestBody BusinessProfileCreateRequest request);

    @GetMapping("/{id}")
    ResponseEntity<?> getBusinessProfile(@PathVariable("id") String id);

    @GetMapping("/{businessProfileId}")
    ResponseEntity<?> getProfileForProduct(@PathVariable("businessProfileId") String businessProfileId,
                                           @RequestParam("externalProductId") String externalProductId);

    @PutMapping("/{businessProfileId}")
    ResponseEntity<?> updateBusinessProfile(@PathVariable("businessProfileId") String businessProfileId,
                                            @RequestParam("externalProductId") String externalProductId,
                                            @RequestBody BusinessProfileUpdateRequest request);

    @GetMapping("/all")
    ResponseEntity<?> getAllBusinessProfiles();

    @PutMapping("/profile/{businessProfileId}")
    ResponseEntity<?> updateBusinessProfile(@PathVariable("businessProfileId") String businessProfileId,
                                            @RequestBody BusinessProfileUpdateRequest request);
}

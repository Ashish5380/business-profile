package com.personal.businessprofile.api.controller;

import com.personal.businessprofile.api.BusinessProfileApi;
import com.personal.businessprofile.api.fallback.BusinessProfileFallback;
import com.personal.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.personal.businessprofile.dto.request.BusinessProfileUpdateRequest;
import com.personal.businessprofile.dto.response.BusinessProfileResponse;
import com.personal.businessprofile.mapper.BusinessProfileMapper;
import com.personal.businessprofile.mapper.BusinessProfileRevisionMapper;
import com.personal.businessprofile.service.BusinessProfileRevisionManager;
import com.personal.businessprofile.service.BusinessProfileService;
import com.personal.businessprofile.validator.domian.BusinessProfileValidator;
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
public class BusinessProfileController implements BusinessProfileApi {

    private final BusinessProfileService businessProfile;

    private final BusinessProfileRevisionManager businessProfileRevisionManager;
    private final BusinessProfileFallback fallback;

    @CircuitBreaker(name = "businessProfileCircuitBreakerConfig", fallbackMethod = "fallbackForCreateBusinessProfile")
    @Override
    public ResponseEntity<?> createBusinessProfile(BusinessProfileCreateRequest request) {
        BusinessProfileValidator.validateBusinessProfile(request);
        log.info("Received request for business-profile creation :: {}", request);
        return new ResponseEntity<>(BusinessProfileMapper
          .mapToResponse(businessProfileRevisionManager.createBusinessProfile(request)), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getBusinessProfile(String id) {
        log.info("Request to get business-profile info for id :: {}", id);
        return new ResponseEntity<>(businessProfile.fetchBusinessProfile(id), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getProfileForProduct(String businessProfileId,
                                                  String externalProductId) {
        log.info("Request for getting business-profile for productId :: {}, and business-profile with id :: {}",
          externalProductId, businessProfileId);
        return new ResponseEntity<>(BusinessProfileRevisionMapper
          .mapRevisionToResponse(businessProfileRevisionManager.fetchLatestBusinessProfileForProduct(businessProfileId,
          externalProductId)), HttpStatus.OK);
    }

    @CircuitBreaker(name = "businessProfileCircuitBreakerConfig", fallbackMethod = "fallbackForCreateBusinessProfile")
    @Override
    public ResponseEntity<?> updateBusinessProfile(String businessProfileId,
                                                   String externalProductId,
                                                   BusinessProfileUpdateRequest request) {
        log.info("Request for updating business-profile with id :: {}, for productId :: {}, update request :: {}",
          businessProfileId, externalProductId, request);
        return new ResponseEntity<>(
          BusinessProfileMapper.mapModelToResponse(
            businessProfileRevisionManager.updateBusinessProfileRevisionForProduct(externalProductId,
              businessProfileId,
              request)),
          HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getAllBusinessProfiles() {
        log.info("Request to fetch all business-profiles");
        List<BusinessProfileResponse> responseList =
          businessProfileRevisionManager.getAllBusinessProfile().stream()
            .map(BusinessProfileMapper::mapToResponse)
            .toList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateBusinessProfile(String businessProfileId,
                                                   BusinessProfileUpdateRequest request){
        log.info("Request for updating business-profile with id :: {}, update request :: {}",
          businessProfileId, request);
        return new ResponseEntity<>(
          BusinessProfileMapper.mapModelToResponse(
            businessProfileRevisionManager.updateBusinessProfileRevision(businessProfileId,request)),
          HttpStatus.OK);
    }

}

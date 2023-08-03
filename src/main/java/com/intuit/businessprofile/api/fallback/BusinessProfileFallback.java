package com.intuit.businessprofile.api.fallback;

import com.intuit.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.intuit.businessprofile.dto.request.BusinessProfileUpdateRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Log4j2
@Component
public class BusinessProfileFallback {

    public RedirectView fallbackForCreateBusinessProfile(BusinessProfileCreateRequest request, Throwable ex) {
        log.error("Fallback initiated for business-profile create request :: {}, " +
          "due to error :: {}", request, ex.getMessage());
        return new RedirectView("/error/api-failure");
    }

    public ResponseEntity<?> fallbackForUpdateBusinessProfile(String businessProfileId,
                                                              String externalProductId,
                                                              BusinessProfileUpdateRequest request,
                                                              Throwable ex) {
        log.error("Fallback initiated for business-profile update request for business-profile id :: {}, " +
          "and product id :: {}, due to error :: {}", businessProfileId, externalProductId, ex.getMessage());
        return new ResponseEntity<>(Map.of(
          "error", true,
          "isRetryable", true,
          "message", "Under Maintenance"
        ), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

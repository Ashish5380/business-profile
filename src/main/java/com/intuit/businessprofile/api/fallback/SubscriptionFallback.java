package com.intuit.businessprofile.api.fallback;

import com.intuit.businessprofile.dto.request.SubscriptionRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Component
public class SubscriptionFallback {

    public RedirectView fallbackForSubscribe(SubscriptionRequest request){
        log.error("Fallback initiated for create subscription request :: {}", request);
        return new RedirectView("/error/api-failure");
    }

    public RedirectView fallbackForUnsubscribe(String businessProfileId,
                                               String externalProductId){
        log.error("Fallback initiated for un-subscription request for product id :: {} " +
          "and business-profile id :: {}", externalProductId, businessProfileId);
        return new RedirectView("/error/api-failure");
    }
}

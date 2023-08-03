package com.intuit.businessprofile.service;

import com.intuit.businessprofile.bo.SubscriptionBO;
import com.intuit.businessprofile.dto.request.SubscriptionRequest;
import com.intuit.businessprofile.dto.response.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionBO> getSubscription(String businessProfileId);

    void createSubscription(SubscriptionRequest request);

    void removeSubscription(String businessProfileId, String externalProductId);
}

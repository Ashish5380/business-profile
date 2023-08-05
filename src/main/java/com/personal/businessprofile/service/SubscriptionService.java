package com.personal.businessprofile.service;

import com.personal.businessprofile.bo.SubscriptionBO;
import com.personal.businessprofile.dto.request.SubscriptionRequest;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionBO> getSubscription(String businessProfileId);

    void createSubscription(SubscriptionRequest request);

    void removeSubscription(String businessProfileId, String externalProductId);
}

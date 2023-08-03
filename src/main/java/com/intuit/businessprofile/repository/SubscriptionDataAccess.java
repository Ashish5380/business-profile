package com.intuit.businessprofile.repository;

import com.intuit.businessprofile.entity.SubscriptionEntity;

import java.util.List;


public interface SubscriptionDataAccess {

    SubscriptionEntity save(SubscriptionEntity subscription);

    void upsertSubscription(SubscriptionEntity subscription);

    List<SubscriptionEntity> getByExternalProductIdAndBusinessProfileIdAndIsActive(String externalProductId,
                                                                                   String businessProfileId,
                                                                                   Boolean isActive);

    List<SubscriptionEntity> getByBusinessProfileIdAndIsActive(String businessProfileId,
                                                               Boolean isActive);
}

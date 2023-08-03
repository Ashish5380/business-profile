package com.intuit.businessprofile.repository.mongo;

import com.intuit.businessprofile.entity.SubscriptionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubscriptionMongoRepository extends MongoRepository<SubscriptionEntity, String> {

    List<SubscriptionEntity> getByExternalProductIdAndBusinessProfileIdAndIsActive(String externalProductId,
                                                                                   String businessProfileId,
                                                                                   Boolean isActive);

    List<SubscriptionEntity> getByBusinessProfileIdAndIsActive(String businessProfileId,
                                                               Boolean isActive);

}

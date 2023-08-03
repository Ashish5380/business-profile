package com.intuit.businessprofile.repository.impl;

import com.intuit.businessprofile.constant.StringConstants;
import com.intuit.businessprofile.entity.SubscriptionEntity;
import com.intuit.businessprofile.exception.DatabaseException;
import com.intuit.businessprofile.repository.SubscriptionDataAccess;
import com.intuit.businessprofile.repository.mongo.SubscriptionMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class SubscriptionDataAccessImpl implements SubscriptionDataAccess {

    private final MongoTemplate template;

    private final SubscriptionMongoRepository subscriptionMongoRepository;

    /**
     * Saves a SubscriptionEntity in the data store.
     *
     * @param subscription The SubscriptionEntity to be saved.
     * @return The saved SubscriptionEntity.
     */
    @Override
    @Transactional
    public SubscriptionEntity save(SubscriptionEntity subscription) {
        try {
            return subscriptionMongoRepository.save(subscription);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Upserts Subscription entity to database
     *
     * @param subscription the {@link SubscriptionEntity} is an entity created post validation and processing.
     *                     The function updates the entity if a document exists with same externalProductId else
     *                     creates a new entry in the collection.
     */
    @Override
    public void upsertSubscription(final SubscriptionEntity subscription) {
        try {
            // Query to get Subscription for a given external product id
            Query query = new Query(
              Criteria.where(SubscriptionEntity.EXTERNAL_PRODUCT_ID)
                .is(subscription.getExternalProductId())
            );

            // Query to update an existing document given selectors
            Update update = new Update()
              .set(SubscriptionEntity.BUSINESS_PROFILE_ID, subscription.getBusinessProfileId())
              .set(SubscriptionEntity.IS_ACTIVE, subscription.getIsActive());

            // Create or update the document
            template.upsert(query, update, SubscriptionEntity.class);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Retrieves a list of SubscriptionEntities based on the provided external product ID, business profile ID,
     * and active status.
     *
     * @param externalProductId The external product ID to filter the subscriptions by.
     * @param businessProfileId The business profile ID to filter the subscriptions by.
     * @param isActive          The active status to filter the subscriptions by.
     * @return A list of {@link SubscriptionEntity} that match the given external product ID, business profile ID,
     * and active status.
     */
    @Override
    public List<SubscriptionEntity> getByExternalProductIdAndBusinessProfileIdAndIsActive(final String externalProductId,
                                                                                          final String businessProfileId,
                                                                                          final Boolean isActive) {
        try {
            return subscriptionMongoRepository
              .getByExternalProductIdAndBusinessProfileIdAndIsActive(externalProductId, businessProfileId, isActive);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Retrieves a list of SubscriptionEntities based on the provided business profile ID and active status.
     *
     * @param businessProfileId The business profile ID to filter the subscriptions by.
     * @param isActive          The active status to filter the subscriptions by.
     * @return A list of {@link SubscriptionEntity} that match the given business profile ID and active status.
     */
    @Override
    public List<SubscriptionEntity> getByBusinessProfileIdAndIsActive(final String businessProfileId,
                                                                      final Boolean isActive) {
        try {
            return subscriptionMongoRepository.getByBusinessProfileIdAndIsActive(businessProfileId, isActive);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}

package com.intuit.businessprofile.service.impl;

import com.intuit.businessprofile.bo.BusinessProfileResponseBO;
import com.intuit.businessprofile.bo.ProductBO;
import com.intuit.businessprofile.bo.SubscriptionBO;
import com.intuit.businessprofile.constant.StringConstants;
import com.intuit.businessprofile.dto.request.SubscriptionRequest;
import com.intuit.businessprofile.entity.SubscriptionEntity;
import com.intuit.businessprofile.enums.ValidationStatus;
import com.intuit.businessprofile.exception.SubscriptionAlreadyExistException;
import com.intuit.businessprofile.mapper.ProfileMappingMapper;
import com.intuit.businessprofile.mapper.SubscriptionMapper;
import com.intuit.businessprofile.repository.SubscriptionDataAccess;
import com.intuit.businessprofile.service.BusinessProfileRevisionManager;
import com.intuit.businessprofile.service.ProductService;
import com.intuit.businessprofile.service.ProfileMappingService;
import com.intuit.businessprofile.service.SubscriptionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDataAccess subscriptionDataAccess;

    private final ProductService productService;

    private final BusinessProfileRevisionManager businessProfileRevisionManager;

    private final RevisionObserverRegistry observerRegistry;

    private final ProfileMappingService profileMappingService;

    private final RevisionObserverRegistry revisionObserverRegistry;

    @PostConstruct
    public void initMap() {
        List<BusinessProfileResponseBO> allProfiles = businessProfileRevisionManager.getAllBusinessProfile();
        List<List<SubscriptionBO>> allSubscriptions = new ArrayList<>();
        if (!CollectionUtils.isEmpty(allProfiles)) { // Checks if profiles list is not empty
            for (BusinessProfileResponseBO response : allProfiles) {
                allSubscriptions.add(getSubscription(response.getId().toString()));
            }
        }
        allSubscriptions.forEach(this::addRevisionObserver);

    }

    /**
     * Creates a subscription based on the provided SubscriptionRequest. This method performs the following steps:
     * <p>
     * 1. Validates the SubscriptionRequest to ensure it does not conflict with an existing subscription.
     * 2. Retrieves the {@link ProductBO} and {@link BusinessProfileResponseBO} associated with the request.
     * 3. Adds an observer to the BusinessProfile for the specified Product.
     * 4. Persists the profile mapping in the database for the BusinessProfile and Product association.
     * 5. Logs the successful creation of the profile-mapping for the subscription request.
     * 6. Adds the BusinessProfile as a subscriber to the Product.
     *
     * @param request The SubscriptionRequest containing the information necessary to create the subscription.
     */
    @Override
    public void createSubscription(SubscriptionRequest request) {
        // Validate Request
        validateExistingSubscription(request);

        // Get Product and profile
        ProductBO product = productService.getProduct(request.getExternalProductId());
        BusinessProfileResponseBO profile = businessProfileRevisionManager.getBusinessProfile(request.getBusinessProfileId());

        // Add observer to the profile for the product
        observerRegistry.addObserver(profile.getId().toString(), product.getExternalProductId());

        // persist profile mapping in database
        profileMappingService.createProfileMapping(ProfileMappingMapper.mapToBO(
          profile.getId().toString(),
          product.getExternalProductId(),
          profile.getLatestValidRevision()), ValidationStatus.VALID);
        log.info("Successfully created profile-mapping for subscription request :: {}", request);

        // Add business as a subscriber to the product
        addSubscription(product, profile);
    }

    /**
     * Retrieves a list of active subscriptions associated with the given business profile ID.
     *
     * @param businessProfileId The business profile ID for which active subscriptions are to be retrieved.
     * @return A list of {@link SubscriptionBO} (Business Objects) representing the active subscriptions associated with the specified business profile ID.
     * If no active subscriptions are found, an empty list is returned.
     */
    @Override
    public List<SubscriptionBO> getSubscription(String businessProfileId) {
        List<SubscriptionEntity> subscriptionEntityList = subscriptionDataAccess
          .getByBusinessProfileIdAndIsActive(businessProfileId, true);
        if (CollectionUtils.isEmpty(subscriptionEntityList)) {
            return Collections.emptyList();
        }
        log.info("Found subscription for business-profile :: {}, subscription :: {}", businessProfileId,
          subscriptionEntityList);
        return subscriptionEntityList
          .stream()
          .map(SubscriptionMapper::mapToBO)
          .collect(Collectors.toList());
    }

    /**
     * Removes a subscription by setting its 'isActive' status to false for the specified business profile ID and external product ID.
     *
     * @param businessProfileId The business profile ID associated with the subscription to be removed.
     * @param externalProductId The external product ID associated with the subscription to be removed.
     */
    @Override
    public void removeSubscription(String businessProfileId, String externalProductId) {
        List<SubscriptionBO> responses = getSubscription(businessProfileId);

        log.info("Subscription to be unsubscribed with businessProfileId :: {}, for externalProductId :: {}," +
          "subscription :: {}", businessProfileId, externalProductId, responses);
        Optional<SubscriptionBO> subscription = responses.stream()
          .filter(response -> externalProductId.equals(response.getExternalProductId()))
          .findFirst();
        // Removing observer (externalProductId) from the set of observers for business-profile (businessProfileId)
        observerRegistry.removeObserver(businessProfileId, externalProductId);
        subscription.map(SubscriptionMapper::mapResponseToEntity)
          .ifPresent(dbObject -> {
              dbObject.setIsActive(false);
              subscriptionDataAccess.upsertSubscription(dbObject);
          });
    }

    /**
     * Adds a new subscription to the database based on the provided ProductBO and BusinessProfileResponseBO.
     *
     * @param productBO                 The {@link ProductBO} containing the product information for the new subscription.
     * @param businessProfileResponseBO The {@link BusinessProfileResponseBO} containing the business profile information
     *                                  for the new subscription.
     */
    private void addSubscription(ProductBO productBO,
                                 BusinessProfileResponseBO businessProfileResponseBO) {
        log.info("Request to save subscription to database for business-profile :: {}, productId :: {}",
          businessProfileResponseBO.getId().toString(), productBO.getExternalProductId());
        subscriptionDataAccess.save(SubscriptionMapper.map(productBO, businessProfileResponseBO));
    }

    /**
     * Adds revision observers for the given list of subscriptions. This method iterates through the list of SubscriptionBO
     * objects and adds a revision observer for each subscription. The revision observer is associated with the BusinessProfile
     * ID and the external Product ID from each subscription.
     *
     * @param subscriptionResponses A List of SubscriptionBO objects containing the subscription information.
     *                              Each SubscriptionBO should have a valid BusinessProfile ID and external Product ID.
     */
    private void addRevisionObserver(List<SubscriptionBO> subscriptionResponses) {
        subscriptionResponses.forEach(response ->
          revisionObserverRegistry.addObserver(response.getBusinessProfileId(), response.getExternalProductId()));
    }

    /**
     * Validates if a subscription with the given external product ID and business profile ID already exists and is active.
     * If an active subscription already exists for the specified external product ID and business profile ID,
     * it throws a SubscriptionAlreadyExistException.
     *
     * @param request The SubscriptionRequest containing the external product ID and business profile ID to be validated.
     * @throws SubscriptionAlreadyExistException If an active subscription already exists for the specified external product ID
     *                                           and business profile ID.
     */
    private void validateExistingSubscription(SubscriptionRequest request) {
        List<SubscriptionEntity> entityList = subscriptionDataAccess
          .getByExternalProductIdAndBusinessProfileIdAndIsActive(request.getExternalProductId(),
            request.getBusinessProfileId(), true);

        final boolean exists = !CollectionUtils.isEmpty(entityList);
        if (exists) {
            log.error("Error subscribing to business-profile, as the subscription already exist");
            throw new SubscriptionAlreadyExistException(StringConstants.Error.INVALID_SUBSCRIPTION,
              HttpStatus.BAD_REQUEST);
        }
    }
}

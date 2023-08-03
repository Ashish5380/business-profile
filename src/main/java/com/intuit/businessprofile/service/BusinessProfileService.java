package com.intuit.businessprofile.service;

import com.intuit.businessprofile.bo.BusinessProfileResponseBO;
import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.dto.request.BusinessProfileCreateRequest;

import java.util.List;

public interface BusinessProfileService {

    /**
     * Fetches a business profile by its unique ID.
     *
     * @param id The ID of the business profile to fetch.
     * @return A BusinessProfileResponseBO containing the details of the requested business profile.
     */
    BusinessProfileResponseBO fetchBusinessProfile(String id);

    /**
     * Creates a new business profile based on the provided request.
     *
     * @param request The BusinessProfileCreateRequest containing the information to create a new profile.
     * @return A BusinessProfileResponseBO representing the newly created business profile.
     */
    BusinessProfileResponseBO createBusinessProfile(final BusinessProfileCreateRequest request);

    /**
     * Retrieves a list of all existing business profiles.
     *
     * @return A list of BusinessProfileResponseBO objects, each representing a business profile.
     */
    List<BusinessProfileResponseBO> getAllBusinessProfile();

    /**
     * Updates a business profile with the provided revision details.
     *
     * @param revisionBO The BusinessProfileRevisionBO containing the updates for the business profile.
     */
    void updateBusinessProfileForRevision(BusinessProfileRevisionBO revisionBO);
}

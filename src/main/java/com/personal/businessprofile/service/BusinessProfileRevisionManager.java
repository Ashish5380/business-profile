package com.personal.businessprofile.service;

import com.personal.businessprofile.bo.BusinessProfileResponseBO;
import com.personal.businessprofile.bo.BusinessProfileRevisionBO;
import com.personal.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.personal.businessprofile.dto.request.BusinessProfileUpdateRequest;
import com.personal.businessprofile.model.BusinessProfileModel;

import java.util.List;

public interface BusinessProfileRevisionManager {

    /**
     * Retrieves Business-Profile information for a specific business profile Id and returns a
     * {@link BusinessProfileResponseBO}
     * @param businessProfileId The ID of the business profile to fetch.
     * @return {@link BusinessProfileResponseBO} as response
     */
    BusinessProfileResponseBO getBusinessProfile(final String businessProfileId);

    /**
     * Retrieves all possible Business-Profile saved in database
     * @return List of {@link BusinessProfileResponseBO}
     */
    List<BusinessProfileResponseBO> getAllBusinessProfile();

    /**
     * Receives request to create business-profile using {@link BusinessProfileCreateRequest} as function argument
     * and returns {@link BusinessProfileResponseBO}
     * @param request Receives {@link BusinessProfileCreateRequest} containing info for business-profile to be created.
     * @return The {@link BusinessProfileResponseBO} representing the Business Profile saved information.
     */
    BusinessProfileResponseBO createBusinessProfile(final BusinessProfileCreateRequest request);

    /**
     * Receives request to update business-profile using {@link BusinessProfileUpdateRequest} in function arguments
     * for a specific business-profile id and product id
     * @param externalProductId The unique identifier of product information.
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param request {@link BusinessProfileUpdateRequest} containing information to update for business-profile
     * @return {@link BusinessProfileModel} updated business-profile as response
     */
    BusinessProfileModel updateBusinessProfileRevisionForProduct(final String externalProductId,
                                                                 final String businessProfileId,
                                                                 final BusinessProfileUpdateRequest request);

    /**
     * Retrieves latest business-profile revision information for a given unique product identifier and returns
     * {@link BusinessProfileRevisionBO}
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param externalProductId The unique identifier of product information.
     * @return {@link BusinessProfileRevisionBO} containing business-profile revision information
     */
    BusinessProfileRevisionBO fetchLatestBusinessProfileForProduct(final String businessProfileId,
                                                                   final String externalProductId);

    /**
     * Retrieves business-profile for a specific business profile unique identifier and revision and returns
     * {@link BusinessProfileRevisionBO} in response
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param revision Business profile revision to fetch
     * @return {@link BusinessProfileRevisionBO} containing business-profile revision information
     */
    BusinessProfileRevisionBO fetchBusinessProfileRevision(final String businessProfileId,
                                                           final Integer revision);

    /**
     * Receives request for updating business-profile for a given {@link BusinessProfileRevisionBO} in arguments
     * @param revision {@link BusinessProfileRevisionBO} revision to be made for specific business-profile
     */
    void updateBusinessProfile(final BusinessProfileRevisionBO revision);

    /**
     * Receive request for creating business-profile revision for {@link BusinessProfileUpdateRequest} and
     * unique identifier of business-profile
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param request {@link BusinessProfileUpdateRequest} containing information to update for business-profile
     * @return {@link BusinessProfileModel} updated business-profile as response
     */
    BusinessProfileModel updateBusinessProfileRevision(String businessProfileId,
                                                       BusinessProfileUpdateRequest request);

}

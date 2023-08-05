package com.personal.businessprofile.service;

import com.personal.businessprofile.bo.ProfileMappingBO;
import com.personal.businessprofile.enums.ValidationStatus;

import java.util.List;

public interface ProfileMappingService {

    /**
     * Creates a new business-profile to product mapping based on provided {@link ProfileMappingBO} containing mapping
     * information and validation status of type {@link ValidationStatus}
     * @param profileMappingBO Profile mapping request, with profile-mapping model
     * @param validationStatus Validation status for profile-mapping
     */
    void createProfileMapping(ProfileMappingBO profileMappingBO, ValidationStatus validationStatus);

    /**
     * Retrieves latest profile mapping information for provided business-profile unique identifier and product identifier
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param externalProductId The unique identifier of the product information.
     * @return {@link ProfileMappingBO} business-profile to product mapping for given params
     */
    ProfileMappingBO getLatestProfileMapping(String businessProfileId, String externalProductId);

    /**
     * Retrieves all business-profile to product mappings for a given business-profile identifier
     * @param businessProfileId The unique identifier of the Business Profile.
     * @return list of all matching business-profile to product mapping as {@link ProfileMappingBO}
     */
    List<ProfileMappingBO> getAllLatestValidMappingForBusiness(String businessProfileId);

    /**
     * Retrieves latest valid business-profile to product mapping for for provided business-profile
     * unique identifier and product identifier
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param externalProductId The unique identifier of the product information.
     * @return {@link ProfileMappingBO} business-profile to product mapping for given params
     */
    ProfileMappingBO getLatestValidMappingForBusinessAndProduct(String businessProfileId, String externalProductId);

    void updateProfileMapping(ProfileMappingBO profileMappingBO,
                              ValidationStatus validationStatus);
}

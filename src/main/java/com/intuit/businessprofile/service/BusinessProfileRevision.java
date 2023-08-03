package com.intuit.businessprofile.service;

import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.model.BusinessProfileModel;

public interface BusinessProfileRevision {

    /**
     * Creates a new Business Profile revision based on the provided {@link BusinessProfileRevisionBO) (Business Object).
     * @param internalRequest {@link BusinessProfileRevisionBO}
     * @return The {@link BusinessProfileModel} representing the newly created Business Profile revision.
     */
    BusinessProfileModel createBusinessProfileRevision(BusinessProfileRevisionBO internalRequest);

    /**
     * Retrieves business-profile revision for a given revision and business-profile unique identifier
     * @param businessProfileId The unique identifier of the Business Profile.
     * @param revision Business-profile revision to fetch
     * @return {@link BusinessProfileRevisionBO} containing business-profile revision information
     */
    BusinessProfileRevisionBO getBusinessProfileRevision(String businessProfileId,
                                                         Integer revision);

    BusinessProfileRevisionBO getLatestBusinessProfileRevision(String businessProfileId);

}

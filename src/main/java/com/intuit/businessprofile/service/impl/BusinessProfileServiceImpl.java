package com.intuit.businessprofile.service.impl;

import com.intuit.businessprofile.bo.BusinessProfileResponseBO;
import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.constant.BusinessProfileConstant;
import com.intuit.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.intuit.businessprofile.entity.BusinessProfileEntity;
import com.intuit.businessprofile.exception.InvalidBusinessProfileException;
import com.intuit.businessprofile.mapper.BusinessProfileMapper;
import com.intuit.businessprofile.repository.BusinessProfileDataAccess;
import com.intuit.businessprofile.service.BusinessProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BusinessProfileServiceImpl implements BusinessProfileService {

    private final BusinessProfileDataAccess businessProfileDataAccess;

    /**
     * Fetches a business profile based on its unique ID.
     *
     * @param id The ID of the business profile to fetch.
     * @return A BusinessProfileResponseBO containing the details of the requested business profile.
     * @throws InvalidBusinessProfileException If no business profile is found with the provided ID, an exception is thrown.
     *                                         The {@link InvalidBusinessProfileException} is wrapped with an appropriate error message and HTTP status code
     *                                         to indicate that the resource was not found (HTTP 500 Internal Server Error).
     */
    @Override
    public BusinessProfileResponseBO fetchBusinessProfile(String id) {
        return businessProfileDataAccess.findById(id)
          .map(BusinessProfileMapper::mapToBO)
          .orElseThrow(() -> new InvalidBusinessProfileException(BusinessProfileConstant.NO_BUSINESS_PROFILE,
            HttpStatus.NOT_FOUND));
    }

    /**
     * Creates a new business profile based on the provided {@link BusinessProfileCreateRequest}.
     * If the request passes the validation for duplicate business profiles, the new profile is saved
     * and returned as a {@link BusinessProfileResponseBO}. Otherwise, an exception is thrown with
     * details of the validation failure.
     *
     * @param request The {@link BusinessProfileCreateRequest} containing the details for the new
     *                business profile.
     * @return A {@link BusinessProfileResponseBO} representing the newly created business profile,
     * if the validation is successful.
     * @throws InvalidBusinessProfileException If the provided request fails validation for duplicate
     *                                         business profiles.
     */
    @Override
    @Transactional
    public BusinessProfileResponseBO createBusinessProfile(final BusinessProfileCreateRequest request) {

        // Validate request;
        validateDuplicateBusinessProfile(request);

        // If Validated then save the business profile
        BusinessProfileEntity savedEntity =
          businessProfileDataAccess.save(BusinessProfileMapper.map(request));
        log.info("Successfully created business-profile :: {}", savedEntity);

        // Map to bo and return
        return BusinessProfileMapper.mapToBO(savedEntity);
    }

    /**
     * Retrieves all existing business profiles and converts them to a list of {@link BusinessProfileResponseBO}.
     *
     * @return A list of {@link BusinessProfileResponseBO} containing details of all existing business profiles.
     * An empty list will be returned if no profiles are found.
     */
    @Override
    public List<BusinessProfileResponseBO> getAllBusinessProfile() {
        return businessProfileDataAccess.findAll()
          .stream()
          .map(BusinessProfileMapper::mapToBO)
          .collect(Collectors.toList());
    }

    /**
     * Updates or inserts a business profile revision into the data store based on the provided
     * {@link BusinessProfileRevisionBO}.
     *
     * @param revisionBO The {@link BusinessProfileRevisionBO} containing the details of the business
     *                   profile revision to be updated or inserted.
     */
    @Override
    public void updateBusinessProfileForRevision(BusinessProfileRevisionBO revisionBO) {
        businessProfileDataAccess.upsertBusinessProfile(BusinessProfileMapper.mapRevisionToEntity(revisionBO));
    }

    /**
     * Validates whether a duplicate business profile already exists based on the provided
     * {@link BusinessProfileCreateRequest} using the email field. This method checks if any
     * existing business profile matches the provided email, to ensure that duplicate profiles
     * are not created with the same email.
     * <p>
     * If a business profile with the provided email already exists, this method throws an
     * {@link InvalidBusinessProfileException} with details of the validation failure.
     *
     * @param request The {@link BusinessProfileCreateRequest} containing the email for the business
     *                profile to be created.
     * @throws InvalidBusinessProfileException If a business profile with the provided email already
     *                                         exists, indicating a validation failure for duplicate
     *                                         business profiles.
     */
    private void validateDuplicateBusinessProfile(BusinessProfileCreateRequest request) {
        List<BusinessProfileEntity> entityList =
          businessProfileDataAccess.getBusinessProfileByEmail(request.getEmail());

        // If list is empty which tells us that no data for the given email exists
        final boolean exists = !CollectionUtils.isEmpty(entityList);

        if (exists) {
            // Throw error for validation failure
            log.error("Error creating business-profile as it already exist :: {}", request);
            throw new InvalidBusinessProfileException(BusinessProfileConstant.INVALID_BUSINESS_PROFILE,
              HttpStatus.BAD_REQUEST);
        }
    }
}

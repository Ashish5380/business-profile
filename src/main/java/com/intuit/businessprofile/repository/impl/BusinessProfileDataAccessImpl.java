package com.intuit.businessprofile.repository.impl;

import com.intuit.businessprofile.constant.StringConstants;
import com.intuit.businessprofile.entity.BusinessProfileEntity;
import com.intuit.businessprofile.exception.DatabaseException;
import com.intuit.businessprofile.model.BusinessProfileModel;
import com.intuit.businessprofile.repository.BusinessProfileDataAccess;
import com.intuit.businessprofile.repository.mongo.BusinessProfileMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BusinessProfileDataAccessImpl implements BusinessProfileDataAccess {

    private final MongoTemplate template;

    private final BusinessProfileMongoRepository businessProfileMongoRepository;


    /**
     * Upserts a business profile entity into the database.
     * <p>
     * The method updates an existing business profile if a profile with the same ID already exists,
     * or inserts a new business profile if no profile with the provided ID is found in the database.
     * </p>
     *
     * @param entity The BusinessProfileEntity representing the business profile to upsert.
     * @throws DatabaseException If an error occurs while performing the upsert operation in the database,
     *                           a DatabaseException is thrown with an appropriate error message and
     *                           HTTP status code indicating an internal server error (HTTP 500).
     */
    @Override
    public void upsertBusinessProfile(final BusinessProfileEntity entity) {
        try {
            Query query = new Query(Criteria.where("_id").is(entity.getId()));

            Update update = new Update()
              .set("legalName", entity.getLegalName())
              .set("businessAddress", entity.getBusinessAddress())
              .set("legalAddress", entity.getLegalAddress())
              .set("email", entity.getEmail())
              .set("website", entity.getWebsite())
              .set("taxIdentifier", entity.getTaxIdentifier())
              .set("companyName", entity.getCompanyName())
              .set("latestValidRevision", entity.getLatestValidRevision());

            template.upsert(query, update, BusinessProfileEntity.class);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Retrieves a list of business profiles from the database based on their email.
     * <p>
     * The method queries the database to find business profiles that match the provided email.
     * If one or more matching profiles are found, they are returned as a list of BusinessProfileEntity objects.
     * </p>
     *
     * @param email The email address of the business profiles to search for.
     * @return A list of BusinessProfileEntity objects representing the business profiles that match the provided
     * email. If no matching profiles are found, an empty list is returned.
     * @throws DatabaseException If an error occurs while querying the database for the business profiles,
     *                           a DatabaseException is thrown with an appropriate error message and
     *                           HTTP status code indicating an internal server error (HTTP 500).
     */
    public List<BusinessProfileEntity> getBusinessProfileByEmail(final String email) {
        try {
            return businessProfileMongoRepository.getByEmail(email);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Saves a business profile entity into the database.
     * <p>
     * The method persists the provided business profile entity in the database. If the entity has an ID, it will be updated
     * if an existing profile with the same ID is found. If the entity has no ID, a new business profile will be created
     * in the database.
     * </p>
     *
     * @param profile The BusinessProfileEntity representing the business profile to save or update.
     * @return The saved or updated BusinessProfileEntity object.
     * @throws DatabaseException If an error occurs while saving or updating the business profile entity in the database,
     *                           a DatabaseException is thrown with an appropriate error message and
     *                           HTTP status code indicating an internal server error (HTTP 500).
     */
    @Override
    @Transactional
    public BusinessProfileEntity save(final BusinessProfileEntity profile) {
        try {
            return businessProfileMongoRepository.save(profile);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Finds a business profile entity in the database based on its ID.
     * <p>
     * The method queries the database to find a business profile with the provided ID. If a matching profile is found,
     * it is returned as an Optional of BusinessProfileEntity. If no profile with the provided ID is found,
     * an empty Optional is returned.
     * </p>
     *
     * @param businessProfileId The ID of the business profile to search for.
     * @return An Optional containing the BusinessProfileEntity if a matching profile is found, or an empty Optional
     * if no profile with the provided ID is found.
     * @throws DatabaseException If an error occurs while querying the database for the business profile,
     *                           a DatabaseException is thrown with an appropriate error message and
     *                           HTTP status code indicating an internal server error (HTTP 500).
     */
    @Override
    public Optional<BusinessProfileEntity> findById(final String businessProfileId) {
        try {
            return businessProfileMongoRepository.findById(businessProfileId);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Retrieves a list of all business profile entities from the database.
     * <p>
     * The method queries the database to retrieve all business profile entities and returns them as a list
     * of BusinessProfileEntity objects.
     * </p>
     *
     * @return A list of BusinessProfileEntity objects representing all business profiles in the database.
     * If there are no business profiles in the database, an empty list is returned.
     * @throws DatabaseException If an error occurs while querying the database for the business profiles,
     *                           a DatabaseException is thrown with an appropriate error message and
     *                           HTTP status code indicating an internal server error (HTTP 500).
     */
    @Override
    public List<BusinessProfileEntity> findAll() {
        try {
            return businessProfileMongoRepository.findAll();
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }


}

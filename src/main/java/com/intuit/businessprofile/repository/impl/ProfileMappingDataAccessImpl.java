package com.intuit.businessprofile.repository.impl;

import com.intuit.businessprofile.constant.StringConstants;
import com.intuit.businessprofile.entity.ProfileMappingEntity;
import com.intuit.businessprofile.entity.SubscriptionEntity;
import com.intuit.businessprofile.entity.projection.LatestValidRevisionProjection;
import com.intuit.businessprofile.enums.ValidationStatus;
import com.intuit.businessprofile.exception.DatabaseException;
import com.intuit.businessprofile.model.ProfileMapping;
import com.intuit.businessprofile.repository.ProfileMappingDataAccess;
import com.intuit.businessprofile.repository.mongo.ProfileMappingMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@RequiredArgsConstructor
public class ProfileMappingDataAccessImpl implements ProfileMappingDataAccess {
    private final MongoTemplate template;

    private final ProfileMappingMongoRepository profileMappingMongoRepository;

    @Override
    public List<ProfileMappingEntity> findLatestMapping(
      String businessProfileId,
      String externalProductId) {
        Aggregation aggregation = newAggregation(
          match(Criteria.where("businessProfileId").is(businessProfileId)
            .and("externalProductId").is(externalProductId)),
          sort(Sort.Direction.DESC, "revision"),
          limit(1)
        );
        AggregationResults<ProfileMappingEntity> results = template.aggregate(aggregation,
          ProfileMappingEntity.class,
          ProfileMappingEntity.class);
        return results.getMappedResults();

    }

    @Override
    @Transactional
    public ProfileMappingEntity save(ProfileMappingEntity profileMapping) {
        return profileMappingMongoRepository.save(profileMapping);
    }

    @Override
    public List<ProfileMappingEntity> findDocumentWithLatestValidRevision(
      String businessProfileId) {
        MatchOperation match = Aggregation.match(
          Criteria.where(ProfileMapping.BUSINESS_PROFILE_ID).is(businessProfileId)
            .and(ProfileMapping.VALIDATION_STATUS).is(ValidationStatus.VALID)
        );
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, ProfileMapping.REVISION);
        GroupOperation group = Aggregation.group(ProfileMapping.BUSINESS_PROFILE_ID)
          .first(ProfileMapping.REVISION).as("highestRevision")
          .push(Aggregation.ROOT).as("documents");
        ProjectionOperation project = Aggregation.project(Fields.fields("highestRevision"))
          .and(ArrayOperators.Filter.filter("documents")
            .as("doc")
            .by(ComparisonOperators.Eq.valueOf("$$doc.revision").equalToValue("$highestRevision"))
          ).as("documents");
        Aggregation aggregation = Aggregation.newAggregation(match, sort, group, project);
        AggregationResults<LatestValidRevisionProjection> results = template.aggregate(aggregation,
          ProfileMappingEntity.class,
          LatestValidRevisionProjection.class);
        return results.getMappedResults().get(0).getDocuments();

    }

    @Override
    public List<ProfileMappingEntity> findLatestValidMapping(String businessProfileId, String externalProductId) {
        Aggregation aggregation = newAggregation(
          match(Criteria.where("businessProfileId").is(businessProfileId)
            .and("externalProductId").is(externalProductId)),
          match(Criteria.where("validationStatus").is("valid")),
          sort(Sort.Direction.DESC, "revision"),
          limit(1)
        );
        AggregationResults<ProfileMappingEntity> results = template.aggregate(aggregation,
          ProfileMappingEntity.class,
          ProfileMappingEntity.class);
        return results.getMappedResults();
    }

    @Override
    public void upsertProfileMapping(ProfileMappingEntity entity){
        try {
            // Query to get Profile Mapping for a given external product id, business profile id and revision
            Query query = new Query(
              Criteria.where(ProfileMappingEntity.EXTERNAL_PRODUCT_ID)
                .is(entity.getExternalProductId())
                .and(ProfileMapping.BUSINESS_PROFILE_ID)
                .is(entity.getBusinessProfileId())
                .and(ProfileMapping.REVISION).is(entity.getRevision()));
            Update update = new Update()
              .set(ProfileMapping.VALIDATION_STATUS, entity.getValidationStatus())
              .set(ProfileMapping.VALIDATION_ERROR, entity.getValidationError());
            template.upsert(query, update, ProfileMappingEntity.class);
        }catch (Exception e){
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

}

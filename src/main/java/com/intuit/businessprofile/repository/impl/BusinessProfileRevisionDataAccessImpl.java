package com.intuit.businessprofile.repository.impl;

import com.intuit.businessprofile.constant.StringConstants;
import com.intuit.businessprofile.entity.BusinessProfileRevisionEntity;
import com.intuit.businessprofile.exception.DatabaseException;
import com.intuit.businessprofile.repository.BusinessProfileRevisionDataAccess;
import com.intuit.businessprofile.repository.mongo.BusinessProfileRevisionMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@RequiredArgsConstructor
public class BusinessProfileRevisionDataAccessImpl implements BusinessProfileRevisionDataAccess {
    private final MongoTemplate template;
    private final BusinessProfileRevisionMongoRepository businessProfileRevisionMongoRepository;

    @Override
    public List<BusinessProfileRevisionEntity> getBusinessProfileForRevision(final String businessProfileId,
                                                                             final Integer revision) {
        try {
            return businessProfileRevisionMongoRepository.getByRevisionAndBusinessProfileId(revision, businessProfileId);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public BusinessProfileRevisionEntity save(BusinessProfileRevisionEntity businessProfileRevision) {
        try {
            return businessProfileRevisionMongoRepository.save(businessProfileRevision);
        } catch (Exception e) {
            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
              HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public List<BusinessProfileRevisionEntity> getLatestBusinessProfileRevision(final String businessProfileId){
        Aggregation aggregation = newAggregation(
          match(Criteria.where("businessProfileId").is(businessProfileId)),
          sort(Sort.Direction.DESC, "revision"),
          limit(1));
        AggregationResults<BusinessProfileRevisionEntity> results = template.aggregate(aggregation,
        BusinessProfileRevisionEntity.class,
          BusinessProfileRevisionEntity.class);
        return results.getMappedResults();
    }

//    @Override
//    public List<BusinessProfileRevisionEntity> findDocumentWithLatestRevision(
//      String businessProfileId) {
//        try {
//            Aggregation aggregation = newAggregation(
//              match(Criteria.where("businessProfileId").is(businessProfileId)),
//              sort(Sort.Direction.DESC, "revision"),
//              limit(1)
//            );
//            AggregationResults<BusinessProfileRevisionEntity> results = template.aggregate(aggregation,
//              BusinessProfileRevisionEntity.class,
//              BusinessProfileRevisionEntity.class);
//            return results.getMappedResults();
//        } catch (Exception e) {
//            throw new DatabaseException(StringConstants.Error.DATABASE_FAILURE_MESSAGE,
//              HttpStatus.INTERNAL_SERVER_ERROR, e);
//        }
//
//    }

}

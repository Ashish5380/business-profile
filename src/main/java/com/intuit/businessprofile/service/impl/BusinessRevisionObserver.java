package com.intuit.businessprofile.service.impl;

import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.config.KafkaProducer;
import com.intuit.businessprofile.dto.request.KafkaMessageRequest;
import com.intuit.businessprofile.service.RevisionObserver;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class BusinessRevisionObserver implements RevisionObserver {

    private final KafkaProducer kafkaProducer;
    private final String businessProfileValidationTopic;

    public BusinessRevisionObserver(final KafkaProducer kafkaProducer,
                                    @Value("${business.profile.validation.topic}") String businessProfileValidationTopic) {
        this.kafkaProducer = kafkaProducer;
        this.businessProfileValidationTopic = businessProfileValidationTopic;
    }

    @Override
    public void update(String businessProfileId, BusinessProfileRevisionBO revision) {
        KafkaMessageRequest request = KafkaMessageRequest.builder()
          .businessProfileId(businessProfileId)
          .businessProfile(revision)
          .build();
        log.info("Sending kafka message for business-profile revision to topic :: {}, message :: {}",
          businessProfileValidationTopic, revision);
        kafkaProducer.sendMessage(request, businessProfileValidationTopic);
    }
}

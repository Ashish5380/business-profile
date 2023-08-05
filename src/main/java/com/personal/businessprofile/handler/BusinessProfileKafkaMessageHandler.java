package com.personal.businessprofile.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.businessprofile.config.DummyHttpClient;
import com.personal.businessprofile.dto.request.KafkaMessageRequest;
import com.personal.businessprofile.enums.ValidationStatus;
import com.personal.businessprofile.mapper.ProfileMappingMapper;
import com.personal.businessprofile.service.ProfileMappingService;
import com.personal.businessprofile.service.impl.RevisionObserverRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Log4j2
@Component
@RequiredArgsConstructor
public class BusinessProfileKafkaMessageHandler {
    private final ProfileMappingService profileMappingService;
    private final RevisionObserverRegistry revisionObserverRegistry;
    private final ObjectMapper objectMapper;
    private final DummyHttpClient httpClient;

    @KafkaListener(topics = "business-profile-validation-topic")
    public void consume(@Payload String payload) {
        try {
            log.info("Payload received {}", payload);
            KafkaMessageRequest message = objectMapper.readValue(payload, KafkaMessageRequest.class);
            Map<String, Set<String>> observers = revisionObserverRegistry.getObserversMap();
            Set<String> observerProducts = observers.get(message.getBusinessProfileId());
            for (String productId : observerProducts) {
                profileMappingService.updateProfileMapping(ProfileMappingMapper.mapToBO(
                  message.getBusinessProfileId(),
                  productId,
                  message.getBusinessProfile().getRevision()), ValidationStatus.IN_PROGRESS);
                ResponseEntity<?> response = httpClient.callExternalApi();

                ValidationStatus validationStatus = response.getStatusCode().is2xxSuccessful()
                  ? ValidationStatus.VALID
                  : ValidationStatus.INVALID;

                profileMappingService.updateProfileMapping(ProfileMappingMapper.mapToBO(
                  message.getBusinessProfileId(),
                  productId,
                  message.getBusinessProfile().getRevision()), validationStatus);
            }
        } catch (Exception exception) {
            log.error("Error processing kafka message :: {}", exception.getMessage());
        }

    }


}

package com.personal.businessprofile.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.businessprofile.dto.request.KafkaMessageRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Log4j2
@Configuration
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendMessage(KafkaMessageRequest msg, String topicName) {
        try {
            log.info("Received message :: {} to produce to topic :: {}", kafkaTemplate, topicName);
            String stringMessage = objectMapper.writeValueAsString(msg);
            kafkaTemplate.send(topicName, stringMessage);
        }catch (Exception exception){
            log.error("Unable to produce message due to error :: {}", exception.getMessage());
        }
    }
}

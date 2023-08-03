package com.intuit.businessprofile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessageResponse {

    String businessProfileId;

    Boolean isValid;

    String validationReason;

    String externalProductId;

    Integer revision;
}

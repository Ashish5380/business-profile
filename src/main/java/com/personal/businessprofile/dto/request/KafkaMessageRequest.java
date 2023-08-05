package com.personal.businessprofile.dto.request;

import com.personal.businessprofile.bo.BusinessProfileRevisionBO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessageRequest {

    String businessProfileId;

    BusinessProfileRevisionBO businessProfile;
}

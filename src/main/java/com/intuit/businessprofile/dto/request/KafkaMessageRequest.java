package com.intuit.businessprofile.dto.request;

import com.intuit.businessprofile.bo.BusinessProfileRevisionBO;
import com.intuit.businessprofile.model.BusinessProfileModel;
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

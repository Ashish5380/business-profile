package com.intuit.businessprofile.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest {

    String externalProductId;

    String businessProfileId;
}

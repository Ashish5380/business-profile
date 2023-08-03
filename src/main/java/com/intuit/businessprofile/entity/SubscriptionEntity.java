package com.intuit.businessprofile.entity;

import com.intuit.businessprofile.model.SubscriptionModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Document(collection = "product_profile_subscription")
public class SubscriptionEntity extends SubscriptionModel {
}

package com.personal.businessprofile.entity;

import com.personal.businessprofile.model.SubscriptionModel;
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

package com.personal.businessprofile.bo;

import com.personal.businessprofile.model.SubscriptionModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SubscriptionBO extends SubscriptionModel {
}

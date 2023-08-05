package com.personal.businessprofile.entity;

import com.personal.businessprofile.model.ProfileMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Document(collection = "business_profile_product_mapping")
public class ProfileMappingEntity extends ProfileMapping {
}

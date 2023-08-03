package com.intuit.businessprofile.bo;

import com.intuit.businessprofile.model.ProfileMapping;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class ProfileMappingBO extends ProfileMapping {
}

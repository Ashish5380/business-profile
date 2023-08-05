package com.personal.businessprofile.bo;

import com.personal.businessprofile.model.ProfileMapping;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class ProfileMappingBO extends ProfileMapping {
}

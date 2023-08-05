package com.personal.businessprofile.bo;

import com.personal.businessprofile.model.BusinessProfileModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class BusinessProfileResponseBO extends BusinessProfileModel {
    protected Integer latestValidRevision;

}

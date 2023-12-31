package com.personal.businessprofile.bo;

import com.personal.businessprofile.model.BusinessProfileModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class BusinessProfileRevisionBO extends BusinessProfileModel {

    protected String businessProfileId;

    protected Integer revision;
}

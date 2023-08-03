package com.intuit.businessprofile.entity;

import com.intuit.businessprofile.model.BusinessProfileModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "business_profile_revision")
public class BusinessProfileRevisionEntity extends BusinessProfileModel {

    protected String businessProfileId;

    protected Integer revision;
}

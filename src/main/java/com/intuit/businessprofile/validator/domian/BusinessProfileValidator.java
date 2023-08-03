package com.intuit.businessprofile.validator.domian;

import com.intuit.businessprofile.constant.StringConstants;
import com.intuit.businessprofile.constant.StringConstants.Error;
import com.intuit.businessprofile.dto.request.BusinessProfileCreateRequest;
import com.intuit.businessprofile.exception.ValidationFailureException;
import com.intuit.businessprofile.model.Address;
import com.intuit.businessprofile.validator.BaseValidator;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

@UtilityClass
public class BusinessProfileValidator {

    public void validateBusinessProfile(BusinessProfileCreateRequest request) {
        BaseValidator.singleValidator()
          .isNotNullOrEmpty(request.getBusinessAddress(), StringConstants.Error.EMPTY_BUSINESS_ADDRESS_MESSAGE)
          .isNotNullOrEmpty(request.getLegalAddress(), StringConstants.Error.EMPTY_LEGAL_ADDRESS_MESSAGE)
          .isEmail(request.getEmail(), StringConstants.Error.INVALID_EMAIL)
          .isWebsite(request.getWebsite(), StringConstants.Error.INVALID_WEBSITE)
          .isNotNullAndBlank(request.getCompanyName(), StringConstants.Error.INVALID_BUSINESS_NAME)
          .isNotNullAndBlank(request.getLegalName(), StringConstants.Error.INVALID_LEGAL_NAME)
          .join(validator -> validateAddress(validator, request.getBusinessAddress()))
          .join(validator -> validateAddress(validator, request.getLegalAddress()))
          .validate(errors -> {
              throw new ValidationFailureException(StringUtils.join(errors, ","), HttpStatus.BAD_REQUEST);
          });
    }

    public void validateAddress(BaseValidator validator, Address address) {
        validator.isPinCode(address.getPinCode(), StringConstants.Error.INVALID_PIN_CODE_MESSAGE);
    }

}

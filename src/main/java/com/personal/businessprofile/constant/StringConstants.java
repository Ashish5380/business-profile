package com.personal.businessprofile.constant;

public interface StringConstants {

    interface Error {
        String DATABASE_FAILURE_MESSAGE = "Unable to process database request";

        String NO_MAPPING_ERROR_MESSAGE = "Product is not subscribed to business profile";

        String INVALID_VALIDATION_STATE = "Please validate the existing update before making a update!";

        String INVALID_BUSINESS_PROFILE_REVISION =
          "Business profile revision is inappropriate or does not exist";

        String INVALID_PIN_CODE_MESSAGE = "Invalid pinCode supplied in the request";

        String EMPTY_BUSINESS_ADDRESS_MESSAGE = "Empty business address passed in the request";

        String EMPTY_LEGAL_ADDRESS_MESSAGE = "Empty legal address passed in the request";

        String INVALID_BUSINESS_NAME = "Empty/Invalid business name passed in the request";

        String INVALID_LEGAL_NAME = "Empty/Invalid legal name passed in the request";

        String INVALID_EMAIL = "Empty/Invalid email passed in the request";

        String INVALID_WEBSITE = "Empty/Invalid website passed in the request";

        public static String INVALID_SUBSCRIPTION = "Subscription with product already exist";

        public static String NO_SUBSCRIPTION = "Subscription does not exist for business profile";

        String RESOURCE_OCCUPIED = "Resource currently occupied for another same operation";
    }
}

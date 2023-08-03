package com.intuit.businessprofile.validator;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class BaseValidator {
    public static final String WEBSITE_REGEX = "^(https?://)?(www\\.)?[a-zA-Z0-9.-]+\\.[a-zA-Z.]{2,}(\\/\\S*)?$";
    public static final Pattern WEBSITE_PATTERN = Pattern.compile(WEBSITE_REGEX);
    public static final String PIN_CODE_REGEX = "^\\d{5}(?:-\\d{4})?$";
    public static final Pattern PIN_CODE_PATTERN = Pattern.compile(PIN_CODE_REGEX);
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private final ArrayList<String> errorList = new ArrayList<>();

    private boolean hasValidationFailedBefore = false;

    private boolean checkAll = false;

    private BaseValidator() {
    }

    public static BaseValidator singleValidator() {
        return new BaseValidator();
    }

    public static BaseValidator allErrorValidator() {
        BaseValidator validator = new BaseValidator();
        validator.checkAll = true;
        return validator;
    }

    public BaseValidator validate(Consumer<String[]> onError) {
        if (!errorList.isEmpty()) {
            onError.accept(errorList.toArray(new String[0]));
            hasValidationFailedBefore = true;
        }
        return this;
    }

    public BaseValidator isTrue(Supplier<Boolean> predicate, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }
        if (!predicate.get()) {
            addErrorMessage(message);
        }
        return this;
    }

    public BaseValidator join(Consumer<BaseValidator> validatorConsumer) {
        validatorConsumer.accept(this);
        return this;
    }

    public BaseValidator isNotNull(Object object, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (object == null) {
            addErrorMessage(message);
        }
        return this;
    }

    private void addErrorMessage(String message) {
        errorList.add(message);
        if (!checkAll) {
            hasValidationFailedBefore = true;
        }
    }

    public BaseValidator isEmail(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || !EMAIL_PATTERN.matcher(val).matches()) {
            addErrorMessage(message);
        }
        return this;
    }

    public BaseValidator isWebsite(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || !WEBSITE_PATTERN.matcher(val).matches()) {
            addErrorMessage(message);
        }
        return this;
    }

    public BaseValidator isPinCode(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || !PIN_CODE_PATTERN.matcher(val).matches()) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> BaseValidator isNotNullOrEmpty(Collection<T> val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || val.isEmpty()) {
            addErrorMessage(message);
        }
        return this;
    }

    public <T> BaseValidator isNotNullOrEmpty(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || val.isEmpty()) {
            addErrorMessage(message);
        }
        return this;
    }

    public BaseValidator isNotNullAndBlank(String val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || val.trim().isEmpty()) {
            addErrorMessage(message);
        }
        return this;
    }

    public BaseValidator isNotNullOrEmpty(Object val, String message) {
        if (hasValidationFailedBefore) {
            return this;
        }

        if (val == null || ObjectUtils.isEmpty(val)) {
            addErrorMessage(message);
        }
        return this;
    }
}

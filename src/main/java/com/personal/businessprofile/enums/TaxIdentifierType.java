package com.personal.businessprofile.enums;

public enum TaxIdentifierType {
    PAN("PAN"),
    EIN("EIN"),
    GST_IN("GSTIN");
    private final String name;

    TaxIdentifierType(String name) {
        this.name = name;
    }
}

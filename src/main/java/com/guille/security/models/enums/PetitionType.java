package com.guille.security.models.enums;

public enum PetitionType {
    PETITION(0, "Petition"),
    OFFER(0, "Offer");

    private final int value;
    private final String displayName;

    PetitionType(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PetitionType fromValue(int value) {
        for (PetitionType type : PetitionType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PetitionType value: " + value);
    }
}

package com.guille.security.models.enums;

import jakarta.persistence.AttributeConverter;

public class PetitionTypeConverter implements AttributeConverter<PetitionType, Boolean> {
    @Override
    public Boolean convertToDatabaseColumn(PetitionType attribute) {
        if (attribute == null) return null;

        switch(attribute){
            case PETITION:
                return false;
            case OFFER:
                return true;
            default:
                throw new IllegalArgumentException(attribute + " not supported.");
        }
    }

    @Override
    public PetitionType convertToEntityAttribute(Boolean dbData) {
        if (dbData == null) return null;

        if(dbData){
            return PetitionType.OFFER;
        }else{
            return PetitionType.PETITION;
        }

    }
}

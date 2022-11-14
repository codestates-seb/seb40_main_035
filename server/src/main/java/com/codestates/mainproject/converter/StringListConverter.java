package com.codestates.mainproject.converter;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute.toString();
    }


    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData.equals("[]")) {
            return new ArrayList<>();
        }
        
        return Arrays.asList(dbData.substring(1, dbData.length()-1).split(", "));
    }
}

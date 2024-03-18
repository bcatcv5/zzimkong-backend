package com.boostcamp.zzimkong.support;

import com.boostcamp.zzimkong.domain.ObjectType;
import org.springframework.core.convert.converter.Converter;

public class ObjectTypeEnumConverter implements Converter<String, ObjectType> {
    @Override
    public ObjectType convert(String source) {
        String value = (source.equals("0")) ? "SPACE" : "FURNITURE";
        try {
            return ObjectType.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

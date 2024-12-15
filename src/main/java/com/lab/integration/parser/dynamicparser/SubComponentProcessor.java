package com.lab.integration.parser.dynamicparser;

import com.lab.integration.model.Field;
import com.lab.integration.model.ParserEntity;

import java.util.regex.Pattern;

public class SubComponentProcessor implements FieldProcessor {
    @Override
    public Object process(String fieldValue, Field field, ParserEntity parserEntity) {
        if (field.getSubComponentIndex() != null) {
            String[] subComponents = fieldValue.split(Pattern.quote(String.valueOf(parserEntity.getSubComponentDelimiter())));
            int subInx = field.getSubComponentIndex() - 1; // Zero-based index
            if (subInx < subComponents.length) {
                return subComponents[subInx];
            }else{
                // if not exists return empty string
                return "";
            }
        }
        return fieldValue;
    }
}

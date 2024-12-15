package com.lab.integration.parser.dynamicparser;

import com.lab.integration.model.Field;
import com.lab.integration.model.ParserEntity;

import java.util.regex.Pattern;

public class ComponentProcessor implements FieldProcessor {
    @Override
    public Object process(String fieldValue, Field field, ParserEntity parserEntity) {
        if (field.getComponentIndex() != null) {
            String[] components = fieldValue.split(Pattern.quote(String.valueOf(parserEntity.getComponentDelimiter())));
            int cmpInx = field.getComponentIndex() - 1; // Zero-based index
            if (cmpInx < components.length) {
                return components[cmpInx];
            }else{
                // if not exists return empty string
                return "";
            }
        }
        return fieldValue; // Fallback to the original value if no component index is found
    }
}

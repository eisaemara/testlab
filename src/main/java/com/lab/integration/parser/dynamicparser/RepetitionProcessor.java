package com.lab.integration.parser.dynamicparser;

import com.lab.integration.model.Field;
import com.lab.integration.model.ParserEntity;

import java.util.regex.Pattern;

public class RepetitionProcessor implements FieldProcessor {
    @Override
    public Object process(String fieldValue, Field field, ParserEntity parserEntity) {
        if (fieldValue != null && fieldValue.contains(String.valueOf(parserEntity.getRepetitionDelimiter()))) {
            return fieldValue.split(Pattern.quote(String.valueOf(parserEntity.getRepetitionDelimiter())));
        }
        return fieldValue;
    }
}

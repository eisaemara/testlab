package com.lab.integration.parser.dynamicparser;

import com.lab.integration.model.Field;
import com.lab.integration.model.ParserEntity;

public class BaseProcessor implements FieldProcessor {
    @Override
    public Object process(String fieldValue, Field field, ParserEntity parserEntity) {
        return fieldValue;
    }
}

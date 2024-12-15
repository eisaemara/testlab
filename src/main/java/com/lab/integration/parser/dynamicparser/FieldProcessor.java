package com.lab.integration.parser.dynamicparser;

import com.lab.integration.model.Field;
import com.lab.integration.model.ParserEntity;

public interface FieldProcessor {
    Object process(String fieldValue, Field field, ParserEntity parserEntity);
}

package com.lab.integration.parser.dynamicparser;


import com.lab.integration.model.Field;
import com.lab.integration.model.ParserEntity;
import com.lab.integration.model.Segment;
import com.lab.integration.parser.MessageParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DynamicMessageParser implements MessageParser {

    private final ParserEntity parserEntity;

    public DynamicMessageParser(ParserEntity parserEntity) {
        this.parserEntity = parserEntity;
    }

    @Override
    public Map<String, Object> parse(String message) {
        System.out.println("Starting message parsing...");
        System.out.println("Message: \n" + message);

        Map<String, Object> result = new HashMap<>();
        String[] segments = message.split(parserEntity.getSegmentDelimiter());

        for (Segment segment : parserEntity.getSegments()) {
            System.out.println("Processing segment: " + segment.getName());

            Pattern segmentPattern = Pattern.compile(segment.getRegex());
            for (String line : segments) {
                System.out.println("Checking line: " + line.trim());

                if (segmentPattern.matcher(line.trim()).matches()) {
                    System.out.println("Line matched segment: " + segment.getName());

                    Map<String, Object> extractedFields = extractFields(line, segment.getFields());

                    System.out.println("Extracted fields for segment [" + segment.getName() + "]: " + extractedFields);

                    result.put(segment.getName(), extractedFields);
                } else {
                    System.out.println("Line did not match segment: " + segment.getName());
                }
            }
        }

        System.out.println("Parsing completed. Parsed result: " + result);
        return result;
    }

    // this should be updated and take in his account the component and sub-component and esachoe and repeast char
    private Map<String, Object> extractFields(String segment, List<Field> fields) {
        Map<String, Object> fieldData = new HashMap<>();
        String[] tokens = segment.split("\\|");
        List<FieldProcessor> processors = List.of(
                new ComponentProcessor(),
                new SubComponentProcessor(),
                new RepetitionProcessor(),
                new BaseProcessor()
        );

        for (Field field : fields) {
            int position = field.getPosition() - 1; // Adjust for zero-based index
            if (position < tokens.length) {
                String fieldValue = tokens[position];
                for (FieldProcessor processor : processors) {
                    fieldValue = (String) processor.process(fieldValue, field, parserEntity);
                }
                fieldData.put(field.getName(), fieldValue);
            }
        }

        return fieldData;
    }
}
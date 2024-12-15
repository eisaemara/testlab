//package com.lab.integration.parser;
//
//import com.lab.integration.model.MessageResult;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class DefaultParser implements MessageParser {
//
//    private static final String HEADER_REGEX = "H\\|.*";
//    private static final String PATIENT_REGEX = "P\\|.*";
//    private static final String ORDER_REGEX = "O\\|.*";
//    private static final String RESULT_REGEX = "R\\|.*";
//    private static final String SEGMENT_DELIMITER = "(<CR>|\\r|\\n)"; // Matches <CR>, \r, or \n
//
//    @Override
//    public MessageResult parse(String message) {
//
//        // Divide the message into lines based on segment delimiters
//        String[] segments = message.split(SEGMENT_DELIMITER);
//        Map<String, String> headerInfo = extractSegmentInfo(segments, HEADER_REGEX);
//        Map<String, String> patientInfo = extractSegmentInfo(segments, PATIENT_REGEX);
//        Map<String, String> orderInfo = extractSegmentInfo(segments, ORDER_REGEX);
//        Map<String, String> resultInfo = extractSegmentInfo(segments, RESULT_REGEX);
//
//        return new MessageResult(headerInfo, patientInfo, orderInfo, resultInfo);
//
//    }
//
//    private Map<String, String> extractSegmentInfo(String[] segments, String regex) {
//        Map<String, String> data = new HashMap<>();
//        Pattern pattern = Pattern.compile(regex);
//
//        for (String segment : segments) {
//            Matcher matcher = pattern.matcher(segment.trim());
//            if (matcher.matches()) {
//                data.put("raw", segment); // Add the matched segment to the map
//            }
//        }
//        return data;
//    }
//}

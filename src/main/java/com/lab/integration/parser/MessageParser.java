package com.lab.integration.parser;

import com.lab.integration.model.MessageResult;

import java.util.List;
import java.util.Map;

public interface MessageParser {
    Map<String,Object> parse(String message);
}
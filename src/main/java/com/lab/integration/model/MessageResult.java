package com.lab.integration.model;

import java.util.Map;

public record MessageResult(Map<String, String> headerInfo, Map<String, String> patientInfo,
                            Map<String, String> orderInfo, Map<String, String> resultInfo) {

}

package com.lab.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private IntegrationFlowContext flowContext;

    @GetMapping("/active-listeners")
    public List<String> getActiveListeners() {
        return flowContext.getRegistry().keySet().stream().toList();
    }
}
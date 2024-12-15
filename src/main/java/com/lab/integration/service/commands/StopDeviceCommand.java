package com.lab.integration.service.commands;

import org.springframework.integration.dsl.context.IntegrationFlowContext;

public class StopDeviceCommand implements DeviceCommand{
    private final String deviceId;
    private final IntegrationFlowContext flowContext;

    public StopDeviceCommand(String deviceId, IntegrationFlowContext flowContext) {
        this.deviceId = deviceId;
        this.flowContext = flowContext;
    }

    @Override
    public void execute() {
        flowContext.remove("tcpFlow-" + deviceId);
        System.out.println("Stopped listener for device " + deviceId);
    }
}

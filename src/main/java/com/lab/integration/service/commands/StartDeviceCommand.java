package com.lab.integration.service.commands;

import com.lab.integration.factory.LabDeviceListenerFactory;
import com.lab.integration.model.LabDevice;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;

public class StartDeviceCommand implements DeviceCommand{
    private final LabDevice device;
    private final LabDeviceListenerFactory listenerFactory;
    private final IntegrationFlowContext flowContext;

    public StartDeviceCommand(LabDevice device, LabDeviceListenerFactory listenerFactory, IntegrationFlowContext flowContext) {
        this.device = device;
        this.listenerFactory = listenerFactory;
        this.flowContext = flowContext;
    }

    @Override
    public void execute() {
        IntegrationFlow flow = listenerFactory.createListener(device);
        flowContext.registration(flow).id("tcpFlow-" + device.getDeviceId()).register();
        System.out.println("Started listener for device " + device.getDeviceId());
    }
}

package com.lab.integration.service;

import com.lab.integration.factory.LabDeviceListenerFactory;
import com.lab.integration.model.LabDevice;
import com.lab.integration.service.commands.DeviceCommand;
import com.lab.integration.service.commands.StartDeviceCommand;
import com.lab.integration.service.commands.StopDeviceCommand;
import lombok.AllArgsConstructor;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.stereotype.Service;



@AllArgsConstructor
@Service
public class DeviceControllerService {

    private LabDeviceListenerFactory listenerFactory;

    private IntegrationFlowContext flowContext;

    public void addDevice(LabDevice device) {
        DeviceCommand startCommand = new StartDeviceCommand(device, listenerFactory, flowContext);
        startCommand.execute();
    }

    public void removeDevice(String deviceId) {
        DeviceCommand stopCommand = new StopDeviceCommand(deviceId, flowContext);
        stopCommand.execute();
    }
}

package com.lab.integration.controller;

import com.lab.integration.model.LabDevice;
import com.lab.integration.repository.LabDeviceRepository;
import com.lab.integration.service.DeviceControllerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
@AllArgsConstructor
public class LabDeviceManagementController {

    private LabDeviceRepository labDeviceRepository;
    private DeviceControllerService deviceController;

    @PostMapping("/add")
    public ResponseEntity<String> addDevice(@RequestBody LabDevice device) {
        labDeviceRepository.save(device);
        deviceController.addDevice(device);
        return ResponseEntity.ok("Device added and listener started.");
    }

    @DeleteMapping("/remove/{deviceId}")
    public ResponseEntity<String> removeDevice(@PathVariable String deviceId) {
        deviceController.removeDevice(deviceId);
        labDeviceRepository.deleteById(Long.valueOf(deviceId));
        return ResponseEntity.ok("Device removed and listener stopped.");
    }
}

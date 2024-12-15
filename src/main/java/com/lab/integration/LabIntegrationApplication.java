package com.lab.integration;

import com.lab.integration.model.LabDevice;
import com.lab.integration.repository.LabDeviceRepository;
import com.lab.integration.service.DeviceControllerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class LabIntegrationApplication implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final ResourceLoader resourceLoader;
    private LabDeviceRepository labDeviceRepository;
    private DeviceControllerService deviceController;

    @Value("${inserts}")
    private String insertsSQl;

    public LabIntegrationApplication(JdbcTemplate jdbcTemplate,
                                     ResourceLoader resourceLoader,
                                     LabDeviceRepository labDeviceRepository,
                                        DeviceControllerService deviceController

    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceLoader = resourceLoader;
        this.labDeviceRepository = labDeviceRepository;
        this.deviceController = deviceController;
    }

    public static void main(String[] args) {
        SpringApplication.run(LabIntegrationApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        //Resource resource = resourceLoader.getResource(insertsSQl);
        Path scriptPath = Path.of(insertsSQl);
        String script = Files.readString(scriptPath);
        jdbcTemplate.execute(script);
        LabDevice l = new LabDevice();
        l.setDeviceId("Device 1");
        l.setName("Device 1");
        l.setParserType("DXH500");
        l.setPort(1200);
        labDeviceRepository.save(l);
        deviceController.addDevice(l);

    }
}

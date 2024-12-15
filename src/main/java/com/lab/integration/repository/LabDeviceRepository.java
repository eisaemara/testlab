package com.lab.integration.repository;

import com.lab.integration.model.LabDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabDeviceRepository extends JpaRepository<LabDevice, Long> {
    List<LabDevice> findAll();
}

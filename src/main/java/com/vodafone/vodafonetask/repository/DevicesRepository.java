package com.vodafone.vodafonetask.repository;

import com.vodafone.vodafonetask.model.Devices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevicesRepository extends JpaRepository<Devices, Long> {
}
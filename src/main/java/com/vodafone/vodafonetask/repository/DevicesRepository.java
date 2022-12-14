package com.vodafone.vodafonetask.repository;

import com.vodafone.vodafonetask.model.DeviceStatus;
import com.vodafone.vodafonetask.model.Devices;
import com.vodafone.vodafonetask.model.Sim;
import com.vodafone.vodafonetask.model.SimStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DevicesRepository extends JpaRepository<Devices, Long> {
    @Query("select d from Devices d where d.sim.simStatus = ?1")
    List<Devices> findBySim_SimStatusEquals(SimStatus simStatus);

    @Query("select d from Devices d " +
            "where d.deviceStatus = 'READY' and d.deviceIdealTemperature between -25 and 85 and d.sim.id is not null " +
            "order by d.id")
    List<Devices> findByDeviceStatusEqualsAndDeviceIdealTemperatureIsBetweenAndSim_IdIsNotNullOrderByIdAsc();

}
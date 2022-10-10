package com.vodafone.vodafonetask.DatabaseRecords;

import com.vodafone.vodafonetask.model.DeviceStatus;
import com.vodafone.vodafonetask.model.Devices;
import com.vodafone.vodafonetask.model.Sim;
import com.vodafone.vodafonetask.model.SimStatus;
import com.vodafone.vodafonetask.repository.DevicesRepository;
import com.vodafone.vodafonetask.repository.SimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FillDatabaseWithRecords {

    @Autowired
    DevicesRepository devicesRepository;

    @Autowired
    SimRepository simRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void addSimsAndDevices() {
        Devices devices = new Devices();
        Sim sim = new Sim();
        List<Sim> simList = new ArrayList<>();

        //add SIMs
        List<Long> simIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        List<String> countries = Arrays.asList("Egypt", "UK", "Egypt", "UK", "Egypt");
        List<Integer> operators = Arrays.asList(10, 20, 30, 40, 50);
        List<SimStatus> simStatus = Arrays.asList(SimStatus.WAITING, SimStatus.ACTIVE, SimStatus.WAITING, SimStatus.BLOCKED, SimStatus.WAITING);
        for (int i = 0; i < simIds.size(); i++) {
//            sim.setId(simIds.get(i));
            sim.setSimStatus(simStatus.get(i));
            sim.setCountry(countries.get(i));
            sim.setOperatorCode(operators.get(i));
            simRepository.save(sim);
            simList.add(sim);
        }

        // add devices
        List<Long> deviceIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        List<Integer> temperature = Arrays.asList(10, 25, -10, 0, 50);
        List<DeviceStatus> deviceStatus = Arrays.asList(DeviceStatus.READY , DeviceStatus.READY , DeviceStatus.READY, DeviceStatus.NOT_READY, DeviceStatus.READY);
        for(int i = 0; i < deviceIds.size(); i++){
//            devices.setId(deviceIds.get(i));
            devices.setDeviceStatus(deviceStatus.get(i));
            devices.setDeviceIdealTemperature(temperature.get(i));
            if(i < simList.size())
                devices.setSim(simList.get(i));
            devicesRepository.save(devices);
        }
    }

}

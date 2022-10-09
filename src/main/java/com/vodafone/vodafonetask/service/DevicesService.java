package com.vodafone.vodafonetask.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vodafone.vodafonetask.model.DeviceStatus;
import com.vodafone.vodafonetask.model.Devices;
import com.vodafone.vodafonetask.model.SimStatus;
import com.vodafone.vodafonetask.repository.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DevicesService {

    @Autowired
    private DevicesRepository devicesRepository;

    public List<Devices> getAllWaitingDevices(){
        return devicesRepository.findBySim_SimStatusEquals(SimStatus.WAITING);
    }
    public List<Devices> getAllAvailableDevicesForSale(){
        return devicesRepository.findByDeviceStatusEqualsAndDeviceIdealTemperatureIsBetweenAndSim_IdIsNotNullOrderByIdAsc();
    }
    public void updateDevice(SimStatus simStatus, Long id){
        devicesRepository.updateSimSimStatusById(simStatus , id);

    }

    public List<Devices> getDevices() {
        return devicesRepository.findAll();
    }
}

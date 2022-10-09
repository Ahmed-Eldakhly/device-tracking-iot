package com.vodafone.vodafonetask.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vodafone.vodafonetask.model.*;
import com.vodafone.vodafonetask.repository.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class DevicesService {

    @Autowired
    private DevicesRepository devicesRepository;

    public List<DeviceResponse> getAllWaitingDevices(){
        return DeviceResponse.getResponseAsJSON(devicesRepository.findBySim_SimStatusEquals(SimStatus.WAITING));
    }
    public List<DeviceResponse> getAllAvailableDevicesForSale(){
        return DeviceResponse.getResponseAsJSON(devicesRepository.findByDeviceStatusEqualsAndDeviceIdealTemperatureIsBetweenAndSim_IdIsNotNullOrderByIdAsc());
    }
    public void updateDevice(Map<String , Object> updatedData, Long id){
        System.out.println(updatedData);
        Optional<Devices> oldRecord = devicesRepository.findById(id);
        Devices updatedDevice = oldRecord.get();
        Sim updatedSim = updatedDevice.getSim();
        Iterator<Map.Entry<String, Object>> iterator = updatedData.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
            switch (entry.getKey()){
                case "status":
                    updatedDevice.setDeviceStatus(DeviceStatus.valueOf((String) entry.getValue()));
                    break;
                case "ideal_teamperature":
                    updatedDevice.setDeviceIdealTemperature((int) entry.getValue());
                    break;
                case "sim_country":
                    updatedSim.setCountry((String) entry.getValue());
                    break;
                case "sim_operator_code":
                    updatedSim.setOperatorCode((int) entry.getValue());
                    break;
                case "sim_status":
                    updatedSim.setSimStatus(SimStatus.valueOf((String) entry.getValue()));
                    break;
            }
            updatedDevice.setSim(updatedSim);
            devicesRepository.save(updatedDevice);


        }

    }

    public List<Devices> getDevices() {
        return devicesRepository.findAll();
    }
}

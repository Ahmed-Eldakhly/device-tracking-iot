package com.vodafone.vodafonetask.service;

import com.vodafone.vodafonetask.VodafoneTaskApplication;
import com.vodafone.vodafonetask.model.Devices;
import com.vodafone.vodafonetask.model.Sim;
import com.vodafone.vodafonetask.repository.DevicesRepository;
import com.vodafone.vodafonetask.repository.SimRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.vodafone.vodafonetask.model.DeviceStatus.NOT_READY;
import static com.vodafone.vodafonetask.model.DeviceStatus.READY;
import static com.vodafone.vodafonetask.model.SimStatus.ACTIVE;
import static com.vodafone.vodafonetask.model.SimStatus.WAITING;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@ImportResource({"classpath*:application-context.xml"})
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = VodafoneTaskApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class DevicesServiceIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private DevicesRepository devicesRepository;

    @Autowired
    private SimRepository simRepository;

    private void addDevice(){
        // add device and sim and assign sim to device
        Devices d = new Devices();
        Sim s = new Sim();
        d.setId(1L);
        d.setDeviceStatus(READY);
        d.setDeviceIdealTemperature(25);
        s.setId(1L);
        s.setSimStatus(WAITING);
        s.setOperatorCode(20);
        s.setCountry("Egypt");

        simRepository.save(s);
        d.setSim(s);
        devicesRepository.save(d);
    }

    @Test
    void getAllWaitingDevices() {
        addDevice();
        List<Devices> devicesList = devicesRepository.findBySim_SimStatusEquals(WAITING);
        assertEquals(devicesList.get(0).getId(), 1);
    }

    @Test
    void getAllAvailableDevicesForSale() {
        addDevice();
        List<Devices> devicesList = devicesRepository.findByDeviceStatusEqualsAndDeviceIdealTemperatureIsBetweenAndSim_IdIsNotNullOrderByIdAsc();
        assertEquals(devicesList.get(0).getId(), 1);
    }

    @Test
    void updateDevice() {
        addDevice();
        Devices DeviceFromDB = devicesRepository.findById(1L).get();
        assertEquals(DeviceFromDB.getDeviceStatus(), READY);
        assertEquals(DeviceFromDB.getDeviceIdealTemperature(), 25);
        assertEquals(DeviceFromDB.getSim().getSimStatus(), WAITING);
        int id = 1;
        Devices updatedDevice = new Devices();
        Sim s = new Sim();
        updatedDevice.setId(1L);
        updatedDevice.setDeviceStatus(NOT_READY);
        updatedDevice.setDeviceIdealTemperature(45);
        s.setId(1L);
        s.setSimStatus(ACTIVE);
        s.setOperatorCode(20);
        s.setCountry("Egypt");

        simRepository.save(s);
        updatedDevice.setSim(s);

        devicesRepository.save(updatedDevice);

        DeviceFromDB = devicesRepository.findById(1L).get();
        assertEquals(DeviceFromDB.getDeviceStatus(), NOT_READY);
        assertEquals(DeviceFromDB.getDeviceIdealTemperature(), 45);
        assertEquals(DeviceFromDB.getSim().getSimStatus(), ACTIVE);
    }
}
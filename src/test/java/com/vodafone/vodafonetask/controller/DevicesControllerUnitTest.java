package com.vodafone.vodafonetask.controller;

import com.vodafone.vodafonetask.model.DeviceResponse;
import com.vodafone.vodafonetask.model.DeviceStatus;
import com.vodafone.vodafonetask.repository.DevicesRepository;
import com.vodafone.vodafonetask.service.DevicesService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.vodafone.vodafonetask.model.SimStatus.WAITING;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ImportResource({"classpath*:application-context.xml"})
@WebMvcTest
class DevicesControllerUnitTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    DevicesService devicesService;


    @Test
    void getWaitingDevices() throws Exception {

        DeviceResponse d1 = new DeviceResponse(), d2 = new DeviceResponse();
        // device 1 data
        d1.setId(1L);
        d1.setDeviceIdealTemperature(25);
        d1.setDeviceStatus(DeviceStatus.READY);
        d1.setSim_id(1);
        d1.setSim_status(WAITING);
        d1.setSim_country("Egypt");
        d1.setSim_operator_code(20);

        // device 2 data
        d2.setId(2L);
        d2.setDeviceIdealTemperature(45);
        d2.setDeviceStatus(DeviceStatus.READY);
        d2.setSim_id(2);
        d2.setSim_status(WAITING);
        d2.setSim_country("UK");
        d2.setSim_operator_code(20);

        Mockito.when(devicesService.getAllWaitingDevices()).thenReturn( asList(d1, d2));

        mockMvc.perform(get("/api/devices/getWaitingDevices"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].deviceStatus", Matchers.is("READY")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void getAvailableForSellDevices() throws Exception {
        DeviceResponse d1 = new DeviceResponse(), d2 = new DeviceResponse(), d3 = new DeviceResponse();
        // device 1 data
        d1.setId(1L);
        d1.setDeviceIdealTemperature(25);
        d1.setDeviceStatus(DeviceStatus.READY);
        d1.setSim_id(1);
        d1.setSim_status(WAITING);
        d1.setSim_country("Egypt");
        d1.setSim_operator_code(20);

        // device 2 data
        d2.setId(2L);
        d2.setDeviceIdealTemperature(45);
        d2.setDeviceStatus(DeviceStatus.READY);
        d2.setSim_id(2);
        d2.setSim_status(WAITING);
        d2.setSim_country("UK");
        d2.setSim_operator_code(20);

        // device 3 data
        d3.setId(3L);
        d3.setDeviceIdealTemperature(80);
        d3.setDeviceStatus(DeviceStatus.READY);
        d3.setSim_id(2);
        d3.setSim_status(WAITING);
        d3.setSim_country("UK");
        d3.setSim_operator_code(20);

        Mockito.when(devicesService.getAllAvailableDevicesForSale()).thenReturn( asList(d1, d2, d3));

        mockMvc.perform(get("/api/devices/getAvailableDevicesforSell"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].deviceStatus", Matchers.is("READY")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)));
    }

    @Test
    void updateDevices() throws Exception {
//        Mockito.when(devicesService.getAllAvailableDevicesForSale()).thenReturn( asList(d1, d2, d3));

        int id = 1;
        String payload = "{\"id\":1,\"deviceStatus\":\"READY\",\"deviceIdealTemperature\":25,\"sim_id\":1,\"sim_country\":\"Egypt\",\"sim_operator_code\":20,\"sim_status\":\"WAITING\"}";

        mockMvc.perform(patch("/api/devices/update/" + 1)
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().is(200));

    }
}
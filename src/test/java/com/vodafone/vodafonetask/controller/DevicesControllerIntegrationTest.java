package com.vodafone.vodafonetask.controller;

import com.vodafone.vodafonetask.VodafoneTaskApplication;
import com.vodafone.vodafonetask.model.DeviceResponse;
import com.vodafone.vodafonetask.model.DeviceStatus;
import com.vodafone.vodafonetask.model.Devices;
import com.vodafone.vodafonetask.model.Sim;
import com.vodafone.vodafonetask.repository.DevicesRepository;
import com.vodafone.vodafonetask.repository.SimRepository;
import com.vodafone.vodafonetask.service.DevicesService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.vodafone.vodafonetask.model.DeviceStatus.READY;
import static com.vodafone.vodafonetask.model.SimStatus.WAITING;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ImportResource({"classpath*:application-context.xml"})
@RunWith(SpringRunner.class)
@SpringBootTest(
         webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = VodafoneTaskApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class DevicesControllerIntegrationTest {

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
    void getWaitingDevices() throws Exception {
        // add device and sim and assign sim to device
        addDevice();

        mockMvc.perform(get("/api/devices/getWaitingDevices"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].deviceStatus", Matchers.is("READY")))
                .andExpect(jsonPath("$[0].sim_country", Matchers.is("Egypt")));
    }

    @Test
    void getAvailableForSellDevices() throws Exception {

        addDevice();
        mockMvc.perform(get("/api/devices/getAvailableDevicesforSell"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].deviceStatus", Matchers.is("READY")))
                .andExpect(jsonPath("$[0].sim_id", Matchers.is(1)));
    }

    @Test
    void updateDevices() throws Exception {
        addDevice();
        int id = 1;
        String payload = "{\"id\":1,\"deviceStatus\":\"READY\",\"deviceIdealTemperature\":25,\"sim_id\":1,\"sim_country\":\"Egypt\",\"sim_operator_code\":20,\"sim_status\":\"WAITING\"}";

        mockMvc.perform(patch("/api/devices/update/" + 1)
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().is(200));

    }
}
package com.vodafone.vodafonetask.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.vodafone.vodafonetask.model.DeviceResponse;
import com.vodafone.vodafonetask.model.DeviceStatus;
import com.vodafone.vodafonetask.model.Devices;
import com.vodafone.vodafonetask.model.SimStatus;
import com.vodafone.vodafonetask.service.DevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
@RestController
@RequestMapping("/api/devices")
public class DevicesController {
    @Autowired
    private DevicesService devicesService;

    @JsonProperty(value = "Devices" , required = true)


    @GetMapping(path = "/getWaitingDevices")
    public Iterable<DeviceResponse> getWaitingDevices() {
        return DeviceResponse.getResponseAsJSON(devicesService.getAllWaitingDevices());
    }

    @GetMapping(path = "/getAvailableDevicesforSell")
    public Iterable<DeviceResponse> getAvailableForSellDevices() {
        return DeviceResponse.getResponseAsJSON(devicesService.getAllAvailableDevicesForSale());
    }

    @PatchMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void save(@RequestParam("status") SimStatus status, @PathVariable Long id) {

        System.out.println("update");
        devicesService.updateDevice(status, id);
//        devicesService.updateDevice(status, id);

    }

}

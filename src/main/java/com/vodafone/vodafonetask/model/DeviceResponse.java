package com.vodafone.vodafonetask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeviceResponse {
    public DeviceResponse(){

    }
    public DeviceResponse(Devices device){
        id = device.getId();
        System.out.println(id);
        deviceStatus = device.getDeviceStatus();
        System.out.println(deviceStatus);
        deviceIdealTemperature = device.getDeviceIdealTemperature();
        System.out.println(deviceIdealTemperature);
        sim_id = device.getSim().getId();
        System.out.println(sim_id);
        sim_country = device.getSim().getCountry();
        System.out.println(sim_country);
        sim_status = device.getSim().getSimStatus();
        System.out.println(sim_status);
        sim_operator_code = device.getSim().getOperatorCode();
        System.out.println(sim_operator_code);
    }

    private Long id;
    private DeviceStatus deviceStatus;
    private int deviceIdealTemperature;
    private long sim_id;
    private String sim_country;
    private int sim_operator_code;
    private SimStatus sim_status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public int getDeviceIdealTemperature() {
        return deviceIdealTemperature;
    }

    public void setDeviceIdealTemperature(int deviceIdealTemperature) {
        this.deviceIdealTemperature = deviceIdealTemperature;
    }


    public long getSim_id() {
        return sim_id;
    }

    public void setSim_id(long sim_id) {
        this.sim_id = sim_id;
    }

    public String getSim_country() {
        return sim_country;
    }

    public void setSim_country(String sim_country) {
        this.sim_country = sim_country;
    }

    public int getSim_operator_code() {
        return sim_operator_code;
    }

    public void setSim_operator_code(int sim_operator_code) {
        this.sim_operator_code = sim_operator_code;
    }

    public SimStatus getSim_status() {
        return sim_status;
    }

    public void setSim_status(SimStatus sim_status) {
        this.sim_status = sim_status;
    }

    // Method
    // Creating toString
    @Override public String toString()
    {
        // Returning attributes of organisation
        return "Devices [device_id=" + id
                + ", device_status=" + deviceStatus
                + ", deviceIdeal_temperature=" + deviceIdealTemperature
                + ", sim_id=" + sim_id
                + ", sim_country=" + sim_country
                + ", sim_operator_code=" + sim_operator_code
                + ", sim_status=" + sim_status + "]";
    }

    public static List<DeviceResponse> getResponseAsJSON(List<Devices> databaseRecords){
        List<DeviceResponse> responseList = new ArrayList<DeviceResponse>();
        System.out.println("size = " + databaseRecords.size());
        ObjectMapper Obj = new ObjectMapper();

        // Getting organisation object as a json string
        databaseRecords.forEach(currentDevice -> {
            System.out.println(currentDevice.getSim().getCountry());
            DeviceResponse currentDeviceAsResponse = new DeviceResponse(currentDevice);
            try {

                // Getting organisation object as a json string
                String jsonStr = Obj.writeValueAsString(currentDeviceAsResponse);

                // Displaying JSON String on console
                System.out.println(jsonStr);
                responseList.add(currentDeviceAsResponse);
            }

            // Catch block to handle exceptions
            catch (IOException e) {

                // Display exception along with line number
                // using printStackTrace() method
                e.printStackTrace();
            }
        });
        System.out.println("here");
        return responseList;
    }
}

package com.vodafone.vodafonetask.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;



@Entity
@Table(name = "devices")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="devices")
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceId")
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DeviceStatus deviceStatus;


    @Column(name = "idealTemperature")
    private int deviceIdealTemperature;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "simId", referencedColumnName = "simId")
    private Sim sim;

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
        if(deviceIdealTemperature < -25)
            deviceIdealTemperature = -25;
        else if(deviceIdealTemperature > 85)
            deviceIdealTemperature = 85;
        this.deviceIdealTemperature = deviceIdealTemperature;
    }

    public Sim getSimId() {
        return sim;
    }

    public void setSimId(Sim sim) {
        this.sim = sim;
    }
}
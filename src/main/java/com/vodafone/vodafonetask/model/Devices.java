package com.vodafone.vodafonetask.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "devices")
@NamedQueries({
        @NamedQuery(name = "Devices.updateSimSimStatusById", query = "update Devices d set d.sim.simStatus = ?1 where d.id = ?2")
})
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="devices")
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DeviceStatus deviceStatus;


    @Column(name = "ideal_temperature")
    private int deviceIdealTemperature;


    @OneToOne()
    @JoinColumn(name = "sim_id", referencedColumnName = "sim_id")
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

    public Sim getSim() {
        return sim;
    }

    public void setSim(Sim sim) {
        this.sim = sim;
    }

    // Method
    // Creating toString

}
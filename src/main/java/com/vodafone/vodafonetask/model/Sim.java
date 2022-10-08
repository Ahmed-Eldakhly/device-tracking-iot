package com.vodafone.vodafonetask.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "sim")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="devices")
public class Sim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "simId")
    private Long id;

    @OneToOne(mappedBy = "sim")
    private Devices device;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SimStatus simStatus;


    @Column(name = "operatorCode")
    private int operatorCode;

    @Column(name = "country")
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Devices getDevice() {
        return device;
    }

    public void setDevice(Devices device) {
        this.device = device;
    }

    public SimStatus getSimStatus() {
        return simStatus;
    }

    public void setSimStatus(SimStatus simStatus) {
        this.simStatus = simStatus;
    }

    public int getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(int operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
package com.oxygensened.userprofile.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String postCode;
    private String lon;
    private String lat;

    public Address() {
    }

    public Address(String city, String postCode, String lon, String lat) {
        this.city = city;
        this.postCode = postCode;
        this.lon = lon;
        this.lat = lat;
    }

    public Address(Long id, String city, String postCode, String lon, String lat) {
        this.id = id;
        this.city = city;
        this.postCode = postCode;
        this.lon = lon;
        this.lat = lat;
    }

    public Long id() {
        return id;
    }

    public String city() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String postCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String lon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String lat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}

package com.oxygensend.joboffer.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.io.Serializable;

@Table(name = "address", indexes = {
        @Index(name = "post_code_city_idx", columnList = "postCode, city")

})
@Entity
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String postCode;
    private Double lon;
    private Double lat;

    public Address() {
    }

    public Address(String city, String postCode, Double lon, Double lat) {
        this.city = city;
        this.postCode = postCode;
        this.lon = lon;
        this.lat = lat;
    }

    public Address(Long id, String city, String postCode, Double lon, Double lat) {
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

    public Double lon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double lat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}

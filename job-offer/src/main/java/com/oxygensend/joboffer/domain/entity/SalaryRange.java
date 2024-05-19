package com.oxygensend.joboffer.domain.entity;

import com.oxygensend.joboffer.domain.entity.part.SalaryType;
import com.oxygensend.joboffer.domain.entity.part.SupportedCurrency;
import com.oxygensend.joboffer.infrastructure.jpa.converter.SupportedCurrencyConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
public class SalaryRange implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double downRange;
    private Double upRange;
    @Convert(converter = SupportedCurrencyConverter.class)
    @Column(nullable = false, length = 3)
    private SupportedCurrency currency;
    private SalaryType type;

    public SalaryRange() {
    }

    public SalaryRange(Double downRange, Double upRange, SupportedCurrency currency, SalaryType type) {
        this.downRange = downRange;
        this.upRange = upRange;
        this.currency = currency;
        this.type = type;
    }

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double downRange() {
        return downRange;
    }

    public void setDownRange(Double downRange) {
        this.downRange = downRange;
    }

    public Double upRange() {
        return upRange;
    }

    public void setUpRange(Double upRange) {
        this.upRange = upRange;
    }

    public SupportedCurrency currency() {
        return currency;
    }

    public void setCurrency(SupportedCurrency currency) {
        this.currency = currency;
    }

    public SalaryType type() {
        return type;
    }

    public void setType(SalaryType type) {
        this.type = type;
    }
}

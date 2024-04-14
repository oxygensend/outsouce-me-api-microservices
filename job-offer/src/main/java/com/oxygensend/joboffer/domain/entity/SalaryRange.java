package com.oxygensend.joboffer.domain.entity;

import com.oxygensend.joboffer.domain.SalaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Currency;

@Entity
public class SalaryRange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double downRange;
    private Double upRange;
    @Column(nullable = false)
    private Currency currency;
    private SalaryType type;

    public SalaryRange() {
    }

    public SalaryRange(Double downRange, Double upRange, Currency currency, SalaryType type) {
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

    public Currency currency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public SalaryType type() {
        return type;
    }

    public void setType(SalaryType type) {
        this.type = type;
    }
}

package com.oxygensened.userprofile.application.company.dto;

import com.oxygensened.userprofile.domain.entity.Company;

public record CompanyView(String name) {

    public static CompanyView from(Company company) {
        return new CompanyView(company.name());
    }
}

package com.oxygensened.userprofile.context.company.dto;

import com.oxygensened.userprofile.domain.Company;

public record CompanyView(String name) {

    public static CompanyView from(Company company) {
        return new CompanyView(company.name());
    }
}

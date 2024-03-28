package com.oxygensened.userprofile.domain;

import java.util.Optional;

public interface CompanyRepository {

    Company save(Company company);

    Optional<Company> findByName(String name);
}

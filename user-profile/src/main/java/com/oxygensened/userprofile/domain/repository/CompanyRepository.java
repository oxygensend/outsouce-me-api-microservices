package com.oxygensened.userprofile.domain.repository;

import com.oxygensened.userprofile.domain.entity.Company;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    Company save(Company company);

    Optional<Company> findByName(String name);

    List<Company> findAll();
}

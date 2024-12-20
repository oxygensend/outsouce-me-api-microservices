package com.oxygensened.userprofile.infrastructure.jpa.repository;

import com.oxygensened.userprofile.domain.entity.Company;
import com.oxygensened.userprofile.domain.repository.CompanyRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class CompanyJpaFacadeRepository implements CompanyRepository {

    private final CompanyJpaRepository companyJpaRepository;

    CompanyJpaFacadeRepository(CompanyJpaRepository companyJpaRepository) {
        this.companyJpaRepository = companyJpaRepository;
    }

    @Override
    public Company save(Company company) {
        return companyJpaRepository.save(company);
    }

    @Override
    public Optional<Company> findByName(String name) {
        return companyJpaRepository.findByName(name);
    }

    @Override
    public List<Company> findAll() {
        return companyJpaRepository.findAll();
    }

    @Override
    public void saveAll(List<Company> companies) {
        companyJpaRepository.saveAll(companies);
    }

    @Override
    public long count() {
        return companyJpaRepository.count();
    }
}

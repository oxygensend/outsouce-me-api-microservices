package com.oxygensened.userprofile.context.company;

import com.oxygensened.userprofile.context.company.dto.CompanyView;
import com.oxygensened.userprofile.domain.Company;
import com.oxygensened.userprofile.domain.CompanyRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyView> getAllCompanies() {
        return companyRepository.findAll()
                                .stream()
                                .map(CompanyView::from)
                                .toList();
    }

    public Company getCompany(String name) {
        return companyRepository.findByName(name).orElse(new Company(name));
    }
}

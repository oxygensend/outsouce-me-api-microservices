package com.oxygensened.userprofile.context.company;

import com.oxygensened.userprofile.context.company.dto.CompanyView;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
class CompanyController {
    private final CompanyService companyService;

    CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    List<CompanyView> getAllCompanies() {
        return companyService.getAllCompanies();
    }

}

package com.oxygensend.joboffer.application.application;

import com.oxygensend.commonspring.DefaultView;
import com.oxygensend.commonspring.PagedListView;
import com.oxygensend.joboffer.application.application.dto.CreateApplicationCommand;
import com.oxygensend.joboffer.application.application.dto.request.ChangeStatusRequest;
import com.oxygensend.joboffer.application.application.dto.request.CreateApplicationRequest;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationInfoView;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationListView;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationStatusView;
import com.oxygensend.joboffer.application.application.dto.view.ApplicationView;
import com.oxygensend.joboffer.application.cache.CacheData;
import com.oxygensend.joboffer.domain.repository.filter.ApplicationFilter;
import com.oxygensend.joboffer.domain.repository.filter.ApplicationSort;
import com.oxygensend.joboffer.domain.repository.filter.SortDirection;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Cacheable(value = CacheData.APPLICATION_CACHE, key = CacheData.APPLICATION_KEY)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationView get(@PathVariable Long id) {
        return applicationService.getApplication(id);
    }

    @Cacheable(value = CacheData.APPLICATION_CACHE, key = CacheData.APPLICATION_INFO_KEY)
    @GetMapping("/{id}/info")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationInfoView getInfo(@PathVariable Long id) {
        return applicationService.getApplicationInfo(id);
    }

    @Cacheable(value = CacheData.APPLICATION_CACHE, key = CacheData.USERS_APPLICATIONS)
    @GetMapping
    public PagedListView<ApplicationListView> paginatedList(@RequestParam(required = true) String userId,
                                                            @RequestParam(required = false, defaultValue = "CREATED_AT") ApplicationSort sort,
                                                            @RequestParam(required = false, defaultValue = "ASC") SortDirection dir,
                                                            Pageable pageable) {
        var filter = ApplicationFilter.builder()
                                      .userId(userId)
                                      .sort(sort)
                                      .dir(dir)
                                      .build();

        return applicationService.getApplicationsByUser(filter, pageable);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    DefaultView create(@RequestPart @Validated @Parameter CreateApplicationRequest request,
                       @RequestPart(required = false) List<MultipartFile> attachments) {
        var command = CreateApplicationCommand.create(request, attachments);
        applicationService.createApplication(command);
        return DefaultView.of("Application was created successfully");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        applicationService.deleteApplication(id);
    }

    @PostMapping("/{id}/change-status")
    @ResponseStatus(HttpStatus.OK)
    ApplicationStatusView changeStatus(@PathVariable Long id,
                                       @RequestBody @Validated ChangeStatusRequest request) {
        applicationService.changeStatus(id, request.status());
        return new ApplicationStatusView(request.status());
    }


}

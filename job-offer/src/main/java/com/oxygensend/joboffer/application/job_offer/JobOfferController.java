package com.oxygensend.joboffer.application.job_offer;

import com.oxygensend.commonspring.PagedListView;
import com.oxygensend.joboffer.application.cache.CacheData;
import com.oxygensend.joboffer.application.job_offer.dto.request.CreateJobOfferRequest;
import com.oxygensend.joboffer.application.job_offer.dto.request.UpdateJobOfferRequest;
import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferDetailsView;
import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferManagementView;
import com.oxygensend.joboffer.application.job_offer.dto.view.JobOfferView;
import com.oxygensend.joboffer.domain.JobOfferSearchResult;
import com.oxygensend.joboffer.domain.entity.part.FormOfEmployment;
import com.oxygensend.joboffer.domain.entity.part.WorkType;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferFilter;
import com.oxygensend.joboffer.domain.repository.filter.JobOfferSort;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/job-offers")
public class JobOfferController {

    private final JobOfferService jobOfferService;


    JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @Cacheable(value = CacheData.JOB_OFFER_CACHE, key = CacheData.JOB_OFFER_KEY)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{slug}")
    public JobOfferDetailsView show(@PathVariable String slug) {
        return jobOfferService.getJobOffer(slug);
    }

    @Cacheable(value = CacheData.JOB_OFFER_CACHE, key = CacheData.JOB_OFFERS_LIST_KEY, cacheManager = "jobOffersCacheManager", unless = "#pageable.pageNumber > 20")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PagedListView<JobOfferView> list(@RequestParam(name = "workTypes", required = false) List<WorkType> workTypes,
                                            @RequestParam(name = "technologies", required = false) List<String> technologies,
                                            @RequestParam(name = "address.postCode", required = false) String postCode,
                                            @RequestParam(name = "address.city", required = false) String city,
                                            @RequestParam(name = "formOfEmployments", required = false) List<FormOfEmployment> formOfEmployments,
                                            @RequestParam(name = "archived", required = false) Boolean archived,
                                            @RequestParam(name = "userId", required = false) String userId,
                                            @RequestParam(name = "sort", required = false, defaultValue = "NEWEST") JobOfferSort sort,
                                            Pageable pageable) {

        var filter = JobOfferFilter.builder()
                                   .workTypes(workTypes)
                                   .technologies(technologies)
                                   .formOfEmployments(formOfEmployments)
                                   .postCode(postCode)
                                   .city(city)
                                   .archived(archived)
                                   .userId(userId)
                                   .sort(sort)
                                   .build();

        return jobOfferService.getPaginatedJobOffers(filter, pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{slug}/management")
    JobOfferManagementView managementInfo(@PathVariable String slug) {
        return jobOfferService.getJobOfferManagement(slug);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    JobOfferDetailsView create(@Validated @RequestBody CreateJobOfferRequest request) {
        return jobOfferService.createJobOffer(request.toCommand());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{slug}/add-redirect")
    void addRedirect(@PathVariable String slug) {
        jobOfferService.addRedirect(slug);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{slug}")
    void delete(@PathVariable String slug) {
        jobOfferService.deleteJobOffer(slug);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{slug}")
    JobOfferDetailsView update(@PathVariable String slug, @Validated @RequestBody UpdateJobOfferRequest request) {
        return jobOfferService.updateJobOffer(slug, request);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    PagedListView<JobOfferSearchResult> search(@RequestParam String query, Pageable pageable) {
        return jobOfferService.search(query, pageable);
    }


}

package com.oxygensend.joboffer.application.job_offer.dto.view;

import com.oxygensend.joboffer.application.application.dto.view.ApplicationManagementView;
import com.oxygensend.joboffer.application.user.dto.view.UserViewFactory;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import org.springframework.stereotype.Component;

@Component
public class JobOfferViewFactory {

    private final UserViewFactory userViewFactory;

    public JobOfferViewFactory(UserViewFactory userViewFactory) {
        this.userViewFactory = userViewFactory;
    }

    public JobOfferDetailsView create(JobOffer jobOffer) {
        var userView = userViewFactory.createUserView(jobOffer.user());
        var salaryRangeView = jobOffer.salaryRange() != null ? SalaryRangeView.from(jobOffer.salaryRange()) : null;
        var addressView = jobOffer.address() != null ? AddressView.from(jobOffer.address()) : null;

        return new JobOfferDetailsView(jobOffer.id(),
                                       jobOffer.slug(),
                                       jobOffer.name(),
                                       userView,
                                       jobOffer.description(),
                                       jobOffer.workTypes(),
                                       jobOffer.experience(),
                                       jobOffer.formOfEmployment(),
                                       jobOffer.technologies().stream().toList(),
                                       jobOffer.numberOfApplications(),
                                       salaryRangeView,
                                       addressView,
                                       jobOffer.createdAt(),
                                       jobOffer.validTo());
    }

    public JobOfferView createInfo(JobOffer jobOffer) {
        var userView = userViewFactory.createUserView(jobOffer.user());
        return new JobOfferView(jobOffer.id(),
                                jobOffer.slug(),
                                jobOffer.name(),
                                jobOffer.description(),
                                jobOffer.shortDescription(),
                                jobOffer.numberOfApplications(),
                                userView,
                                jobOffer.archived());
    }

    public JobOfferWithUserView createJobOfferWithUserView(JobOffer jobOffer) {
        var userView = userViewFactory.createBaseUserView(jobOffer.user());
        return new JobOfferWithUserView(jobOffer.id(),
                                        jobOffer.slug(),
                                        jobOffer.name(),
                                        userView);
    }

    public BaseJobOfferView createBaseJobOfferView(JobOffer jobOffer) {
        return new BaseJobOfferView(jobOffer.id(),
                                    jobOffer.slug(),
                                    jobOffer.name());
    }

    public PrincipleJobOfferView createPrincipleJobOfferView(JobOffer jobOffer) {
        return new PrincipleJobOfferView(jobOffer.id(),
                                         jobOffer.slug(),
                                         jobOffer.name(),
                                         jobOffer.description(),
                                         jobOffer.shortDescription(),
                                         jobOffer.numberOfApplications());
    }

    public JobOfferManagementView createJobOfferManagementView(JobOffer jobOffer) {
        var salaryRangeView = jobOffer.salaryRange() != null ? SalaryRangeView.from(jobOffer.salaryRange()) : null;
        var addressView = jobOffer.address() != null ? AddressView.withCoords(jobOffer.address()) : null;
        var applications = jobOffer.applications().stream().map(ApplicationManagementView::from).toList();

        return new JobOfferManagementView(jobOffer.id(),
                                          jobOffer.slug(),
                                          jobOffer.name(),
                                          jobOffer.description(),
                                          jobOffer.workTypes(),
                                          jobOffer.experience(),
                                          jobOffer.formOfEmployment(),
                                          jobOffer.technologies().stream().toList(),
                                          jobOffer.numberOfApplications(),
                                          salaryRangeView,
                                          addressView,
                                          jobOffer.createdAt(),
                                          jobOffer.validTo() != null ? jobOffer.validTo().toLocalDate() : null,
                                          jobOffer.redirectCount(),
                                          applications,
                                          jobOffer.archived());
    }

    public JobOfferInfoView createJobOfferInfoView(JobOffer jobOffer) {
        return new JobOfferInfoView(jobOffer.id(),
                                    jobOffer.slug(),
                                    jobOffer.name(),
                                    jobOffer.numberOfApplications(),
                                    jobOffer.validTo() != null ? jobOffer.validTo().toLocalDate() : null,
                                    jobOffer.archived());
    }

    public JobOfferOrderView createJobOfferOrderView(JobOffer jobOffer) {
        return new JobOfferOrderView(jobOffer.id(),
                                     jobOffer.experience(),
                                     jobOffer.technologies().stream().toList(),
                                     jobOffer.address() != null ? jobOffer.address().lon() : null,
                                     jobOffer.address() != null ? jobOffer.address().lat() : null);

    }
}

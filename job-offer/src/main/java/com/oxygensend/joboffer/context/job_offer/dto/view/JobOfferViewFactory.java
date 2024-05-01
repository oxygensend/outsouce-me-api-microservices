package com.oxygensend.joboffer.context.job_offer.dto.view;

import com.oxygensend.joboffer.context.user.dto.view.UserViewFactory;
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
        var salaryRangeView = SalaryRangeView.from(jobOffer.salaryRange());
        var addressView = AddressView.from(jobOffer.address());

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
                                userView);
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

}

package com.oxygensend.joboffer.context.job_offer;

import com.oxygensend.joboffer.config.properties.JobOffersProperties;
import com.oxygensend.joboffer.context.job_offer.dto.AddressView;
import com.oxygensend.joboffer.context.job_offer.dto.JobOfferView;
import com.oxygensend.joboffer.context.job_offer.dto.JobOfferDetailsView;
import com.oxygensend.joboffer.context.job_offer.dto.SalaryRangeView;
import com.oxygensend.joboffer.context.job_offer.dto.UserView;
import com.oxygensend.joboffer.domain.entity.JobOffer;
import com.oxygensend.joboffer.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class JobOfferViewFactory {
    private final String userThumbnailServerUrl;

    public JobOfferViewFactory(JobOffersProperties properties) {
        this.userThumbnailServerUrl = properties.userThumbnailServerUrl();
    }


    public JobOfferDetailsView create(JobOffer jobOffer) {
        var userView = createUserView(jobOffer.user());
        var salaryRangeView = SalaryRangeView.from(jobOffer.salaryRange());
        var addressView = AddressView.from(jobOffer.address());

        return new JobOfferDetailsView(jobOffer.id(),
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
                                       userView,
                                       jobOffer.createdAt());
    }

    public JobOfferView createInfo(JobOffer jobOffer) {
        var userView = createUserView(jobOffer.user());
        return new JobOfferView(jobOffer.id(),
                                jobOffer.slug(),
                                jobOffer.name(),
                                jobOffer.description(),
                                jobOffer.shortDescription(),
                                userView);
    }

    private UserView createUserView(User user) {
        return new UserView(user.id(),
                            user.fullName(),
                            user.thumbnailPath(userThumbnailServerUrl),
                            user.activeJobPosition());
    }
}

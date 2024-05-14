package com.oxygensend.joboffer.context.application.dto.view;

import com.oxygensend.joboffer.domain.entity.Application;
import com.oxygensend.joboffer.domain.entity.part.ApplicationStatus;
import java.time.LocalDateTime;

public record ApplicationManagementView(Long id,
                                        ApplicationStatus status,
                                        String applierFullName,
                                        LocalDateTime createdAt) {

    public static ApplicationManagementView from(Application application) {
        return new ApplicationManagementView(application.id(), application.status(), application.user().fullName(), application.createdAt());
    }
}

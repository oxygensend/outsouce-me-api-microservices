package com.oxygensened.userprofile.application.profile;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
final class UserScheduler {

    private final UserAdminService userAdminService;

    UserScheduler(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @Scheduled(cron = "${user-profile.developers-popularity-rate-recalculation-cron}")
    void scheduleDevelopersPopularityRateRecalculation() {
        userAdminService.updateDevelopersPopularityRate();
    }
}

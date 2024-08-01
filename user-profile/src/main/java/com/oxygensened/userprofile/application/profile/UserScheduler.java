package com.oxygensened.userprofile.application.profile;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
public class UserScheduler {

    private final UserAdminService userAdminService;

    UserScheduler(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @Scheduled(cron = "${user-profile.developers-popularity-rate-recalculation-cron}")
    @SchedulerLock(name = "developersPopularityRateRecalculation")
    void scheduleDevelopersPopularityRateRecalculation() {
        userAdminService.updateDevelopersPopularityRate();
    }
}

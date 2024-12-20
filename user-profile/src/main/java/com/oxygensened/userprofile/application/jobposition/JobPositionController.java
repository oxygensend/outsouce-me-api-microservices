package com.oxygensened.userprofile.application.jobposition;

import com.oxygensened.userprofile.application.cache.CacheData;
import com.oxygensened.userprofile.application.jobposition.dto.request.CreateJobPositionRequest;
import com.oxygensened.userprofile.application.jobposition.dto.view.JobPositionView;
import com.oxygensened.userprofile.application.jobposition.dto.request.UpdateJobPositionRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Job Position")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
class JobPositionController {
    private final JobPositionService jobPositionService;

    JobPositionController(JobPositionService jobPositionService) {
        this.jobPositionService = jobPositionService;
    }

    @PostMapping("/{userId}/job-positions")
    JobPositionView create(@PathVariable Long userId,
                           @Validated @RequestBody CreateJobPositionRequest request) {
        return jobPositionService.createJobPosition(userId, request);
    }

    @PatchMapping("/{userId}/job-positions/{jobPositionId}")
    JobPositionView update(@PathVariable Long userId,
                           @PathVariable Long jobPositionId,
                           @Validated @RequestBody UpdateJobPositionRequest request) {
        return jobPositionService.updateJobPosition(userId, jobPositionId, request);
    }

    @DeleteMapping("/{userId}/job-positions/{jobPositionId}")
    void delete(@PathVariable Long userId, @PathVariable Long jobPositionId) {
        jobPositionService.deleteJobPosition(userId, jobPositionId);
    }


    @Cacheable(value = CacheData.JOB_POSITION_CACHE, key = "#userId")
    @GetMapping("/{userId}/job-positions")
    public List<JobPositionView> list(@PathVariable Long userId) {
        return jobPositionService.getJobPositions(userId);
    }
}

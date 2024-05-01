package com.oxygensened.userprofile.context.technology;

import com.oxygensened.userprofile.context.technology.dto.TechnologyRequest;
import com.oxygensened.userprofile.context.technology.dto.TechnologyView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Technology")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class TechnologyController {
    private final TechnologyService technologyService;

    TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @PostMapping("/{userId}/technologies")
    TechnologyView add(@PathVariable Long userId,
                       @Validated @RequestBody TechnologyRequest request) {
        return technologyService.addTechnology(userId, request);
    }

    @DeleteMapping("/{userId}/technologies")
    void delete(@PathVariable Long userId,
                @Validated @RequestBody TechnologyRequest request) {
        technologyService.deleteTechnology(userId, request);
    }

}

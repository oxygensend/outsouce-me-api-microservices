package com.oxygensened.userprofile.application.technology;

import com.oxygensened.userprofile.application.technology.dto.AddTechnologiesRequest;
import com.oxygensened.userprofile.application.technology.dto.TechnologyRequest;
import com.oxygensened.userprofile.application.technology.dto.TechnologyView;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/technologies")
    void delete(@PathVariable Long userId,
                @RequestParam(required = true, name = "name") String name) {
        technologyService.deleteTechnology(userId, name);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{userId}/technologies/addAll")
    void add(@PathVariable Long userId,
             @Validated @RequestBody AddTechnologiesRequest request) {
        technologyService.addTechnologies(userId, request);
    }

}

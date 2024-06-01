package com.oxygensened.userprofile.application.language;

import com.oxygensened.userprofile.application.cache.CacheData;
import com.oxygensened.userprofile.application.language.dto.request.CreateLanguageRequest;
import com.oxygensened.userprofile.application.language.dto.request.UpdateLanguageRequest;
import com.oxygensened.userprofile.application.language.dto.view.LanguageView;
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


@Tag(name = "Language")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @Cacheable(value = CacheData.LANGUAGE_CACHE, key = "#userId")
    @GetMapping("/{userId}/languages")
    public List<LanguageView> list(@PathVariable Long userId) {
        return languageService.getLanguages(userId);
    }

    @PostMapping("/{userId}/languages")
    LanguageView create(@PathVariable Long userId,
                        @Validated @RequestBody CreateLanguageRequest request) {
        return languageService.createLanguage(userId, request);
    }

    @PatchMapping("/{userId}/languages/{languageId}")
    LanguageView update(@PathVariable Long userId,
                        @PathVariable Long languageId,
                        @Validated @RequestBody UpdateLanguageRequest request) {
        return languageService.updateLanguage(userId, languageId, request);
    }

    @DeleteMapping("/{userId}/languages/{languageId}")
    void delete(@PathVariable Long userId, @PathVariable Long languageId) {
        languageService.deleteLanguage(userId, languageId);
    }

}

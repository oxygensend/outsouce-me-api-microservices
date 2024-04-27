package com.oxygensened.userprofile.context.profile.dto.request;

import com.oxygensened.userprofile.context.profile.dto.AddressDto;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import org.openapitools.jackson.nullable.JsonNullable;

public record UserDetailsRequest(@NotBlank JsonNullable<String> name,
                                 @NotBlank JsonNullable<String> surname,
                                 @NotBlank JsonNullable<String> phoneNumber,
                                 @NotBlank JsonNullable<String> description,
                                 @NotBlank JsonNullable<String> githubUrl,
                                 @NotBlank JsonNullable<String> linkedinUrl,
                                 @NotBlank JsonNullable<LocalDate> dateOfBirth,
                                 @Valid JsonNullable<AddressDto> addressDto,
                                 Boolean lookingForJob,
                                 JsonNullable<Experience> experience) {
}

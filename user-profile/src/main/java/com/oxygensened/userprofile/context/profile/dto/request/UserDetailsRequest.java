package com.oxygensened.userprofile.context.profile.dto.request;

import com.oxygensened.userprofile.context.profile.dto.AddressDto;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.openapitools.jackson.nullable.JsonNullable;

public record UserDetailsRequest(@NotBlank JsonNullable<String> name,
                                 @NotBlank JsonNullable<String> surname,
                                 @NotBlank JsonNullable<String> phoneNumber,
                                 @Size(min = 3, max = 1028, message = "Description length must be between 3 and 1028 characters")
                                 @NotBlank JsonNullable<String> description,
                                 @NotBlank JsonNullable<String> githubUrl,
                                 @NotBlank JsonNullable<String> linkedinUrl,
                                 @NotBlank JsonNullable<LocalDate> dateOfBirth,
                                 @Valid JsonNullable<AddressDto> addressDto,
                                 Boolean lookingForJob,
                                 JsonNullable<Experience> experience) {
}

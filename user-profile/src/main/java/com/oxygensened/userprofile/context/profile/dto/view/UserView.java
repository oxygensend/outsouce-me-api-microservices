package com.oxygensened.userprofile.context.profile.dto.view;

import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.entity.part.AccountType;
import com.oxygensened.userprofile.domain.entity.part.Experience;
import java.time.LocalDate;
import java.util.List;

public record UserView(String email,
                       String id,
                       String name,
                       String surname,
                       String fullName,
                       String phoneNumber,
                       String description,
                       String linkedinUrl,
                       String githubUrl,
                       LocalDate dateOfBirth,
                       AccountType accountType,
                       AddressView address,
                       String activeJobPosition,
                       double opinionsRate,
                       double opinionCount,
                       String imagePath,
                       List<String> technologies,
                       Experience experience
) {


    public static UserView from(User user) {
        var address = user.address() != null ? AddressView.from(user.address()) : null;
        return new UserView(user.email(), user.id().toString(), user.name(), user.surname(), user.fullName(), user.phoneNumber(), user.description(),
                            user.linkedinUrl(), user.githubUrl(), user.dateOfBirth(), user.accountType(), address, user.activeJobPosition(),
                            user.opinionsRate(), user.opinions().size(), user.imageName(), user.technologies().stream().toList(), user.experience());
    }
}

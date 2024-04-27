package com.oxygensend.joboffer.context.application.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record CreateApplicationCommand(String userId,
                                       Long jobOfferId,
                                       String description,
                                       List<MultipartFile> attachments) {

    public static CreateApplicationCommand create(CreateApplicationRequest request, List<MultipartFile> attachments) {
        return new CreateApplicationCommand(request.userId(), request.jobOfferId(), request.description(), attachments);
    }
}

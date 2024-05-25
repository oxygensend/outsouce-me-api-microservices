package com.oxygensend.joboffer.application.user.dto.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseUserView {
    public final String id;
    public final String fullName;
    public final String thumbnailPath;

    @JsonCreator
    public BaseUserView(@JsonProperty("id") String id, @JsonProperty("fullName") String fullName, @JsonProperty("thumbnailPath") String thumbnailPath) {
        this.id = id;
        this.fullName = fullName;
        this.thumbnailPath = thumbnailPath;
    }

}

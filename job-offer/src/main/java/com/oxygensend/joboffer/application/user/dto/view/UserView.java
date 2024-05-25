package com.oxygensend.joboffer.application.user.dto.view;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserView extends BaseUserView {
    public final String activeJobPosition;

    @JsonCreator
    public UserView(@JsonProperty("id") String id, @JsonProperty("fullName") String fullName, @JsonProperty("thumbnailPath") String thumbnailPath,
                    @JsonProperty("activeJobPosition") String activeJobPosition) {
        super(id, fullName, thumbnailPath);
        this.activeJobPosition = activeJobPosition;
    }
}

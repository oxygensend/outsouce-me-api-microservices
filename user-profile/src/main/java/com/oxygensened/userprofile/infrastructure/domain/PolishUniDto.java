package com.oxygensened.userprofile.infrastructure.domain;

import java.util.List;

public record PolishUniDto(List<Institution> institutions) {

    public record Institution(String uid,
                              String name,
                              String status) {
    }

}
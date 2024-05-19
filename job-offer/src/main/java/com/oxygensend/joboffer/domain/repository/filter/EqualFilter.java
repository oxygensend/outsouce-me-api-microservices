package com.oxygensend.joboffer.domain.repository.filter;

import java.util.Objects;

final class EqualFilter implements Filter {

    private final Object field;

    EqualFilter(Object field) {
        this.field = field;
    }

    @Override
    public boolean match(Object object) {
        return Objects.equals(field, object);
    }
}

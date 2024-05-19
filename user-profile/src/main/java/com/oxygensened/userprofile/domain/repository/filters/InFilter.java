package com.oxygensened.userprofile.domain.repository.filters;

import java.util.List;

final class InFilter<T> implements Filter {
    private final List<T> values;

    public InFilter(List<T> values) {
        this.values = values;
    }

    @Override
    public boolean match(Object object) {
        return values.contains((T) object);
    }
}

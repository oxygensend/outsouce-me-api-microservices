package com.oxygensened.userprofile.domain.service;

import com.oxygensened.userprofile.domain.entity.User;
import com.oxygensened.userprofile.domain.repository.filters.UserFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DevelopersForYou {

    Page<User> getForUser(Long userId, UserFilter userFilter, Pageable pageable);
}

package com.oxygensend.joboffer.infrastructure.redis;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("globalCache")
public @interface GlobalCache {
}

package com.oxygensend.joboffer.infrastructure.redis;

public class CacheNotAvailableException extends RuntimeException{
    public CacheNotAvailableException(String cache){
        super("Cache not found: " + cache);
    }
}

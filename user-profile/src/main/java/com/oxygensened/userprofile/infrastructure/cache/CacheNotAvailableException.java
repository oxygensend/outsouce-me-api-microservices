package com.oxygensened.userprofile.infrastructure.cache;

public class CacheNotAvailableException extends RuntimeException{
    public CacheNotAvailableException(String cache){
        super("Cache not found: " + cache);
    }
}

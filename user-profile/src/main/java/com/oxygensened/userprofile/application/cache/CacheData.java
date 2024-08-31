package com.oxygensened.userprofile.application.cache;

import java.util.List;
import java.util.Map;

public class CacheData {
    public static final String USER_CACHE = "users";
    public static final String DICTIONARY_CACHE = "dictionaries";
    public static final String EDUCATION_CACHE = "educations";
    public static final String JOB_POSITION_CACHE = "job-positions";
    public static final String LANGUAGE_CACHE = "languages";
    public static final Map<String, Map<CacheLayer, List<String>>> KEYS = Map.of(
            USER_CACHE, Map.of(
                    CacheLayer.DETAILS, List.of("%s"),
                    CacheLayer.LIST, List.of("developers-%s")
            ),
            EDUCATION_CACHE, Map.of(
                    CacheLayer.LIST, List.of("%s")
            ),
            JOB_POSITION_CACHE, Map.of(
                    CacheLayer.LIST, List.of("%s")
            ),
            LANGUAGE_CACHE, Map.of(
                    CacheLayer.LIST, List.of("%s")
            )
    );
    public static final String USER_KEY = "#id";
    public static final String THUMBNAIL_KEY = "#filename";
    public static final String THUMBNAIL_BY_USER_ID_KEY = "'userId-' + #id";
    public static final String DEVELOPERS_KEY = "'developers-' + #sort + '-' + #technologies + '-' + #postCode + '-' + #city + '-' + #experience + "
            + "'-' + #pageable.pageSize + '-' + #pageable.pageNumber";


    private CacheData() {
    }


    public enum CacheLayer {
        DETAILS, LIST
    }
}

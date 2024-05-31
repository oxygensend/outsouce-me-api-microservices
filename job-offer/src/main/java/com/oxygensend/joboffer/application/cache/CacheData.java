package com.oxygensend.joboffer.application.cache;

import java.util.List;
import java.util.Map;

import static com.oxygensend.joboffer.application.cache.CacheData.CacheLayer.DETAILS;
import static com.oxygensend.joboffer.application.cache.CacheData.CacheLayer.LIST;

public class CacheData {
    public static final String APPLICATION_CACHE = "applications";
    public static final String JOB_OFFER_CACHE = "job-offers";
    public static final Map<String, Map<CacheLayer, List<String>>> KEYS = Map.of(
            JOB_OFFER_CACHE, Map.of(
                    DETAILS, List.of("%s"),
                    LIST, List.of("list", "users-%s")
            ),
            APPLICATION_CACHE, Map.of(
                    DETAILS, List.of("%s", "%s-info"),
                    LIST, List.of("list-%s")
            )
    );
    public static final String APPLICATION_INFO_KEY = "#id + '-info'";
    public static final String APPLICATION_KEY = "#id";
    public static final String USERS_APPLICATIONS = "'list-' + #userId + '-' + #sort + '-' + #dir + '-' + #pageable.pageNumber +  '-' "
            + "+ #pageable.pageSize";
    public static final String JOB_OFFER_KEY = "#slug";
    public static final String USERS_JOB_OFFERS = "'users-' + #id + '-' +  #archived";
    public static final String JOB_OFFERS_LIST_KEY = "'list-' + #workTypes + '-' + #technologies + '-' + #postCode + '-' + #city "
            + "+ '-' + #formOfEmployments + '-' + #archived + '-' + #userId + '-' + #sort + '-' + #pageable.pageSize + '-' + #pageable.pageNumber";

    private CacheData() {
    }


    public enum CacheLayer {
        DETAILS, LIST
    }
}

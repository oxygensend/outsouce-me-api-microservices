package com.oxygensened.userprofile.context;

import java.util.Map;

public interface UserProfileProperties {
    String serviceId();

    Map<Topics, String> topics();

    String thumbnailServerUrl();

    String plUniversitiesSourceUrl();


}

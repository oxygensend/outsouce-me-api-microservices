package com.oxygensend.opinions.application

import com.oxygensend.opinions.domain.event.Topics

interface OpinionsProperties {

    val topics: Map<Topics, String>
}
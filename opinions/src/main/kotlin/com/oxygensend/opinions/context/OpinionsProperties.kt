package com.oxygensend.opinions.context

import com.oxygensend.opinions.domain.event.Topics

interface OpinionsProperties {

    val topics: Map<Topics, String>
}
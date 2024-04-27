package com.oxygensend.staticdata.infrastructure.parser

import com.oxygensend.staticdata.config.StaticDataProperties
import com.oxygensend.staticdata.context.address.parser.AddressParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PolishAddressesConfiguration {
    @Bean
    fun polishAddressParser(staticDataProperties: StaticDataProperties): AddressParser {
        return PolishAddressesParser(staticDataProperties.postalCodeDataUrl!!)
    }

}
package com.oxygensend.staticdata.infrastructure.parser

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
internal class PolishAddressesParserTest {

    private lateinit var parser: PolishAddressesParser


    @BeforeEach
    fun setUp() {
        parser = PolishAddressesParser("https://kody-pocztowe.dokladnie.com/kody.csv.zip")
    }

    @Test
    fun getAddresses() {
        var x = parser.getAddresses()
    }
}
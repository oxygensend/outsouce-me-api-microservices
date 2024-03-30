package com.oxygensend.staticdata.context.controller

import com.oxygensend.staticdata.context.dto.AddressView
import com.oxygensend.staticdata.context.service.AddressService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Address")
@RestController
@RequestMapping("/api/v1/static-data")
class AddressController(private val addressService: AddressService) {

    @GetMapping("/addresses")
    fun getAddresses(@RequestParam(required = false) search: String?): List<AddressView> = addressService.findAllAddresses(search)
}
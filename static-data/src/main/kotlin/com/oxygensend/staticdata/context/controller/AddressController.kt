package com.oxygensend.staticdata.context.controller

import com.oxygensend.commons_jdk.DefaultView
import com.oxygensend.staticdata.context.dto.AddressView
import com.oxygensend.staticdata.context.service.AddressService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Address")
@RestController
@RequestMapping("/api/v1/static-data/addresses")
class AddressController(private val addressService: AddressService) {

    @GetMapping("")
    fun getAddresses(@RequestParam(required = false) search: String?): List<AddressView> = addressService.findAllAddresses(search)


    @PostMapping("/load")
    suspend fun loadAddresses(): DefaultView {
        addressService.loadAddresses()
        return DefaultView.of("Loading started")
    }

    @PostMapping("/load/forceStop")
    fun loadForceStop(): DefaultView {

        addressService.forceStop()
        return DefaultView.of("Loading successfully stoped")
    }

}
package com.oxygensend.staticdata.context.address

import com.oxygensend.commons_jdk.DefaultView
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Address")
@RestController
@RequestMapping("/api/v1/static-data/addresses")
internal class AddressController(private val addressService: AddressService) {

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
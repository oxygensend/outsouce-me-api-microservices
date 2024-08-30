package com.oxygensend.staticdata.application.address

import com.oxygensend.commonspring.DefaultView
import com.oxygensend.staticdata.application.PrivilegeChecker
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Address")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/static-data/addresses")
internal class AddressController(
    private val addressService: AddressService,
    private val privilegeChecker: PrivilegeChecker
) {

    @GetMapping("")
    fun getAddresses(@RequestParam(required = false) search: String?): List<AddressView> =
        addressService.findAllAddresses(search)


    @GetMapping("/with-postal-codes")
    fun getAddressesWithPostalCodes(@RequestParam(required = false) search: String?): List<AddressWithPostalCodesView> =
        addressService.findAllAddressesWithPostCodes(search)


    @PostMapping("/load")
    suspend fun loadAddresses(): DefaultView {
        privilegeChecker.checkEditorPrivileges();
        addressService.loadAddresses()
        return DefaultView.of("Loading started")
    }

    @PostMapping("/load/forceStop")
    fun loadForceStop(): DefaultView {
        privilegeChecker.checkEditorPrivileges();
        addressService.forceStop()
        return DefaultView.of("Loading successfully stoped")
    }

}
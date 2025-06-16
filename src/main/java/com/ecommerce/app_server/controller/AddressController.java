package com.ecommerce.app_server.controller;

import com.ecommerce.app_server.model.User;
import com.ecommerce.app_server.payload.AddressDTO;
import com.ecommerce.app_server.service.AddressService;
import com.ecommerce.app_server.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){
        List<AddressDTO> addressListDTO = addressService.getAllAddresses();
        return new ResponseEntity<>(addressListDTO, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddresseById(@PathVariable Long addressId){
        AddressDTO addressListDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressListDTO, HttpStatus.OK);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses(){
        User user = authUtil.loggedInUser();
        List<AddressDTO> addressListDTO = addressService.getUserAddresses(user);
        return new ResponseEntity<>(addressListDTO, HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddresseById(@PathVariable Long addressId, @Valid @RequestBody AddressDTO addressDTO){
        AddressDTO updatedAddress = addressService.updateAddressById(addressId, addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId) {
        String status = addressService.deleteAddressById(addressId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}

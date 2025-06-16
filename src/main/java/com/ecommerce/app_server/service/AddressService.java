package com.ecommerce.app_server.service;

import com.ecommerce.app_server.model.User;
import com.ecommerce.app_server.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);
    List<AddressDTO> getAllAddresses();
    AddressDTO getAddressById(Long addressId);
    List<AddressDTO> getUserAddresses(User user);
    AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO);
    String deleteAddressById(Long addressId);
}

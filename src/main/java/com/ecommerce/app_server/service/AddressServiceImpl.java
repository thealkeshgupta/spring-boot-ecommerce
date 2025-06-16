package com.ecommerce.app_server.service;

import com.ecommerce.app_server.exception.ResourceNotFoundException;
import com.ecommerce.app_server.model.Address;
import com.ecommerce.app_server.model.User;
import com.ecommerce.app_server.payload.AddressDTO;
import com.ecommerce.app_server.repository.AddressRepository;
import com.ecommerce.app_server.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address = modelMapper.map(addressDTO, Address.class);


        List<Address> addressList = user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);

        address.setUser(user);

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<Address> addressList = addressRepository.findAll();
        List<AddressDTO> addressDTOs = addressList.stream().map(address -> {
            return modelMapper.map(address, AddressDTO.class);
        }).toList();
        return addressDTOs;
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddresses(User user) {
        List<Address> addressList = user.getAddresses();
        List<AddressDTO> addressDTOs = addressList.stream().map(address -> {
            return modelMapper.map(address, AddressDTO.class);
        }).toList();
        return addressDTOs;
    }

    @Override
    public AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO) {
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        existingAddress.setBuildingName(addressDTO.getBuildingName());
        existingAddress.setStreet(addressDTO.getStreet());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setState(addressDTO.getState());
        existingAddress.setCountry(addressDTO.getCountry());
        existingAddress.setPincode(addressDTO.getPincode());

        Address updatedAddress = addressRepository.save(existingAddress);

        User user = existingAddress.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(existingAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->  new ResourceNotFoundException("Address", "addressId", addressId));

        User user = address.getUser();
        user.getAddresses().removeIf(item -> item.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(address);
        return "Address deleted successfully with addressId: " + addressId ;
    }
}

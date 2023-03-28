package com.example.demo.ui.controller;

import com.example.demo.service.implementation.UserServiceImpl;
import com.example.demo.shared.dto.AddressDTO;
import com.example.demo.shared.dto.UserDTO;
import com.example.demo.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDTO userDTO;
    final String USER_ID = "asdfghjkl";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDTO = new UserDTO();
        userDTO.setFirstName("Toma");
        userDTO.setLastName("Andrei");
        userDTO.setEmail("tomaandrei98@gmail.com");
        userDTO.setEmailVerificationStatus(Boolean.FALSE);
        userDTO.setEmailVerificationToken(null);
        userDTO.setUserId(USER_ID);
        userDTO.setAddresses(getAddressesDTO());
        userDTO.setEncryptedPassword("qwertyuiop");

    }

    @Test
    public void testGetUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDTO);

        UserRest userRest = userController.getUser(USER_ID);

        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(userDTO.getFirstName(), userRest.getFirstName());
        assertEquals(userDTO.getLastName(), userRest.getLastName());
        assertTrue(userDTO.getAddresses().size() == userRest.getAddresses().size());
    }

    private List<AddressDTO> getAddressesDTO() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setType("shipping");
        addressDTO.setCity("Vancouver");
        addressDTO.setCountry("Canada");
        addressDTO.setPostalCode("ABC123");
        addressDTO.setStreetName("123 Street Name");

        AddressDTO billingAddressDTO = new AddressDTO();
        billingAddressDTO.setType("shipping");
        billingAddressDTO.setCity("Vancouver");
        billingAddressDTO.setCountry("Canada");
        billingAddressDTO.setPostalCode("ABC123");
        billingAddressDTO.setStreetName("123 Street Name");

        List<AddressDTO> addresses = new ArrayList<>();
        addresses.add(addressDTO);
        addresses.add(billingAddressDTO);

        return addresses;
    }
}
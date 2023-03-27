package com.example.demo.service.implementation;

import com.example.demo.io.entity.AddressEntity;
import com.example.demo.io.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.shared.Utils;
import com.example.demo.shared.dto.AddressDTO;
import com.example.demo.shared.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    Utils utils;

    String userId = "hhty57ehfy";
    String encryptedPassword = "74bjhbvjh8474";

    UserEntity userEntity;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("Toma");
        userEntity.setLastName("Andrei");
        userEntity.setEmail("tomaandrei98@gmail.com");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(encryptedPassword);
        userEntity.setAddresses(getAddressesEntity());
    }

    @Test
    public void testGetUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDTO userDTO = userService.getUser("any string");
        assertNotNull(userDTO);

        assertEquals("Toma", userDTO.getFirstName());
    }

    @Test
    public void getUserNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(RuntimeException.class,
                () -> userService.getUser("any string"));
    }


    @Test
    public void testCreateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("qwertyuiop");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDTO userDTO = new UserDTO();
        userDTO.setAddresses(getAddressesDTO());
        userDTO.setFirstName("Toma");
        userDTO.setLastName("Andrei");
        userDTO.setPassword("123");
        userDTO.setEmail("tomaandrei98@gmail.com");

        UserDTO storedUserDetails = userService.createUser(userDTO);
        assertNotNull(storedUserDetails);

        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertNotNull(storedUserDetails.getUserId());

        assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
        verify(utils, times(2)).generateAddressId(10);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testCreateUserException() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDTO userDTO = new UserDTO();
        userDTO.setAddresses(getAddressesDTO());
        userDTO.setFirstName("Toma");
        userDTO.setLastName("Andrei");
        userDTO.setPassword("123");
        userDTO.setEmail("tomaandrei98@gmail.com");

        assertThrows(RuntimeException.class,
                () -> userService.createUser(userDTO));
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

    private List<AddressEntity> getAddressesEntity() {
        List<AddressDTO> addresses = getAddressesDTO();

        Type listType = new TypeToken<List<AddressEntity>>() {}.getType();

        return new ModelMapper().map(addresses, listType);
    }
}
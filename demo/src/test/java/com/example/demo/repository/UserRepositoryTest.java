package com.example.demo.repository;

import com.example.demo.io.entity.AddressEntity;
import com.example.demo.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static boolean recordsCreated = false;

    @BeforeEach
    void setUp() {
        if (!recordsCreated) {
            createRecords();
        }
    }

    private void createRecords() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Toma");
        userEntity.setLastName("Andrei");
        userEntity.setUserId("1a2b3c");
        userEntity.setEncryptedPassword("xxx");
        userEntity.setEmail("tomaandrei98@gmail.com");
        userEntity.setEmailVerificationStatus(true);

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("shipping");
        addressEntity.setAddressId("qwertyuio");
        addressEntity.setCity("Vancouver");
        addressEntity.setCountry("Canada");
        addressEntity.setPostalCode("abccba");
        addressEntity.setStreetName("123 Street Address");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);

        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Toma");
        userEntity2.setLastName("Andrei");
        userEntity2.setUserId("adfgh");
        userEntity2.setEncryptedPassword("xxx");
        userEntity2.setEmail("tomaandrei98@gmail.com");
        userEntity2.setEmailVerificationStatus(true);

        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setType("shipping");
        addressEntity2.setAddressId("qwertyuio");
        addressEntity2.setCity("Vancouver");
        addressEntity2.setCountry("Canada");
        addressEntity2.setPostalCode("abccba");
        addressEntity2.setStreetName("123 Street Address");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses.add(addressEntity2);

        userEntity2.setAddresses(addresses2);

        userRepository.save(userEntity2);

        recordsCreated = true;
    }

    @Test
    void testGetVerifiedUsers() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);

        assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 2);
    }

    @Test
    void testFindUserByFirstName() {
        String firstName = "Toma";
        List<UserEntity> users = userRepository.findUserByFirstName(firstName);

        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity userEntity = users.get(0);
        assertTrue(userEntity.getFirstName().equals(firstName));
    }

    @Test
    void testFindUserByLastName() {
        String lastName = "Andrei";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);

        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity userEntity = users.get(0);
        assertTrue(userEntity.getLastName().equals(lastName));
    }

    @Test
    void testFindUsersByKeyword() {
        String keyword = "And";
        List<UserEntity> users = userRepository.findUsersByKeyword(keyword);

        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity userEntity = users.get(0);
        assertTrue(userEntity.getLastName().contains(keyword) || userEntity.getFirstName().contains(keyword));
    }

    @Test
    void testFindUserFirstNameAndLastNameByKeyword() {
        String keyword = "ndr";
        List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);

        assertNotNull(users);
        assertTrue(users.size() == 2);

        Object[] userEntity = users.get(0);

        assertTrue(userEntity.length == 2);

        String userFirstName = String.valueOf(userEntity[0]);
        String userLastName = String.valueOf(userEntity[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);
    }

    @Test
    void testUpdateUserEmailVerificationStatus() {
        boolean newEmailVerificationStatus = false;
        String userId = "1a2b3c";
        userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, userId);

        UserEntity storedDetails = userRepository.findByUserId(userId);
        boolean storedEmailVerificationStatus = storedDetails.getEmailVerificationStatus();
        assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);
    }
}
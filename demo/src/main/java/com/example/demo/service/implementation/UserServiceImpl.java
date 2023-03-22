package com.example.demo.service.implementation;

import com.example.demo.repository.UserRepository;
import com.example.demo.io.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.shared.Utils;
import com.example.demo.shared.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public UserDTO createUser(UserDTO user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Record already exists");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(user.getEmail() + "encrypted");

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDTO getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new RuntimeException("Username not found");
        }

        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity ,returnValue);

        return returnValue;
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        UserDTO returnValue = new UserDTO();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new RuntimeException("Username not found");
        }

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }
}

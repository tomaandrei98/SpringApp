package com.example.demo.service;

import com.example.demo.shared.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO getUser(String email);

    UserDTO getUserByUserId(String id);
}

package com.example.demo.service;

import com.example.demo.shared.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO getUser(String email);

    UserDTO getUserByUserId(String userId);

    UserDTO updateUser(String userId, UserDTO user);

    void deleteUser(String userId);

    List<UserDTO> getUsers(int page, int limit);
}

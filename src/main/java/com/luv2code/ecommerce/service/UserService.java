package com.luv2code.ecommerce.service;

import com.luv2code.ecommerce.dto.user.UserDto;
import com.luv2code.ecommerce.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}

package com.example.SimbirSoft_2021.service.interfaceService;

import com.example.SimbirSoft_2021.Dto.UserDto;
import com.example.SimbirSoft_2021.entity.UserEntity;
import com.example.SimbirSoft_2021.exception.UserNotFoundException;

public interface UserServiceInterface {
    UserEntity findByEmail(String email) throws UserNotFoundException;
}

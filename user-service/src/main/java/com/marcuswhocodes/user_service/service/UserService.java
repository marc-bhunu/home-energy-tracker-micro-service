package com.marcuswhocodes.user_service.service;

import com.marcuswhocodes.user_service.domain.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
    void updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
}

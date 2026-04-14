package com.marcuswhocodes.user_service.service.impl;

import com.marcuswhocodes.user_service.domain.dto.UserDto;
import com.marcuswhocodes.user_service.domain.entity.User;
import com.marcuswhocodes.user_service.repositorty.UserRepository;
import com.marcuswhocodes.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto) {
        final User createdUser = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .alerting(userDto.isAlerting())
                .energyAlertingThreshold(userDto.getEnergyAlertingThreshold())
                .build();
        User savedUser = userRepository.save(createdUser);

        return toDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Retrieving user by id: {}", id);
        return userRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public void updateUser(Long id, UserDto userDto) {
        User user  = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setAlerting(userDto.isAlerting());
        user.setEnergyAlertingThreshold(userDto.getEnergyAlertingThreshold());

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(user);
    }


    private UserDto toDto(User savedUser) {
        return UserDto.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .surname(savedUser.getSurname())
                .email(savedUser.getEmail())
                .address(savedUser.getAddress())
                .alerting(savedUser.isAlerting())
                .energyAlertingThreshold(savedUser.getEnergyAlertingThreshold())
                .build();
    }
}

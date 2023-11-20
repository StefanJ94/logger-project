package com.example.LoggerProject.domain.services;

import com.example.LoggerProject.domain.dtos.UserInfoDTO;
import com.example.LoggerProject.domain.entities.UserEntity;
import com.example.LoggerProject.domain.mappers.LogMapper;
import com.example.LoggerProject.domain.mappers.UserMapper;
import com.example.LoggerProject.exceptions.UserNotFoundException;
import com.example.LoggerProject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    public final UserMapper userMapper;
    public final LogMapper logMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserInfoDTO> getAllUsers() {
        List<UserInfoDTO> usersInfo = new ArrayList<>();
        for (UserEntity user : userRepository.findAll()) {
            usersInfo.add(userMapper.userEntityToInfoDto(user));
        }
        return usersInfo;
    }

    public void changePassword(Long id, String password) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        user.setPassword(passwordEncoder.encode(password));
    }

    //TODO implement admin method for deleting users
}

package com.example.LoggerProject.domain.services;

import com.example.LoggerProject.domain.dtos.LogDTO;
import com.example.LoggerProject.domain.dtos.UserCreationDTO;
import com.example.LoggerProject.domain.entities.LogEntity;
import com.example.LoggerProject.domain.entities.LogType;
import com.example.LoggerProject.domain.entities.RoleType;
import com.example.LoggerProject.domain.entities.UserEntity;
import com.example.LoggerProject.domain.mappers.LogMapper;
import com.example.LoggerProject.domain.mappers.UserMapper;
import com.example.LoggerProject.exceptions.InvalidCredentialsException;
import com.example.LoggerProject.exceptions.InvalidEmailException;
import com.example.LoggerProject.exceptions.InvalidPasswordException;
import com.example.LoggerProject.exceptions.InvalidUsernameException;
import com.example.LoggerProject.repositories.LogRepository;
import com.example.LoggerProject.repositories.UserRepository;
import com.example.LoggerProject.security.PasswordValidation;
import com.example.LoggerProject.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final LogRepository logRepository;
    public final UserMapper userMapper;
    public final LogMapper logMapper;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidation passwordValidation;

    private static final List<String> DEFAULT_ROLES = List.of("ROLE_USER");
    private static final List<String> ADMIN_ROLES = List.of("USER", "ROLE_ADMIN");

    public String createUser(UserCreationDTO dto) {
        if (!EmailValidator.getInstance().isValid(dto.getEmail())) {
            throw new InvalidEmailException("Provided email not valid!");
        }
        if (!passwordValidation.validate(dto.getPassword()).isValid()) {
            throw new InvalidPasswordException("Invalid password!");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new InvalidUsernameException("Username is taken!");
        }
        UserEntity user = userRepository.save(userMapper.userCreationDtoToEntity(dto));
        user.setRoleType(RoleType.USER);
        return tokenProvider.create(user, DEFAULT_ROLES);
    }

    @Transactional(readOnly = true)
    public String provideToken(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password!"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password!");
        }
        if (user.getRoleType().toString().equals("ADMIN")) {
            return tokenProvider.create(user, ADMIN_ROLES);
        }
        return tokenProvider.create(user, DEFAULT_ROLES);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password!"));
    }

    public void createLog(LogDTO dto, String username) {
        if (dto.getUserLog().length() > 1024) {
            throw new InvalidCredentialsException("Log message is too large!");
        }
        LogEntity log = logMapper.logDtoToEntity(dto);
        log.setUser(getUserByUsername(username));
        logRepository.save(log);
    }

    public List<LogDTO> getLogsByType(LogType logType) {
        List<LogDTO> logDtos = new ArrayList<>();
        for (LogEntity log : logRepository.findAll()) {
            logDtos.add(logMapper.logEntityToDto(log));
        }
        return logDtos;
    }
}

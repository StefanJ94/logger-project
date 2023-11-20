package com.example.LoggerProject.domain.mappers;

import com.example.LoggerProject.domain.dtos.SignInDTO;
import com.example.LoggerProject.domain.dtos.UserCreationDTO;
import com.example.LoggerProject.domain.dtos.UserInfoDTO;
import com.example.LoggerProject.domain.entities.UserEntity;
import lombok.AccessLevel;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public abstract class UserMapper {

    @Autowired
    @Setter(AccessLevel.PACKAGE)
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleType", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "userLogs", ignore = true)
    public abstract UserEntity signInDtoToEntity(SignInDTO dto);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleType", ignore = true)
    @Mapping(target = "userLogs", ignore = true)
    public abstract UserEntity userCreationDtoToEntity(UserCreationDTO dto);

    public abstract UserInfoDTO userEntityToInfoDto(UserEntity entity);
}

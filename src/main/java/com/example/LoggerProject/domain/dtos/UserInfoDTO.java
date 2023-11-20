package com.example.LoggerProject.domain.dtos;

import com.example.LoggerProject.domain.entities.RoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserInfoDTO {

    @ApiModelProperty(value = "User's username.", example = "someuser123")
    private String username;

    @ApiModelProperty(value = "User's email.", example = "user@mail.com")
    private String email;

    @ApiModelProperty(value = "User's role", example = "User or Admin role")
    private RoleType roleType;
}

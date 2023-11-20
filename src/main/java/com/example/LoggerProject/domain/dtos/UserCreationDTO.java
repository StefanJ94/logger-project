package com.example.LoggerProject.domain.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserCreationDTO {

    @ApiModelProperty(value = "User's username.", example = "someuser123")
    private String username;

    @ApiModelProperty(value = "User's email.", example = "user@mail.com")
    private String email;

    @ApiModelProperty(value = "Plaintext password.", example = "p@ssw0rd")
    private String password;
}

package com.example.LoggerProject.domain.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SignInDTO {

    @ApiModelProperty(value = "User's username.", example = "someuser123")
    private String username;

    @ApiModelProperty(value = "Plaintext password.", example = "p@ssw0rd")
    private String password;
}

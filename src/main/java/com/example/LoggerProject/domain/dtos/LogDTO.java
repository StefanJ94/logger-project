package com.example.LoggerProject.domain.dtos;

import com.example.LoggerProject.domain.entities.LogType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class LogDTO {

    @ApiModelProperty(value = "User's log message", example = "some log message")
    private String userLog;

    @ApiModelProperty(value = "User's log type.", example = "Info, Warning and Error")
    private LogType logType;
}

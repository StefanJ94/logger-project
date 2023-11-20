package com.example.LoggerProject.domain.mappers;

import com.example.LoggerProject.domain.dtos.LogDTO;
import com.example.LoggerProject.domain.entities.LogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper
public abstract class LogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "createdDate", ignore = true)
    public LogEntity logDtoToEntity(LogDTO dto){
         return LogEntity.builder()
                .userLog(dto.getUserLog())
                .logType(dto.getLogType())
                .createdDate(java.util.Date.from(Instant.now()))
                .build();
    }

    public abstract LogDTO logEntityToDto(LogEntity log);
}

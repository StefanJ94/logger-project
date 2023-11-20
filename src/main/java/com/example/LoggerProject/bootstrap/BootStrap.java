package com.example.LoggerProject.bootstrap;

import com.example.LoggerProject.domain.entities.RoleType;
import com.example.LoggerProject.domain.entities.UserEntity;
import com.example.LoggerProject.repositories.LogRepository;
import com.example.LoggerProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;

public class BootStrap implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LogRepository logRepository;

    @Override
    public void run(String... args) throws Exception {

        UserEntity admin = new UserEntity(1L, "JovanovicStefan", "jovanovic.stefan94@yahoo.com"
                , "Atlantik123", RoleType.ADMIN, new ArrayList<>());
        userRepository.save(admin);

    }
}

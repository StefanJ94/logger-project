package com.example.LoggerProject.controllers;

import com.example.LoggerProject.domain.dtos.LogDTO;
import com.example.LoggerProject.domain.dtos.SignInDTO;
import com.example.LoggerProject.domain.dtos.UserCreationDTO;
import com.example.LoggerProject.domain.entities.LogType;
import com.example.LoggerProject.domain.services.UserService;
import com.example.LoggerProject.security.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create account.")
    @ApiResponse(responseCode = "201", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenDTO.class))
    })
    @PostMapping(value = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TokenDTO> signUp(@RequestBody UserCreationDTO dto) {
        String jwt = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTokenDTO(jwt));
    }

    @Operation(summary = "Obtain access token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Invalid username or password!")
    })
    @PostMapping(value = "/signin", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TokenDTO> signIn(@RequestBody SignInDTO dto) {
        String jwt = userService.provideToken(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(createTokenDTO(jwt));
    }

    @Operation(summary = "Create log.", security = { @SecurityRequirement(name = "JWT") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Log created", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    @PostMapping(value = "/create_log")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> createLog(@RequestBody LogDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.createLog(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto.getUserLog());
    }

    public ResponseEntity<List<LogDTO>> getLogsByType(@RequestBody LogType logType) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body((userService.getLogsByType(logType)));
    }

    private TokenDTO createTokenDTO(String jwt) {
        return TokenDTO.builder().accessToken(jwt).build();
    }

}

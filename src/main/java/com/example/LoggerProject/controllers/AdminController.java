package com.example.LoggerProject.controllers;

import com.example.LoggerProject.domain.dtos.UserInfoDTO;
import com.example.LoggerProject.domain.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Get list of all users.", security = { @SecurityRequirement(name = "JWT") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInfoDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    @GetMapping("/get_users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserInfoDTO>> getAllUser() {
        // return ResponseEntity.status(HttpStatus.ACCEPTED).body((adminService.getAllUsers()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminService.getAllUsers());
    }

    @Operation(summary = "Change user password with provided id", security = { @SecurityRequirement(name = "JWT") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    @PatchMapping("/reset-password/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changePassword(@RequestBody String password, @PathVariable(name = "id") Long id) {
        adminService.changePassword(id, password);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password has been changed!");
    }
}


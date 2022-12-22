package com.ldap.springbootusermanagementldap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ldap.springbootusermanagementldap.dto.AuthRequest;
import com.ldap.springbootusermanagementldap.dto.UserRequestDto;
import com.ldap.springbootusermanagementldap.dto.UserResponseDto;
import com.ldap.springbootusermanagementldap.model.User;
import com.ldap.springbootusermanagementldap.service.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "User")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/versions/1")
public class UserController {

    final UserService userService;

    @Operation(summary = "This Service's goal,Get all Users Info")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.listUsers();
    }


    @Operation(summary = "This Service's goal,Save User in User Organization Unit")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CREATOR')")
    @PostMapping("/user")
    public User save(@RequestBody UserRequestDto userRequestDto) {
       return userService.save(userRequestDto);
    }

    @Operation(summary = "This Service's goal,Delete User in User Organization Unit")

    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_DELETER')")
    @DeleteMapping("/user/{username}")
    public void delete(@PathVariable("username") String username){
        userService.delete(username);
    }

    @Operation(summary = "This Service's goal,Get User Info in User Organization Unit")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/user/{username}")
    public User get(@PathVariable("username") String username){
        return userService.get(username);
    }
    @Operation(summary = "This Service's goal,Update User in User Organization Unit")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_UPDATER')")
    @PutMapping("/user/{username}")
    public User update(@PathVariable("username") String username,@RequestBody UserRequestDto userRequestDto){
        return userService.update(username,userRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPage(@RequestBody @Valid AuthRequest authRequest){
        try {

            return ResponseEntity.ok(userService.login(authRequest));
        } catch (BadCredentialsException | JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}

package com.ldap.springbootusermanagementldap.service.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ldap.springbootusermanagementldap.dto.AuthRequest;
import com.ldap.springbootusermanagementldap.dto.AuthResponse;
import com.ldap.springbootusermanagementldap.dto.UserRequestDto;
import com.ldap.springbootusermanagementldap.dto.UserResponseDto;
import com.ldap.springbootusermanagementldap.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {


    List<UserResponseDto> listUsers();

    User save(UserRequestDto userRequestDto);

    User update(String uid,UserRequestDto userRequestDto);

    User get(String uid);

    AuthResponse login(AuthRequest authRequest) throws JsonProcessingException;

    void delete(String uid);
}

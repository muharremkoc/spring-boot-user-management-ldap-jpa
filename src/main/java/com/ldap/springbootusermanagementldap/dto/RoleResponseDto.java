package com.ldap.springbootusermanagementldap.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RoleResponseDto {

    String name;
    List<UserResponseDto> users;


}

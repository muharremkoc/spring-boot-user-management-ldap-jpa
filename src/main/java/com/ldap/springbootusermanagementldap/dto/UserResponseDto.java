package com.ldap.springbootusermanagementldap.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserResponseDto {


    String uid;

    String fullName;

    String lastName;

}

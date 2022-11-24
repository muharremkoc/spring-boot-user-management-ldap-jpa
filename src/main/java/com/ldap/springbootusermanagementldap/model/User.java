package com.ldap.springbootusermanagementldap.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;

import javax.naming.Name;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entry(base = "ou=users", objectClasses = {"top", "person", "inetOrgPerson"}) // Configure here considering your LDAP directory
public class User {

    @Attribute(name = "uid")
    String uid;
    @Attribute(name = "cn")
    String fullName;
    @Attribute(name = "sn")
    String lastName;
    @Attribute(name = "userpassword")
    String password;

}

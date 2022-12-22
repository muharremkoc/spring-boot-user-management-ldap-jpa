package com.ldap.springbootusermanagementldap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import javax.naming.Name;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Data
@Entry(base="ou=users", objectClasses = {
        "top","inetOrgPerson", "person", "organizationalPerson"})
public class User {

        @JsonIgnore
        @Id
        Name dn;

        private @Attribute(name = "uid") String username;

        private @Attribute(name = "cn") String fullName;

        private  @Attribute(name = "sn") String lastName;

        private  @Attribute(name = "userPassword",type = Attribute.Type.BINARY) byte[] password;

        @Attribute(name = "o")
        private Set<String> roles;

    public void setPassword(String password) {
        this.password = password.getBytes(StandardCharsets.UTF_8);
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role.getName());
    }
    public void removeMember(Role role) {
        roles.remove(role.getName());
    }


    public User(String username, String fullName, String lastName, String password) {

                this.username = username;
                this.fullName = fullName;
                this.lastName = lastName;
                this.password = password.getBytes(StandardCharsets.UTF_8);
        }

        public User() {
        }

}

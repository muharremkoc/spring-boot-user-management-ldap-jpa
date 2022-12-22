package com.ldap.springbootusermanagementldap.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ldap.springbootusermanagementldap.enums.ERole;

import java.util.Set;

public class RoleRequestDto {

    @JsonIgnore
    String dn;

    @JsonIgnore
    ERole eRole;

    Set<String> userDns;


    public ERole geteRole() {
        return eRole;
    }

    public void seteRole(ERole eRole) {
        this.eRole = eRole;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }


    public Set<String> getUserDns() {
        return userDns;
    }

    public void setUserDns(Set<String> userDns) {
        this.userDns = userDns;
    }
}

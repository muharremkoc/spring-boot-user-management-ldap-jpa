package com.ldap.springbootusermanagementldap.service.roles;

import com.ldap.springbootusermanagementldap.dto.RoleRequestDto;
import com.ldap.springbootusermanagementldap.enums.ERole;
import com.ldap.springbootusermanagementldap.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getRoles(ERole role);
    Role addUserWithRole(RoleRequestDto roleRequestDto,ERole eRole);
    Role removeUserWithRole(RoleRequestDto roleRequestDto,ERole eRole);
    void addRoleInUser(ERole role,String userDn);
    void removeRoleInUser(ERole role,String userDn);
}


package com.ldap.springbootusermanagementldap.controller;


import com.ldap.springbootusermanagementldap.dto.RoleRequestDto;
import com.ldap.springbootusermanagementldap.enums.ERole;
import com.ldap.springbootusermanagementldap.model.Role;
import com.ldap.springbootusermanagementldap.service.roles.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Manager")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/versions/1")
public class ManagerController {

    final RoleService roleService;

    @Operation(summary = "This Service's goal,Get All User and Role Info in Role Organization Unit")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/roles")
    public List<Role> getAllRoles(@RequestParam ERole eRole) {
        return roleService.getRoles(eRole);
    }

    @Operation(summary = "This Service's goal,Add User in Role Organization Unit")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_UPDATER')")
    @PutMapping("/roles")
    public Role addUserInRole(@RequestParam ERole eRole, @RequestBody RoleRequestDto roleRequestDto) {
        return roleService.addUserWithRole(roleRequestDto, eRole);
    }

    @Operation(summary = "This Service's goal,Remove User in Role Organization Unit")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_UPDATER')")
    @DeleteMapping("/roles")
    public Role removeUserInRole(@RequestParam ERole eRole, @RequestBody RoleRequestDto roleRequestDto) {
        return roleService.removeUserWithRole(roleRequestDto, eRole);
    }
}

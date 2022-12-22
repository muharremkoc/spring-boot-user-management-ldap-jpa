package com.ldap.springbootusermanagementldap.repository;

import com.ldap.springbootusermanagementldap.enums.ERole;
import com.ldap.springbootusermanagementldap.model.Role;
import org.springframework.data.ldap.repository.LdapRepository;

import java.util.List;

public interface RoleRepository extends LdapRepository<Role> {

   List<Role> findAllByName(String eRole);

   Role findByName(String eRole);


}

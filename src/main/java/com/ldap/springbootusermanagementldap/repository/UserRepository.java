package com.ldap.springbootusermanagementldap.repository;

import com.ldap.springbootusermanagementldap.model.User;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends LdapRepository<User>{

    User findByUsername(String username);
}
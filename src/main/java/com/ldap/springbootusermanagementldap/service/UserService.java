package com.ldap.springbootusermanagementldap.service;

import com.ldap.springbootusermanagementldap.model.User;

import java.util.List;

public interface UserService {

     String create(User user);

     String update(User user);

    String delete(String uid);

    List<User> findAll();

    }

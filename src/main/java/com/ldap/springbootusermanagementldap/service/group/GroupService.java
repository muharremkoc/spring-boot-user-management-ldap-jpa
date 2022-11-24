package com.ldap.springbootusermanagementldap.service.group;

import com.ldap.springbootusermanagementldap.model.Group;
import com.ldap.springbootusermanagementldap.model.User;

import java.util.List;

public interface GroupService {

    List<Group> findAll();
    void addMemberToGroup(String groupName, User user);
    void removeMemberFromGroup(String groupName, User user);
}

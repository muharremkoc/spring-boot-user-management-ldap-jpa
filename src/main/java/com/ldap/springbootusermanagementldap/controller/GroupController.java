package com.ldap.springbootusermanagementldap.controller;

import com.ldap.springbootusermanagementldap.model.Group;
import com.ldap.springbootusermanagementldap.model.User;
import com.ldap.springbootusermanagementldap.service.group.GroupService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupController {

    private final GroupService groupService;


    @PostMapping("/group/add/member")
    public void bindLdapUser(@RequestParam String groupName, @RequestBody User user) {
         groupService.addMemberToGroup(groupName, user);
    }

    @DeleteMapping("/group/remove/member")
    public void unBindLdapUser(@RequestParam String groupName, @RequestBody User user) {
        groupService.removeMemberFromGroup(groupName, user);
    }

    @GetMapping("/group")
    public ResponseEntity<List<Group>> retrieve() {
        return new ResponseEntity<List<Group>>(groupService.findAll(), HttpStatus.OK);
    }
}

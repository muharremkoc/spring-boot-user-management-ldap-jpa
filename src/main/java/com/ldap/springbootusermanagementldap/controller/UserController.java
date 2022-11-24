package com.ldap.springbootusermanagementldap.controller;

import com.ldap.springbootusermanagementldap.model.User;
import com.ldap.springbootusermanagementldap.service.UserService;
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
public class UserController {

    private final UserService userService;


    @PostMapping("/add-user")
    public ResponseEntity<String> bindLdapPerson(@RequestBody User user) {
        String result = userService.create(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/retrieve-users")
    public ResponseEntity<List<User>> retrieve() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }


    @PutMapping("/update-user")
    public ResponseEntity<String> rebindLdapPerson(@RequestBody User user) {
        String result = userService.update(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/remove-user")
    public ResponseEntity<String> unbindLdapPerson(@RequestParam String uid) {

        String result = userService.delete(uid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

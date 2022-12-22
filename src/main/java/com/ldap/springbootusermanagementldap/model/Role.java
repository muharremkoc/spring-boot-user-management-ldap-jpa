package com.ldap.springbootusermanagementldap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.HashSet;
import java.util.Set;

@Entry(base="ou=roles", objectClasses = {
        "top","groupOfNames"})
public class Role {



    @Id
    @JsonIgnore
    Name dn;

    @Attribute(name = "cn")
    String name;

    @JsonIgnore
    @Attribute(name = "member")
    Set<String> userDns;


    public Role() {
    }

    public Role(String name) {
        this.name = name;

    }

    public Role(String name, Set<String> userDns) {

        this.name = name;
        this.userDns = userDns;
    }

    public Set<String> getUserDns() {
        return userDns;
    }

    public void setUserDns(Set<String> userDns) {

        this.userDns = userDns;
    }

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> filteredUser(Set<String> users){

        Set<String> userList = new HashSet<>();

        users.forEach(s -> {
            String s1[] = s.split(",");
            s = s1[0]+","+s1[1];
            userList.add(s);
        });

        return userList;
    }

    public void addMember(String userDn) {
        if (this.userDns == null){
            this.userDns = new HashSet<>();
        }
        userDns.add(userDn);
    }

    public void removeMember(String member) {
        userDns.remove(member);
    }

}

package com.ldap.springbootusermanagementldap.service;


import com.ldap.springbootusermanagementldap.model.User;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.ldap.LdapName;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService,BaseLdapNameAware{

    @Autowired
     private LdapTemplate ldapTemplate;

    private LdapName ldapName;


    @Override
    public void setBaseLdapPath(LdapName ldapName) {
        this.ldapName = ldapName;
    }

    @Override
    public String create(User user) {
        ldapTemplate.bind(buildDn(user.getUid()), null, buildAttributes(user));
        return user.getFullName() + " created successfully";
    }

    @Override
    public String update(User user) {

            List<User> filterUsers=findAll();

            filterUsers.stream().filter(user1 -> user1.getUid().equals(user.getUid())).forEach(user1 -> {
                    user1.setFullName(user.getFullName());
                    user1.setLastName(user.getLastName());
                    ldapTemplate.rebind(buildDn(user1.getUid()), null, buildAttributes(user));

            });
        return user.getFullName() + " updated successfully";
    }

    @Override
    public String delete(String uid) {
        ldapTemplate.unbind(buildDn(uid));
        return uid +"was been deleted";
    }

    @Override
    public List<User> findAll() {
        EqualsFilter filter = new EqualsFilter("objectClass", "person");

        return ldapTemplate.search(LdapUtils.newLdapName(LdapNameBuilder.newInstance(ldapName).build()), filter.encode(), new LdapUserContextMapper());
    }

    private Name buildDn(String userId) {
        return LdapNameBuilder.newInstance(ldapName)
                .add("ou", "users")
                .add("uid", userId)
                .build();
    }

    private Attributes buildAttributes(User ldapUser) {
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocAttr = new BasicAttribute("objectClass");
        ocAttr.add("top");
        ocAttr.add("person");
        ocAttr.add("inetOrgPerson");
        attrs.put(ocAttr);
        attrs.put("ou", "users");
        attrs.put("uid", ldapUser.getUid());
        attrs.put("sn", ldapUser.getLastName());
        attrs.put("cn", ldapUser.getFullName());
        attrs.put("userpassword", encodePassword(ldapUser.getPassword()));
        return attrs;
    }

    private String encodePassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] passwordByte = password.getBytes();
        assert md != null;
        md.update(passwordByte, 0, passwordByte.length);
        byte[] encodedPassword = md.digest();
        return "{SHA}" + Base64.getEncoder().encodeToString(encodedPassword);
    }

    private static class LdapUserContextMapper extends AbstractContextMapper<User> {
        public User doMapFromContext(DirContextOperations ctx) {
            User ldapUser = new User();
            ldapUser.setUid(ctx.getStringAttribute("uid"));
            ldapUser.setLastName(ctx.getStringAttribute("sn"));
            ldapUser.setFullName(ctx.getStringAttribute("cn"));
            return ldapUser;
        }
    }
}

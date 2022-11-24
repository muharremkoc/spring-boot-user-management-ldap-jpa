package com.ldap.springbootusermanagementldap.service.group;

import com.ldap.springbootusermanagementldap.model.Group;
import com.ldap.springbootusermanagementldap.model.User;
import com.ldap.springbootusermanagementldap.service.UserServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.core.support.BaseLdapNameAware;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupServiceImpl implements GroupService,BaseLdapNameAware{

    private LdapName ldapName;


    final LdapTemplate ldapTemplate;

    @Override
    public List<Group> findAll() {
        EqualsFilter filter = new EqualsFilter("objectClass", "groupOfNames");

        return ldapTemplate.search(LdapUtils.newLdapName(LdapNameBuilder.newInstance(ldapName).build()), filter.encode(), new GroupContextMapper());    }

    @Override
    public void addMemberToGroup(String groupName, User user) {
        Name groupDn = buildGroupDn(groupName);
        Name personDn = buildPersonDn(user);

        DirContextOperations ctx = ldapTemplate.lookupContext(groupDn);
        ctx.addAttributeValue("member", personDn);

        ldapTemplate.modifyAttributes(ctx);
    }

    @Override
    public void removeMemberFromGroup(String groupName, User user) {
        Name groupDn = buildGroupDn(groupName);
        Name personDn = buildPersonDn(user);

        DirContextOperations ctx = ldapTemplate.lookupContext(groupDn);
        ctx.removeAttributeValue("member", personDn);

        ldapTemplate.modifyAttributes(ctx);
    }

    private Name buildGroupDn(String groupName) {
        return LdapNameBuilder.newInstance(ldapName)
                .add("ou", "roles")
                .add("cn", groupName)
                .build();
    }

    private Name buildPersonDn(User user) {
        return LdapNameBuilder.newInstance(ldapName)
                .add("ou", "users")
                .add("uid", user.getUid())
                .build();
    }

    @Override
    public void setBaseLdapPath(LdapName ldapName) {
        this.ldapName = ldapName;
    }


    private static class GroupContextMapper extends AbstractContextMapper<Group> {
        public Group doMapFromContext(DirContextOperations context) {
            Group group = new Group();
            group.setName(context.getStringAttribute("cn"));
            Object[] members = context.getObjectAttributes("member");
            for (Object member : members){
                Name memberDn = LdapNameBuilder.newInstance(String.valueOf(member)).build();
                group.addMember(memberDn);
            }
            return group;
        }
    }
}

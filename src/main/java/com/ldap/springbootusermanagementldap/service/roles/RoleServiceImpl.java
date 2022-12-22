package com.ldap.springbootusermanagementldap.service.roles;

import com.ldap.springbootusermanagementldap.dto.RoleRequestDto;
import com.ldap.springbootusermanagementldap.enums.ERole;
import com.ldap.springbootusermanagementldap.model.Role;
import com.ldap.springbootusermanagementldap.model.User;
import com.ldap.springbootusermanagementldap.repository.RoleRepository;
import com.ldap.springbootusermanagementldap.repository.UserRepository;
import com.ldap.springbootusermanagementldap.service.users.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleServiceImpl implements RoleService{

    static final String BASE_DN=",dc=springframework,dc=org";

    final RoleRepository roleRepository;

    final UserRepository userRepository;

    final UserService userService;

    final LdapTemplate ldapTemplate;


    @Override
    public List<Role> getRoles(ERole eRole) {


        List<Role> roles = roleRepository.findAllByName(eRole.name());



            roles.forEach(role -> {
                role.setUserDns(role.filteredUser(role.getUserDns()));
            });


/*            roles.forEach(role -> {
                role.getUserDns().forEach(s -> {
                    User user = userRepository.findById(LdapNameBuilder.newInstance(s).build()).get();
                    UserResponseDto userResponseDto = UserResponseDto.builder()
                            .uid(user.getUid())
                            .fullName(user.getFullName())
                            .lastName(user.getLastName())
                            .build();
                    userResponseDtos.add(userResponseDto);
                });

                RoleResponseDto roleResponseDto = RoleResponseDto.builder()
                        .name(role.getName())
                        .users(userResponseDtos)
                        .build();
                roleResponseDtos.add(roleResponseDto);
            });*/


        return roles;
    }

    @Override
    public Role addUserWithRole(RoleRequestDto roleRequestDto,ERole eRole) {
        List<Role> roles =roleRepository.findAllByName(eRole.name());

        roleRequestDto.seteRole(eRole);
        roles.forEach(role -> {
            role.setDn(LdapNameBuilder.newInstance("cn="+roleRequestDto.geteRole().name()+",ou=roles").build());
            role.setName(roleRequestDto.geteRole().name());
            roleRequestDto.getUserDns().forEach(s -> {
                s = s+BASE_DN;
                role.addMember(s);
            });

            beforeSave(role);

            roleRepository.save(role);

        });

        roleRequestDto.getUserDns().forEach(s -> {
            addRoleInUser(eRole,s);
        });

        return roleRepository.findByName(eRole.name());
    }

    @Override
    public Role removeUserWithRole(RoleRequestDto roleRequestDto, ERole eRole) {
        List<Role> roles =roleRepository.findAllByName(eRole.name());

        roleRequestDto.seteRole(eRole);
        roles.forEach(role -> {
            role.setDn(LdapNameBuilder.newInstance("cn="+roleRequestDto.geteRole().name()+",ou=roles").build());
            role.setName(roleRequestDto.geteRole().name());
            roleRequestDto.getUserDns().forEach(s -> {
                s = s+BASE_DN;
                role.removeMember(s);
            });
            roleRequestDto.getUserDns().forEach(s -> {
                removeRoleInUser(eRole,s);
            });
            beforeSave(role);
            roleRepository.save(role);

        });

        return roleRepository.findByName(eRole.name());
    }

    @Override
    public void addRoleInUser(ERole role,String userDn) {
        Role roles =roleRepository.findByName(role.name());
        roles.setUserDns(roles.filteredUser(roles.getUserDns()));
        List<String> userDns= new ArrayList<>(roles.getUserDns());

            userDns.forEach(s -> {
                if (userDn.equals(s)){
                    User user = userRepository.findById(LdapNameBuilder.newInstance(s).build()).get();
                    Set<String> roleSet = user.getRoles();
                    roleSet.add(roles.getName());
                    user.setRoles(roleSet);
                    userRepository.save(user);
                    System.out.println(user.getRoles());
                }

        });
    }

    @Override
    public void removeRoleInUser(ERole role, String userDn) {
        Role roles =roleRepository.findByName(role.name());
        roles.setUserDns(roles.filteredUser(roles.getUserDns()));
        List<String> userDns= new ArrayList<>(roles.getUserDns());

        userDns.forEach(s -> {
            if (userDn.equals(s)){
                User user = userRepository.findById(LdapNameBuilder.newInstance(s).build()).get();
                Set<String> roleSet = user.getRoles();
                roleSet.remove(roles.getName());
                user.setRoles(roleSet);
                userRepository.save(user);
            }

        });
    }

    private void beforeSave(Role role){
        DirContextOperations ctx = ldapTemplate.lookupContext(role.getDn());
        role.getUserDns().forEach(s -> {
            ctx.addAttributeValue("member", s);
        });

        ldapTemplate.modifyAttributes(ctx);
    }


}

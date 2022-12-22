package com.ldap.springbootusermanagementldap.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@EnableLdapRepositories(basePackages = "com.ldap.springbootusermanagementldap")
public class ApacheDSConfiguration {

    @Bean
    ContextSource contextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUserDn("cn=admin,dc=springframework,dc=org");
        ldapContextSource.setUrl("ldap://localhost:389/");
        ldapContextSource.setPassword("admin");
        ldapContextSource.setBase("dc=springframework,dc=org");

        return ldapContextSource;
    }

    @Bean
    LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }


}
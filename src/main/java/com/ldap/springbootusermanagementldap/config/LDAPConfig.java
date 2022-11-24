package com.ldap.springbootusermanagementldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathBeanPostProcessor;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LDAPConfig {

    @Value("${ldap.base.dn}")
    private String baseDn;

    @Autowired
    private Environment env;


    @Bean
    public BaseLdapPathBeanPostProcessor ldapPathBeanPostProcessor() {
        BaseLdapPathBeanPostProcessor baseLdapPathBeanPostProcessor = new BaseLdapPathBeanPostProcessor();
        baseLdapPathBeanPostProcessor.setBasePath(baseDn);
        return baseLdapPathBeanPostProcessor;
    }

    @Bean
    public ContextSource contextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setBase(env.getProperty("ldap.userdn" + "," + env.getProperty("ldap.base.dn")));
        ldapContextSource.setUrl(env.getProperty("ldap.url"));
        ldapContextSource.setUserDn(env.getProperty("ldap.managerdn") + "," + env.getProperty("ldap.base.dn"));
        ldapContextSource.setPassword(env.getProperty("ldap.manager.password"));
        return ldapContextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }


}

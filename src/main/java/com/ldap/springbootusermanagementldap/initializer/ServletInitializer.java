package com.ldap.springbootusermanagementldap.initializer;

import com.ldap.springbootusermanagementldap.SpringBootUserManagementLdapApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootUserManagementLdapApplication.class);
    }

}
package com.ldap.springbootusermanagementldap.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import javax.naming.ldap.LdapName;
import java.io.IOException;

@JsonComponent
public class LdapNameSerializer extends JsonSerializer<LdapName> {

    @Override
    public void serialize(LdapName ldapName, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(ldapName.toString());
    }
}
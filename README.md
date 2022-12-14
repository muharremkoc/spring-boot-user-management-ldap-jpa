# spring-boot-user-management-ldap-jpa



This Project Goal's,Spring Security User-Management-JWT-LDAP Protocol


## Technologies

- Spring Boot

- Spring Security

- Spring  Data JPA

- LDAP

- Lombok

- JWT

- Docker


## Installation

 - Run docker-compose up -d (Configured Optional)


- Define these ldap dependencies and other ldap dependencies in pom.xml

```java
  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-ldap</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.ldap</groupId>
            <artifactId>spring-ldap-core</artifactId>
        </dependency>
        <dependency>
            <groupId>com.unboundid</groupId>
            <artifactId>unboundid-ldapsdk</artifactId>
        </dependency>
```

- Open application.properties and set ldap settings

```java
spring.ldap.base=dc=springframework,dc=org
spring.ldap.password=admin
spring.ldap.username=cn=admin,dc=springframework,dc=org
spring.ldap.urls=ldap://localhost:389
spring.data.ldap.repositories.enabled=true
```
## Usage

 - Open ApacheDSConfiguration.class and other details
```java
 @Bean
    ContextSource contextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUserDn("cn=admin,dc=springframework,dc=org");
        ldapContextSource.setUrl("ldap://localhost:389/");
        ldapContextSource.setPassword("admin");
        ldapContextSource.setBase("dc=springframework,dc=org");

        return ldapContextSource;
    }
```

```java
@Entry(base="ou=users", objectClasses = {
        "top","inetOrgPerson", "person", "organizationalPerson"})
public class User {

        @JsonIgnore
        @Id
        Name dn;

        private @Attribute(name = "uid") String username;

        private @Attribute(name = "cn") String fullName;

        private  @Attribute(name = "sn") String lastName;

        private  @Attribute(name = "userPassword",type = Attribute.Type.BINARY) byte[] password;

       
       /* @Attribute(name = "o") This attribute name is my Option*/
        private Set<String> roles;
       
}
```


[Muharrem Koç](https://github.com/muharremkoc)

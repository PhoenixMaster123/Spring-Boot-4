# Spring Security 7 MFA Demo

A demo project showcasing Spring Security 7's new `@EnableMultiFactorAuthentication` feature with One-Time Token (OTT) authentication.

[Official Documentation](https://docs.spring.io/spring-security/reference/servlet/authentication/mfa.html)

## What's Demonstrated

- **`@EnableMultiFactorAuthentication`** - New annotation to require multiple authentication factors
- **Two-factor flow** - Password (something you know) + OTT (something you have)
- **Custom `OneTimeTokenService`** - 5-digit PIN implementation instead of UUID
- **Automatic factor detection** - Spring Security redirects users to complete missing factors

## Quick Start

```bash
./mvnw spring-boot:run
```

**Test Users:**
| Username | Password | Roles |
|----------|----------|-------|
| user | password | USER |
| admin | password | ADMIN, USER |

**Flow:**
1. Navigate to `http://localhost:8080/admin`
2. Login with `admin/password`
3. Check console output for the magic link with your PIN
4. Click the link or navigate to `/login/ott?token=YOUR_PIN`

## Key Code

```java
@Configuration
@EnableWebSecurity
@EnableMultiFactorAuthentication(authorities = {
        FactorGrantedAuthority.PASSWORD_AUTHORITY,
        FactorGrantedAuthority.OTT_AUTHORITY
})
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/ott/sent").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                )
                .formLogin(withDefaults())
                .oneTimeTokenLogin(withDefaults())
                .build();
    }
}
```

The `@EnableMultiFactorAuthentication` annotation specifies that **both** `PASSWORD_AUTHORITY` and `OTT_AUTHORITY` are required. Spring Security automatically prompts for any missing factors.

## Resources

- [Spring Security MFA Documentation](https://docs.spring.io/spring-security/reference/servlet/authentication/mfa.html)
- [One-Time Token Login](https://docs.spring.io/spring-security/reference/servlet/authentication/onetimetoken.html)
package com.example.clientservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Autoridades estándar (scope, etc.)
        Collection<GrantedAuthority> defaultAuthorities =
                Optional.ofNullable(jwtGrantedAuthoritiesConverter.convert(jwt))
                        .orElse(Collections.emptyList());

        // Roles personalizados de Keycloak (resource_access)
        Collection<? extends GrantedAuthority> resourceRoles = extractResourceRoles(jwt);

        // Unir y eliminar duplicados
        Collection<GrantedAuthority> authorities = Stream.concat(defaultAuthorities.stream(), resourceRoles.stream())
                .collect(Collectors.toSet()); // Set para evitar duplicados

        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
    }

    private String getPrincipalClaimName(Jwt jwt) {
        // Keycloak suele usar "preferred_username", pero puedes cambiarlo si tu JWT usa otro claim
        return jwt.getClaimAsString("preferred_username");
    }

    @SuppressWarnings("unchecked")
    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            return Collections.emptySet();
        }

        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("client-service");
        if (clientAccess == null) {
            return Collections.emptySet();
        }

        Collection<String> roles = (Collection<String>) clientAccess.get("roles");
        if (roles == null) {
            return Collections.emptySet();
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}

package com.noxabank.backend.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthConverter implements Converter <Jwt, AbstractAuthenticationToken> {
    
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        List<String> roles = List.of();

        if (realmAccess != null && realmAccess.containsKey("roles")) {
            Object rolesObject = realmAccess.get("roles");

            if (rolesObject instanceof List<?> roleList) {
                roles = roleList.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
            }
        }

        Collection<SimpleGrantedAuthority> authorities =
            roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        
        return new UsernamePasswordAuthenticationToken(
            jwt, 
            jwt,
            authorities
        );
    }
}

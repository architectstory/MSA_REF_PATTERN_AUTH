package com.samsungsds.msa.auth.oidc;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.samsungsds.msa.auth.strategy.ApplicationConstant.*;

public class MsaJwtRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        final Map<String, Object> realmAccess
                = (Map<String, Object>) jwt.getClaims().get(JWT_CLAIM_ACCESS_KEY);
        return ((List<String>) realmAccess.get(JWT_CLAIM_KEY_FOR_ROLE_ACCESS))
                .stream()
                .map(roleName -> JWT_ROLE_PREFIX + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}

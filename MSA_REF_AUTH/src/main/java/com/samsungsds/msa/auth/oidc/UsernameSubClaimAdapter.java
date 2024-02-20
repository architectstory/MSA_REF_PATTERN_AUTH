package com.samsungsds.msa.auth.oidc;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;

import java.util.Collections;
import java.util.Map;

import static com.samsungsds.msa.auth.strategy.ApplicationConstant.JWT_CLAIM_KEY_FOR_USERNAME;

public class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>> {

    private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

    @Override
    public Map<String, Object> convert(Map<String, Object> claims) {
        Map<String, Object> convertedClaims = this.delegate.convert(claims);
        String username = (String) convertedClaims.get(JWT_CLAIM_KEY_FOR_USERNAME);
        convertedClaims.put("sub", username);
        return convertedClaims;
    }
}

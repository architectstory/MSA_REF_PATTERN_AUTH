package com.samsungsds.msa.auth.oauth2.token;

import com.samsungsds.msa.auth.strategy.ApplicationConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id:@null}")
    private String clientId;
    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri:@null}")
    private String tokenUrl;

    public TokenResponse getRefreshToken(RefreshTokenRequest refreshTokenRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(ApplicationConstant.KEY_FOR_REFRESH_TOKEN, refreshTokenRequest.getRefreshToken());
        map.add(ApplicationConstant.KEY_FOR_CLIENT_ID, clientId);
        map.add(ApplicationConstant.KEY_FOR_GRANT_TYPE, ApplicationConstant.GRANT_TYPE_FOR_REFRESH_TOKEN);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);
        return new RestTemplate().postForObject(tokenUrl, httpEntity, TokenResponse.class);

    }
}

package com.samsungsds.msa.auth.oauth2.token;

import com.samsungsds.msa.auth.strategy.ApplicationConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.Instant;


@RestController
@RequestMapping("/authentication/v1")
public class Oauth2SecurityTokenController {

    private final Logger logger = LogManager.getLogger(Oauth2SecurityTokenController.class);

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id:@null}")
    private String clientId;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri:@null}")
    private String tokenUrl;

    @GetMapping("/token")
    public ResponseEntity<TokenResponse> getAccessToken(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient){

        TokenResponse tokenResponse = new TokenResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof OAuth2AccessToken){
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String idToken = ((DefaultOidcUser) oAuth2AuthenticationToken.getPrincipal()).getIdToken().getTokenValue();
            logger.info("==== IdToken: " + idToken);
        }
        if(Instant.now().isAfter(authorizedClient.getAccessToken().getExpiresAt())){
            throw new RuntimeException("Token is expired, please login again");
        }
        tokenResponse.setAccessToken(authorizedClient.getAccessToken().getTokenValue());
        tokenResponse.setRefreshToken(authorizedClient.getRefreshToken().getTokenValue());

        return new ResponseEntity<>(tokenResponse, headers, HttpStatus.OK);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<TokenResponse> getRefreshToken(RefreshTokenRequest refreshTokenRequest){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(ApplicationConstant.KEY_FOR_REFRESH_TOKEN, refreshTokenRequest.getRefreshToken());
        map.add(ApplicationConstant.KEY_FOR_CLIENT_ID, "msa-order");
        map.add(ApplicationConstant.KEY_FOR_GRANT_TYPE, ApplicationConstant.GRANT_TYPE_FOR_REFRESH_TOKEN);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        TokenResponse tokenResponse = new RestTemplate().postForObject(tokenUrl, httpEntity, TokenResponse.class);

        headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(tokenResponse, headers, HttpStatus.OK);
    }
}

package com.samsungsds.msa.auth.oauth2;

import com.samsungsds.msa.auth.role.AuthorizeHttpRequest;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "msa.security")
public class Oauth2Request {

    HttpSecurity httpSecurity;
    ClientRegistrationRepository clientRegistrationRepository;

    public Oauth2Request(HttpSecurity httpSecurity, ClientRegistrationRepository clientRegistrationRepository){
        this.httpSecurity = httpSecurity;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    private String registrationId;
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String [] scopes;

    private SecurityIgnoring ignoring;

    private AuthorizeHttpRequest authorizeHttpRequest;

}

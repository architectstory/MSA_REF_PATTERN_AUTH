package com.samsungsds.msa.auth.adapter;

import com.samsungsds.msa.auth.strategy.AuthenticationStrategy;
import com.samsungsds.msa.auth.strategy.OAuth2StrategyImpl;
import com.samsungsds.msa.auth.oauth2.Oauth2Request;
import org.springframework.security.web.SecurityFilterChain;

public class Oauth2AuthenticationAdapterImpl implements AuthenticationAdapter<Oauth2Request, SecurityFilterChain> {

    AuthenticationStrategy authenticationStrategy;

    public Oauth2AuthenticationAdapterImpl(){
        authenticationStrategy = new OAuth2StrategyImpl();
    }

    @Override
    public SecurityFilterChain initiateAuthentication(Oauth2Request oauth2Request) throws Exception {
        return authenticationStrategy.authentication(oauth2Request);
    }

    @Override
    public SecurityFilterChain authorization(Oauth2Request oauth2Request) throws Exception {
        return authenticationStrategy.authorization(oauth2Request);
    }

//    @Override
//    public SecurityFilterChain initiateAuthentication(Oauth2Request oauth2Request, ClientRegistrationRepository clientRegistrationRepository) throws Exception {
//        return authenticationStrategy.oauth2ClientSecurityFilterChain(oauth2Request, clientRegistrationRepository);
//    }
//
//    @Override
//    public SecurityFilterChain configureOauth2ResourceServer(Oauth2Request oauth2Request) throws Exception {
//        return authenticationStrategy.oauth2ResourceServerSecurityFilterChain(oauth2Request);
//    }
}

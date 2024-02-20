package com.samsungsds.msa.auth.strategy;

import com.samsungsds.msa.auth.oauth2.Oauth2Request;
import org.springframework.security.web.SecurityFilterChain;

public interface AuthenticationStrategy<Request, Response> {

    SecurityFilterChain authentication(Request request) throws Exception;
    SecurityFilterChain authorization(Request request) throws Exception;

    SecurityFilterChain authentication(Oauth2Request oauth2Request) throws Exception;

//    SecurityFilterChain oauth2ClientSecurityFilterChain(
//            Oauth2Request oauth2Request, ClientRegistrationRepository clientRegistrationRepository) throws Exception;
//
//    SecurityFilterChain oauth2ResourceServerSecurityFilterChain(
//            Oauth2Request oauth2Request) throws Exception;
}

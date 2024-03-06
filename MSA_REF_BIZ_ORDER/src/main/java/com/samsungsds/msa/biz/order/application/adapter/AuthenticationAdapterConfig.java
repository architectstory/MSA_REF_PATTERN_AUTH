package com.samsungsds.msa.biz.order.application.adapter;

import com.samsungsds.msa.auth.adapter.AuthenticationAdapter;
import com.samsungsds.msa.auth.adapter.Oauth2AuthenticationAdapterImpl;
import com.samsungsds.msa.auth.adapter.Oauth2Request;
import com.samsungsds.msa.biz.order.MsaRefBizOrderApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ComponentScan(basePackageClasses = MsaRefBizOrderApplication.class)
public class AuthenticationAdapterConfig {

    @Bean
    SecurityFilterChain initiateAuthentication(Oauth2Request oauth2Request) throws Exception {
        AuthenticationAdapter<Oauth2Request, SecurityFilterChain> authenticationAdapter
                = new Oauth2AuthenticationAdapterImpl();

        return authenticationAdapter.initiateAuthentication(oauth2Request);
    }
}


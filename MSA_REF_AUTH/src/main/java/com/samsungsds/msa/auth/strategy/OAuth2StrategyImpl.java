package com.samsungsds.msa.auth.strategy;

import com.samsungsds.msa.auth.adapter.Oauth2Request;
import com.samsungsds.msa.auth.adapter.SecurityIgnoring;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
public class OAuth2StrategyImpl implements AuthenticationStrategy<Oauth2Request, SecurityFilterChain> {

    @Override
    public SecurityFilterChain authentication(Oauth2Request oauth2Request) throws Exception {
        ClientRegistrationRepository clientRegistrationRepository = oauth2Request.getClientRegistrationRepository();
        HttpSecurity httpSecurity = oauth2Request.getHttpSecurity();
        setIgnoringAndAuthorizeRequest(oauth2Request, httpSecurity);
        httpSecurity.authorizeHttpRequests(req-> req.anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults()).oauth2Client(Customizer.withDefaults())
                .logout(logout->logout.logoutSuccessHandler(oidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository)))
                .oauth2ResourceServer((oauth2ResourceServer) -> oauth2ResourceServer.jwt(jwt ->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return httpSecurity.build();
    }

    @Override
    public SecurityFilterChain authorization(Oauth2Request oauth2Request) throws Exception {
        HttpSecurity httpSecurity = oauth2Request.getHttpSecurity();
        setIgnoringAndAuthorizeRequest(oauth2Request, httpSecurity);
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .headers(header->header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return httpSecurity.build();
    }
    private OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository){
        OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        oidcClientInitiatedLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/swagger-ui.html");
        return new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
    }

    private void setIgnoringAndAuthorizeRequest(Oauth2Request oauth2Request, HttpSecurity httpSecurity) throws Exception {
        SecurityIgnoring securityIgnoring = oauth2Request.getIgnoring();
        if(Objects.nonNull(securityIgnoring)) {
            setAllowedUrl(httpSecurity, securityIgnoring);
        }
        AuthorizeHttpRequest authorizeHttpRequest = oauth2Request.getAuthorizeHttpRequest();
        if(Objects.nonNull(authorizeHttpRequest)) {
            setSecurityRoleUrlConfiguration(httpSecurity,authorizeHttpRequest);
        }
    }

    @Bean
    public JwtDecoder jwtDecoderByIssuerUri(OAuth2ResourceServerProperties properties){
        String issuerUri = properties.getJwt().getIssuerUri();
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
        jwtDecoder.setClaimSetConverter(new UsernameSubClaimAdapter());
        return jwtDecoder;
    }
    private JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new MsaJwtRealmRoleConverter());
        return jwtAuthenticationConverter;
    }

    private void setSecurityRoleUrlConfiguration(HttpSecurity httpSecurity, AuthorizeHttpRequest authorizeHttpRequest) throws Exception {
        for(RoleUrlMapping roleUrlMapping : authorizeHttpRequest.getRoleUrlMappings()){
            httpSecurity.authorizeHttpRequests(req -> req.requestMatchers(
                    AntPathRequestMatcher.antMatcher(
                            HttpMethod.valueOf(roleUrlMapping.getMethod()),
                            roleUrlMapping.getUrlPattern())
            ).hasAnyRole(roleUrlMapping.getRole()));
        }
    }
    private void setAllowedUrl(HttpSecurity httpSecurity, SecurityIgnoring securityIgnoring)
            throws Exception {
        httpSecurity.authorizeHttpRequests(
                req -> req.requestMatchers(Arrays.stream(securityIgnoring.getUrlAntPatterns())
                        .map(AntPathRequestMatcher::new).toArray(RequestMatcher[]::new)).permitAll());
        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2console/**")));
    }
}

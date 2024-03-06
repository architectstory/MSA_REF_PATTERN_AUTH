package com.samsungsds.msa.biz.order.application.adapter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "OpenApi Specification - MSA ORDER Service"
                ,description = "OpenApi Description for MSA Order Service"
                , version = "1.0")
        , security = {@SecurityRequirement(name = "bearerAuth")})
@SecurityScheme(
        name = "bearerAuth"
        , description = "JWT Auth Access Token"
        , scheme = "bearer"
        , type = SecuritySchemeType.HTTP
        , bearerFormat = "JWT"
        , in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}
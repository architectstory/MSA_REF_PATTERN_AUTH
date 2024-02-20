package com.samsungsds.msa.auth.oauth2.token;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}

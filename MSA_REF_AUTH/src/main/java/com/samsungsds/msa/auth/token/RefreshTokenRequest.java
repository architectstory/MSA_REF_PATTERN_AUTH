package com.samsungsds.msa.auth.token;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}

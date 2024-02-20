package com.samsungsds.msa.auth.oauth2;

import lombok.Data;

@Data
public class SecurityIgnoring {
    private String[] urlAntPatterns;
}

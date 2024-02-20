package com.samsungsds.msa.auth.role;

import lombok.Data;

@Data
public class RoleUrlMapping {
    private String[] role;
    private String urlPattern;
    private String method;
}

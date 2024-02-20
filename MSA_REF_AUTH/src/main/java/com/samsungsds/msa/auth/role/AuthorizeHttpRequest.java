package com.samsungsds.msa.auth.role;

import lombok.Data;

import java.util.List;

@Data
public class AuthorizeHttpRequest {
    private List<RoleUrlMapping> roleUrlMappings;
}

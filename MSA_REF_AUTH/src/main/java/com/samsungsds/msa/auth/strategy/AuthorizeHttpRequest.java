package com.samsungsds.msa.auth.strategy;

import com.samsungsds.msa.auth.strategy.RoleUrlMapping;
import lombok.Data;

import java.util.List;

@Data
public class AuthorizeHttpRequest {
    private List<RoleUrlMapping> roleUrlMappings;
}

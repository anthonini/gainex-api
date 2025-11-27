package com.anthonini.gainex.api.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class SecurityConfigRules {

	public static void apply(HttpSecurity http) throws Exception {
		http
    	.authorizeHttpRequests(authz -> authz
    			.requestMatchers("/categories").permitAll()
    			.anyRequest().authenticated());
	}
}

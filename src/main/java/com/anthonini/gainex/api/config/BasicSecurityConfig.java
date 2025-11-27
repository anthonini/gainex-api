package com.anthonini.gainex.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Profile("basic-security")
@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {

	@Bean
	@Order(1)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		SecurityConfigRules.apply(http);
		
		http
			.httpBasic(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable());
        
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

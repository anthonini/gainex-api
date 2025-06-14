package com.anthonini.gainex.api.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.anthonini.gainex.api.config.property.ApiProperty;
import com.anthonini.gainex.api.security.SystemUser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@Profile("oauth-security")
public class AuthorizationServerConfig {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ApiProperty apiProperty;
	
	@Bean
	RegisteredClientRepository registeredClientRepository() {
		RegisteredClient webClient = RegisteredClient
				.withId(UUID.randomUUID().toString())
				.clientId("gainex")
				.clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUris(uris -> uris.addAll(apiProperty.getSecurity().getPermittedRedirects()))
				.scope(OidcScopes.OPENID)
				.scope(OidcScopes.PROFILE)
				.scope("read")
				.scope("write")
				.clientSettings(ClientSettings.builder()
					.requireProofKey(true)
					.build())
				.build();
		
		RegisteredClient mobileClient = RegisteredClient
				.withId(UUID.randomUUID().toString())
				.clientId("mobile")
				.clientSecret(passwordEncoder.encode("m0b1l3-cl13nt-s3cr3t"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.redirectUris(uris -> uris.addAll(apiProperty.getSecurity().getPermittedRedirects()))
				.scope("read")
				.tokenSettings(TokenSettings.builder()
					.accessTokenTimeToLive(Duration.ofMinutes(30))
					.refreshTokenTimeToLive(Duration.ofDays(24))
					.build())
				.clientSettings(ClientSettings.builder()
					.requireAuthorizationConsent(false)
					.build())
				.build();
		
		return new InMemoryRegisteredClientRepository(Arrays.asList(webClient, mobileClient));
	}
	
	@Bean
	@Order(1)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
			throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

		http
			.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
			.with(authorizationServerConfigurer, (authorizationServer) ->
				authorizationServer
					.oidc(Customizer.withDefaults())
			)
			.authorizeHttpRequests((authorize) ->
				authorize
					.anyRequest().authenticated()
			).formLogin(Customizer.withDefaults());

		return http.cors(Customizer.withDefaults()).build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setAllowedOrigins(apiProperty.getSecurity().getAllowedOrigins());
		config.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", config);
		return source;
	}

    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> jwtBuildCustomizer() {
        return (context) -> {
            UsernamePasswordAuthenticationToken authenticationToken = context.getPrincipal();
            SystemUser systemUser = (SystemUser) authenticationToken.getPrincipal();

            Set<String> authorities = new HashSet<>();
            for (GrantedAuthority grantedAuthority : systemUser.getAuthorities()) {
                authorities.add(grantedAuthority.getAuthority());
            }

            context.getClaims().claim("name", systemUser.getUser().getName());
            context.getClaims().claim("authorities", authorities);
        };
    }

    @Bean
    JWKSet jwkSet() throws JOSEException {
        RSAKey rsa = new RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .generate();

        return new JWKSet(rsa);
    }

    @Bean
    JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer(apiProperty.getSecurity().getAuthServerUrl())
                .build();
    }
}

package com.anthonini.gainex.api.config.property;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("gainex")
public class ApiProperty {

	private final Security security = new Security();

	public Security getSecurity() {
		return security;
	}

	public static class Security {
		private List<String> permittedRedirects;
		private String authServerUrl;
		private List<String> allowedOrigins;
		
		public List<String> getPermittedRedirects() {
			return permittedRedirects;
		}
		
		public void setPermittedRedirects(List<String> permittedRedirects) {
			this.permittedRedirects = permittedRedirects;
		}
		
		public String getAuthServerUrl() {
			return authServerUrl;
		}
		
		public void setAuthServerUrl(String authServerUrl) {
			this.authServerUrl = authServerUrl;
		}

		public List<String> getAllowedOrigins() {
			return allowedOrigins;
		}

		public void setAllowedOrigins(List<String> allowedOrigins) {
			this.allowedOrigins = allowedOrigins;
		}
	}
}

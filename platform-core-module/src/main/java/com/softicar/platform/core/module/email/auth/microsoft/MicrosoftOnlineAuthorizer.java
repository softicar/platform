package com.softicar.platform.core.module.email.auth.microsoft;

import com.softicar.platform.common.string.charset.Charsets;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MicrosoftOnlineAuthorizer {

	private static final String DEFAULT_AUTHORITY_URL = "https://login.microsoftonline.com/%s/oauth2/v2.0/token";
	private String authorityUrl;
	private String tenantId;
	private String clientId;
	private String clientSecret;
	private String grantType;
	private String username;
	private String password;
	private final Set<String> scopes;

	public MicrosoftOnlineAuthorizer() {

		this.authorityUrl = DEFAULT_AUTHORITY_URL;
		this.tenantId = null;
		this.clientId = null;
		this.clientSecret = null;
		this.grantType = null;
		this.username = null;
		this.password = null;
		this.scopes = new TreeSet<>();
	}

	public static String getDefaultAuthorityUrl() {

		return DEFAULT_AUTHORITY_URL;
	}

	public MicrosoftOnlineAuthorizer setAuthorityUrl(String authorityUrl) {

		this.authorityUrl = authorityUrl;
		return this;
	}

	public MicrosoftOnlineAuthorizer setTenantId(String tenantId) {

		this.tenantId = tenantId;
		return this;
	}

	public MicrosoftOnlineAuthorizer setClientId(String clientId) {

		this.clientId = clientId;
		return this;
	}

	public MicrosoftOnlineAuthorizer setClientSecret(String clientSecret) {

		this.clientSecret = clientSecret;
		return this;
	}

	public MicrosoftOnlineAuthorizer setGrantType(String grantType) {

		this.grantType = grantType;
		return this;
	}

	public MicrosoftOnlineAuthorizer setUsername(String username) {

		this.username = username;
		return this;
	}

	public MicrosoftOnlineAuthorizer setPassword(String password) {

		this.password = password;
		return this;
	}

	public MicrosoftOnlineAuthorizer addScope(String scope) {

		scopes.add(scope);
		return this;
	}

	public MicrosoftOnlineAuthorization authorize() {

		try {
			var address = authorityUrl.formatted(tenantId);
			var request = HttpRequest//
				.newBuilder()
				.uri(new URI(address))
				.POST(HttpRequest.BodyPublishers.ofString(getParameterString()))
				.build();

			try (var client = HttpClient//
				.newBuilder()
				.build()) {
				var response = client.send(request, HttpResponse.BodyHandlers.ofString());
				return new MicrosoftOnlineAuthorization(response);
			}

		} catch (IOException | URISyntaxException | InterruptedException exception) {
			throw new RuntimeException(exception);
		}
	}

	private String getParameterString() {

		var parameters = new TreeMap<String, String>();
		parameters.put("client_id", clientId);
		parameters.put("client_secret", clientSecret);
		parameters.put("grant_type", grantType);
		parameters.put("username", username);
		parameters.put("password", password);
		parameters.put("scope", scopes.stream().collect(Collectors.joining(" ")));
		return getParameterString(parameters);
	}

	private static String getParameterString(Map<String, String> parameters) {

		return parameters//
			.entrySet()
			.stream()
			.map(entry -> encode(entry))
			.collect(Collectors.joining("&"));
	}

	private static String encode(Map.Entry<String, String> entry) {

		return encode(entry.getKey()) + "=" + encode(entry.getValue());
	}

	private static String encode(String text) {

		return URLEncoder.encode(text, Charsets.UTF8);
	}
}

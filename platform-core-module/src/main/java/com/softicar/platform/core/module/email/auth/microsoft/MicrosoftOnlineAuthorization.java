package com.softicar.platform.core.module.email.auth.microsoft;

import com.softicar.platform.common.io.serialization.json.JsonValueReader;
import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import java.util.Optional;

public class MicrosoftOnlineAuthorization {

	private final int statusCode;
	private final String responseBody;

	public MicrosoftOnlineAuthorization(HttpResponse<String> response) {

		this.statusCode = response.statusCode();
		this.responseBody = response.body();
	}

	public boolean isSuccessfull() {

		return statusCode == HttpURLConnection.HTTP_OK;
	}

	public Optional<String> getAccessToken() {

		return new JsonValueReader(responseBody).readValue("access_token");
	}

	public String getAccessTokenOrThrow() {

		return getAccessToken().orElseThrow(() -> new RuntimeException(responseBody));
	}
}

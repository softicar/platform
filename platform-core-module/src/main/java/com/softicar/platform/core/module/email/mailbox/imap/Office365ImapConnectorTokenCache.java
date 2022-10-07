package com.softicar.platform.core.module.email.mailbox.imap;

import com.google.gson.Gson;
import com.softicar.platform.core.module.server.AGServer;
import java.util.Objects;
import java.util.Optional;

class Office365ImapConnectorTokenCache {

	private final String username;
	private final String accessToken;

	public Office365ImapConnectorTokenCache(String username, String accessToken) {

		this.username = username;
		this.accessToken = accessToken;
	}

	public static Optional<String> getAccessToken(AGServer server) {

		return Optional//
			.ofNullable(server.getConnectorCache())
			.map(json -> new Gson().fromJson(json, Office365ImapConnectorTokenCache.class))
			.filter(cache -> cache.isValidFor(server.getUsername()))
			.map(cache -> cache.getAccessToken());
	}

	public static void save(AGServer server, String accessToken) {

		server//
			.setConnectorCache(new Office365ImapConnectorTokenCache(server.getUsername(), accessToken).toJson())
			.save();
	}

	private String toJson() {

		return new Gson().toJson(this);
	}

	private boolean isValidFor(String username) {

		return Objects.equals(this.username, username) && !accessToken.isBlank();
	}

	private String getAccessToken() {

		return accessToken;
	}
}

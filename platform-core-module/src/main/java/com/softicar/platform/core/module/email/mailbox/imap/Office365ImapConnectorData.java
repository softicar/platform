package com.softicar.platform.core.module.email.mailbox.imap;

import com.google.gson.Gson;
import com.softicar.platform.core.module.server.AGServer;
import java.util.Objects;
import java.util.Optional;

class Office365ImapConnectorData {

	private final String username;
	private final String accessToken;

	public Office365ImapConnectorData(String username, String accessToken) {

		this.username = username;
		this.accessToken = accessToken;
	}

	public static Optional<String> getAccessToken(AGServer server) {

		return Optional//
			.ofNullable(server.getConnectorData())
			.map(json -> new Gson().fromJson(json, Office365ImapConnectorData.class))
			.filter(cache -> cache.isValidFor(server.getUsername()))
			.map(cache -> cache.getAccessToken());
	}

	public static void save(AGServer server, String accessToken) {

		server//
			.setConnectorData(new Office365ImapConnectorData(server.getUsername(), accessToken).toJson())
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

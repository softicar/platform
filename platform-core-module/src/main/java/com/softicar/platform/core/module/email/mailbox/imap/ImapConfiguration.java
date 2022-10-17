package com.softicar.platform.core.module.email.mailbox.imap;

import com.google.gson.Gson;
import java.util.Properties;

class ImapConfiguration {

	public String protocol = "imaps";
	public String trustedHosts = "*";
	public int connectionTimeout = 10000;

	public static ImapConfiguration fromJson(String json) {

		return json == null || json.isBlank()? new ImapConfiguration() : new Gson().fromJson(json, ImapConfiguration.class);
	}

	public String toJson() {

		return new Gson().toJson(this);
	}

	public Properties getProperties() {

		var properties = new Properties();
		properties.put("mail.store.protocol", protocol);
		properties.put("mail.%s.ssl.trust".formatted(protocol), trustedHosts);
		properties.put("mail.%s.connectiontimeout".formatted(protocol), connectionTimeout);
		return properties;
	}
}

package com.softicar.platform.core.module.email.mailbox.imap;

import com.google.gson.Gson;

class Office365ImapConfiguration {

	public String tenantId;
	public String clientId;
	public String clientSecret;
	public int connectionTimeout = 10000;

	public static Office365ImapConfiguration fromJson(String json) {

		return json == null || json.isBlank()? new Office365ImapConfiguration() : new Gson().fromJson(json, Office365ImapConfiguration.class);
	}

	public String toJson() {

		return new Gson().toJson(this);
	}
}

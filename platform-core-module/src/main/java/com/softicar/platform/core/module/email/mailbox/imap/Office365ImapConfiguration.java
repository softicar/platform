package com.softicar.platform.core.module.email.mailbox.imap;

import com.google.gson.Gson;
import com.softicar.platform.core.module.email.auth.microsoft.MicrosoftOnlineAuthorizer;

class Office365ImapConfiguration {

	public String authorityUrl = MicrosoftOnlineAuthorizer.getDefaultAuthorityUrl();
	public String accessScope = "https://outlook.office.com/IMAP.AccessAsUser.All";
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

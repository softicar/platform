package com.softicar.platform.core.module.file.smb.jcifs;

import com.softicar.platform.core.module.file.smb.ISmbApi;
import com.softicar.platform.core.module.file.smb.ISmbCredentials;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import jcifs.Config;
import jcifs.smb.NtlmPasswordAuthentication;

public class JcifsSmbApi implements ISmbApi {

	@Override
	public void initialize() {

		disableDfs();
	}

	@Override
	public ISmbFile createFile(String url, ISmbCredentials credentials) {

		var authentication = new NtlmPasswordAuthentication(credentials.getDomain(), credentials.getUsername(), credentials.getPassword());
		return new JcifsSmbFile(url, authentication);
	}

	private void disableDfs() {

		// FIXME vvvv explain why. don't make obscure claims. digging out #38419 should not be necessary to understand
		// the reason. vvvv
		// this is very important (see #38419)
		Config.setProperty("jcifs.smb.client.dfs.disabled", "true");
	}
}

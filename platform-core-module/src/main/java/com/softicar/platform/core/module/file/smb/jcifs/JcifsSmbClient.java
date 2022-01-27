package com.softicar.platform.core.module.file.smb.jcifs;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.core.module.file.smb.ISmbClient;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbCredentials;
import java.util.Properties;
import jcifs.CIFSException;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthenticator;

public class JcifsSmbClient implements ISmbClient {

	private static final BaseContext BASE_CONTEXT = setupBaseContext();

	@Override
	public ISmbFile createFile(String url, SmbCredentials credentials) {

		var cifsContext =
				BASE_CONTEXT.withCredentials(new NtlmPasswordAuthenticator(credentials.getDomain(), credentials.getUsername(), credentials.getPassword()));
		return new JcifsSmbFile(url, cifsContext);
	}

	@Override
	public ISmbDirectory createDirectory(String url, SmbCredentials credentials) {

		var cifsContext =
				BASE_CONTEXT.withCredentials(new NtlmPasswordAuthenticator(credentials.getDomain(), credentials.getUsername(), credentials.getPassword()));
		return new JcifsSmbDirectory(url, cifsContext);
	}

	private static BaseContext setupBaseContext() {

		try {
			// FIXME vvvv explain why. don't make obscure claims. digging out #38419 should not be necessary to understand
			// the reason. vvvv
			// this is very important (see #38419)
			Properties jcifsProperties = new Properties();
			jcifsProperties.setProperty("jcifs.smb.client.dfs.disabled", "true");
			return new BaseContext(new PropertyConfiguration(jcifsProperties));
		} catch (CIFSException exception) {
			throw new SofticarException(exception);
		}
	}
}

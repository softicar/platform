package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.core.module.file.smb.ISmbClient;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbCredentials;
import java.util.Properties;
import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthenticator;

public class JcifsNgSmbClient implements ISmbClient {

	private static final BaseContext BASE_CONTEXT = setupBaseContext();

	@Override
	public ISmbFile createFile(String url, SmbCredentials credentials) {

		return new JcifsNgSmbFile(url, createContext(credentials));
	}

	@Override
	public ISmbDirectory createDirectory(String url, SmbCredentials credentials) {

		return new JcifsNgSmbDirectory(url, createContext(credentials));
	}

	private CIFSContext createContext(SmbCredentials credentials) {

		return BASE_CONTEXT.withCredentials(new NtlmPasswordAuthenticator(credentials.getDomain(), credentials.getUsername(), credentials.getPassword()));
	}

	private static BaseContext setupBaseContext() {

		try {
			return new BaseContext(new PropertyConfiguration(new Properties()));
		} catch (CIFSException exception) {
			throw new SofticarException(exception);
		}
	}
}

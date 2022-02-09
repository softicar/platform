package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.core.module.file.smb.ISmbClient;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbCredentials;

public class JcifsNgSmbClient implements ISmbClient {

	@Override
	public ISmbEntry getEntry(String url, SmbCredentials credentials) {

		return new JcifsNgSmbEntry(url, JcifsNgSmbCifsContextFactory.create(credentials));
	}

	@Override
	public ISmbFile getFile(String url, SmbCredentials credentials) {

		return new JcifsNgSmbFile(url, JcifsNgSmbCifsContextFactory.create(credentials));
	}

	@Override
	public ISmbDirectory getDirectory(String url, SmbCredentials credentials) {

		return new JcifsNgSmbDirectory(url, JcifsNgSmbCifsContextFactory.create(credentials));
	}
}

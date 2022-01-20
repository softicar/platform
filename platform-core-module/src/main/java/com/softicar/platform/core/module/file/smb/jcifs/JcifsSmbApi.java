package com.softicar.platform.core.module.file.smb.jcifs;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.core.module.file.smb.ISmbApi;
import com.softicar.platform.core.module.file.smb.ISmbCredentials;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import jcifs.Config;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

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

	@Override
	public InputStream createInputStream(ISmbFile file) {

		JcifsSmbFile jcifsFile = (JcifsSmbFile) file;
		try {
			return new SmbFileInputStream(jcifsFile.getSmbFile());
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		} catch (MalformedURLException exception) {
			throw new SofticarIOException(exception);
		} catch (UnknownHostException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public OutputStream createOutputStream(ISmbFile file) {

		JcifsSmbFile jcifsFile = (JcifsSmbFile) file;
		try {
			return new SmbFileOutputStream(jcifsFile.getSmbFile());
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		} catch (MalformedURLException exception) {
			throw new SofticarIOException(exception);
		} catch (UnknownHostException exception) {
			throw new SofticarIOException(exception);
		}
	}
}

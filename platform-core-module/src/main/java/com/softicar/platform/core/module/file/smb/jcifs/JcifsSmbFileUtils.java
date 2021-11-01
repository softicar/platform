package com.softicar.platform.core.module.file.smb.jcifs;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import jcifs.Config;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

public class JcifsSmbFileUtils {

	public static void disableDfs() {

		Config.setProperty("jcifs.smb.client.dfs.disabled", "true");
	}

	public static SmbFile createFile(String url, NtlmPasswordAuthentication auth) {

		try {
			return new SmbFile(url, auth);
		} catch (MalformedURLException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public static SmbFileInputStream createInputStream(SmbFile file) {

		try {
			return new SmbFileInputStream(file);
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		} catch (MalformedURLException exception) {
			throw new SofticarIOException(exception);
		} catch (UnknownHostException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public static SmbFileOutputStream createOutputStream(SmbFile file) {

		try {
			return new SmbFileOutputStream(file);
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		} catch (MalformedURLException exception) {
			throw new SofticarIOException(exception);
		} catch (UnknownHostException exception) {
			throw new SofticarIOException(exception);
		}
	}
}

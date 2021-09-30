package com.softicar.platform.core.module.file.smb.jcifs;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Optional;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

public class JcifsSmbFile implements ISmbFile {

	protected final SmbFile file;
	protected final NtlmPasswordAuthentication auth;

	public JcifsSmbFile(String url, NtlmPasswordAuthentication auth) {

		try {
			this.file = new SmbFile(url, auth);
			this.auth = auth;
		} catch (MalformedURLException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public JcifsSmbFile(String context, String name, NtlmPasswordAuthentication auth) {

		try {
			this.file = new SmbFile(context, name, auth);
			this.auth = auth;
		} catch (MalformedURLException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public String getName() {

		return file.getName();
	}

	@Override
	public String getCanonicalPath() {

		return file.getCanonicalPath();
	}

	@Override
	public boolean exists() {

		try {
			return file.exists();
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public boolean isDirectory() {

		try {
			return file.isDirectory();
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public boolean isFile() {

		try {
			return file.isFile();
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public InputStream getInputStream() {

		try {
			return new SmbFileInputStream(file);
		} catch (SmbException | MalformedURLException | UnknownHostException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public Optional<ISmbDirectory> asDirectory() {

		if (isDirectory()) {
			return Optional.of(new JcifsSmbDirectory(file.getCanonicalPath(), auth));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public ISmbDirectory getParentDirectory() {

		return new JcifsSmbDirectory(file.getParent(), auth);
	}

	@Override
	public ISmbFile moveTo(ISmbDirectory parent) {

		return moveAndRenameTo(parent, file.getName());
	}

	@Override
	public ISmbFile renameTo(String name) {

		return moveAndRenameTo(getParentDirectory(), name);
	}

	@Override
	public ISmbFile moveAndRenameTo(ISmbDirectory parent, String name) {

		try {
			SmbFile target = new SmbFile(parent.getCanonicalPath(), name, auth);
			file.renameTo(target);
			return new JcifsSmbFile(target.getCanonicalPath(), auth);
		} catch (SmbException | MalformedURLException exception) {
			throw new RuntimeException(exception);
		}
	}
}

package com.softicar.platform.core.module.file.smb.jcifs;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

class JcifsSmbDirectory extends JcifsSmbFile implements ISmbDirectory {

	public JcifsSmbDirectory(String url, NtlmPasswordAuthentication auth) {

		super(appendSlashIfMissing(url), auth);
	}

	public JcifsSmbDirectory(String context, String name, NtlmPasswordAuthentication auth) {

		super(context, appendSlashIfMissing(name), auth);
	}

	@Override
	public List<ISmbFile> listFiles() {

		try {
			return Arrays//
				.asList(file.listFiles())
				.stream()
				.map(this::wrapSmbFile)
				.collect(Collectors.toList());
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void mkdirs() {

		try {
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public ISmbFile getFile(String name) {

		return new JcifsSmbFile(file.getCanonicalPath(), name, auth);
	}

	@Override
	public ISmbDirectory getSubDirectory(String name) {

		return new JcifsSmbDirectory(file.getCanonicalPath(), name, auth);
	}

	@Override
	public InputStream getInputStream() {

		throw new UnsupportedOperationException("Cannot create an InputStream of a directory.");
	}

	@Override
	public ISmbDirectory moveTo(ISmbDirectory parent) {

		return moveAndRenameTo(parent, getName());
	}

	@Override
	public ISmbDirectory renameTo(String name) {

		return moveAndRenameTo(getParentDirectory(), name);
	}

	@Override
	public ISmbDirectory moveAndRenameTo(ISmbDirectory parent, String name) {

		return super.moveAndRenameTo(parent, name).asDirectory().orElseThrow();
	}

	private ISmbFile wrapSmbFile(SmbFile smbFile) {

		return new JcifsSmbFile(smbFile.getCanonicalPath(), auth);
	}

	private static String appendSlashIfMissing(String path) {

		return !path.endsWith("/")? path + '/' : path;
	}
}

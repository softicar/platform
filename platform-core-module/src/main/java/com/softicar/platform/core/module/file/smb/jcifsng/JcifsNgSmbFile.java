package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbIOException;
import com.softicar.platform.core.module.file.smb.SmbNoFileException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import jcifs.CIFSContext;
import jcifs.SmbResource;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

class JcifsNgSmbFile extends JcifsNgSmbEntry implements ISmbFile {

	public JcifsNgSmbFile(String url, CIFSContext context) {

		super(url, context);
		assertFileIfExists();
	}

	JcifsNgSmbFile(SmbResource parent, String name) {

		super(parent, name);
		assertFileIfExists();
	}

	@Override
	public String getUrl() {

		return Trim.trimRight(super.getUrl(), '/');
	}

	@Override
	public long getSize() {

		try {
			return entry.length();
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public ISmbFile moveTo(ISmbDirectory parent) {

		return moveAndRenameTo(parent, entry.getName());
	}

	@Override
	public ISmbFile moveTo(ISmbFile file) {

		return moveAndRenameTo(file.getUrl());
	}

	@Override
	public ISmbFile copyTo(ISmbFile file) {

		try (SmbFile target = new SmbFile(file.getUrl(), context)) {
			entry.copyTo(target);
			return wrapFile(target);
		} catch (SmbException | MalformedURLException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public ISmbFile copyTo(ISmbDirectory directory) {

		try (SmbFile target = new SmbFile(concatUrl(directory.getUrl(), getName()), context)) {
			entry.copyTo(target);
			return wrapFile(target);
		} catch (SmbException | MalformedURLException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public ISmbFile renameTo(String fileName) {

		return moveAndRenameTo(getParentDirectory(), fileName);
	}

	@Override
	public ISmbFile touch() {

		try (var outputStream = createOutputStream()) {
			return this;
		} catch (IOException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public InputStream createInputStream() {

		try {
			return new SmbFileInputStream(entry);
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public OutputStream createOutputStream() {

		try {
			return new SmbFileOutputStream(entry);
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	private ISmbFile moveAndRenameTo(ISmbDirectory parent, String fileName) {

		return moveAndRenameTo(concatUrl(parent.getUrl(), fileName));
	}

	private ISmbFile moveAndRenameTo(String url) {

		assertValidSmbUrl(url);
		try (SmbFile target = new SmbFile(url, context)) {
			if (!target.exists()) {
				entry.copyTo(target);
				entry.delete();
				return wrapFile(target);
			} else {
				throw new SmbException("The target already exists.");
			}
		} catch (SmbException | MalformedURLException exception) {
			throw new SmbIOException(exception);
		}
	}

	private void assertFileIfExists() {

		if (exists() && !isFile()) {
			throw new SmbNoFileException();
		}
	}
}

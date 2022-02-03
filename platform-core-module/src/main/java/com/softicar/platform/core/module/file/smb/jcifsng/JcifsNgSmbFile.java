package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbExpectedFileException;
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

	public JcifsNgSmbFile(SmbResource parent, String name) {

		super(parent, name);
		assertFile();
	}

	public JcifsNgSmbFile(String url, CIFSContext context) {

		super(url, context);
		assertFile();
	}

	@Override
	public long getSize() {

		try {
			return entry.length();
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public ISmbFile moveTo(ISmbDirectory parent) {

		return moveAndRenameTo(parent, entry.getName());
	}

	@Override
	public ISmbFile copyTo(ISmbFile file) {

		try (SmbFile target = new SmbFile(file.getUrl(), context)) {
			entry.copyTo(target);
			return wrapFile(target);
		} catch (SmbException | MalformedURLException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public ISmbFile copyTo(ISmbDirectory directory) {

		try (SmbFile target = new SmbFile(directory.getUrl() + getName(), context)) {
			entry.copyTo(target);
			return wrapFile(target);
		} catch (SmbException | MalformedURLException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public ISmbFile renameTo(String name) {

		return moveAndRenameTo(getParentDirectory(), name);
	}

	@Override
	public ISmbFile moveAndRenameTo(ISmbDirectory parent, String name) {

		try (SmbFile target = new SmbFile(parent.getUrl() + name, context)) {
			Log.finfo("moveAndRenameTo: %s", target.getCanonicalPath());
			entry.renameTo(target);
			return wrapFile(target);
		} catch (SmbException | MalformedURLException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public ISmbFile touch() {

		try (var outputStream = createOutputStream()) {
			return this;
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public InputStream createInputStream() {

		try {
			return new SmbFileInputStream(entry);
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public OutputStream createOutputStream() {

		try {
			return new SmbFileOutputStream(entry);
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void assertFile() {

		if (exists() && !isFile()) {
			throw new SmbExpectedFileException();
		}
	}
}

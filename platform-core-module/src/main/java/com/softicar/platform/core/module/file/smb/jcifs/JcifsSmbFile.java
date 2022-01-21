package com.softicar.platform.core.module.file.smb.jcifs;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Optional;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

class JcifsSmbFile implements ISmbFile {

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
	public long getFreeDiskSpace() {

		//TODO Questionable behavior, returning 0 does not seem normal
		try {
			return file.getDiskFreeSpace();
		} catch (SmbException exception) {
			Log.ferror("Could not determine free disk space of share.");
			DevNull.swallow(exception);
			return 0;
		}
	}

	@Override
	public long getSize() {

		try {
			return file.length();
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public DayTime getLastModifiedDate() {

		try {
			return DayTime.fromDate(new Date(file.lastModified()));
		} catch (SmbException exception) {
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
	public void delete() {

		try {
			file.delete();
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
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

	@Override
	public InputStream createInputStream() {

		try {
			return new SmbFileInputStream(file);
		} catch (SmbException | MalformedURLException | UnknownHostException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public OutputStream createOutputStream() {

		try {
			return new SmbFileOutputStream(file);
		} catch (SmbException | MalformedURLException | UnknownHostException exception) {
			throw new SofticarIOException(exception);
		}
	}
}

package com.softicar.platform.core.module.file.smb.jcifsng;

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
import jcifs.CIFSContext;
import jcifs.SmbResource;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

class JcifsNgSmbFile implements ISmbFile {

	protected final SmbFile file;
	protected final CIFSContext context;

	public JcifsNgSmbFile(String url, CIFSContext context) {

		try {
			this.file = new SmbFile(url, context);
			this.context = context;
		} catch (MalformedURLException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public JcifsNgSmbFile(SmbResource parent, String name) {

		try {
			this.file = new SmbFile(parent, name);
			this.context = parent.getContext();
		} catch (MalformedURLException | UnknownHostException exception) {
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
			return Optional.of(new JcifsNgSmbDirectory(file.getCanonicalPath(), context));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public ISmbDirectory getParentDirectory() {

		return new JcifsNgSmbDirectory(file.getParent(), context);
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
	public void copyTo(String url) {

		if (isDirectory()) {
			if (!url.endsWith("/")) {
				url = url + "/";
			}
		}
		try (SmbFile target = new SmbFile(url, context)) {
			file.copyTo(target);
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

		try (SmbFile target = new SmbFile(parent.getCanonicalPath() + name, context)) {
			file.renameTo(target);
			return new JcifsNgSmbFile(target.getCanonicalPath(), context);
		} catch (SmbException | MalformedURLException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public InputStream createInputStream() {

		try {
			return new SmbFileInputStream(file);
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public OutputStream createOutputStream() {

		try {
			return new SmbFileOutputStream(file);
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}
}

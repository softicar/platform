package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Optional;
import jcifs.CIFSContext;
import jcifs.SmbResource;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class JcifsNgSmbEntry implements ISmbEntry {

	protected final SmbFile entry;
	protected final CIFSContext context;

	public JcifsNgSmbEntry(String url, CIFSContext context) {

		try {
			this.entry = new SmbFile(url, context);
			this.context = context;
		} catch (MalformedURLException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public JcifsNgSmbEntry(SmbResource parent, String name) {

		try {
			this.entry = new SmbFile(parent, name);
			this.context = parent.getContext();
		} catch (MalformedURLException | UnknownHostException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public String getName() {

		return entry.getName();
	}

	@Override
	public String getUrl() {

		return entry.getCanonicalPath();
	}

	@Override
	public boolean exists() {

		try {
			return entry.exists();
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void delete() {

		try {
			entry.delete();
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public DayTime getLastModifiedDate() {

		try {
			return DayTime.fromDate(new Date(entry.lastModified()));
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public ISmbDirectory getParentDirectory() {

		return new JcifsNgSmbDirectory(entry.getParent(), context);
	}

	@Override
	public long getFreeDiskSpace() {

		try {
			return entry.getDiskFreeSpace();
		} catch (SmbException exception) {
			// TODO Questionable behavior:
			// TODO Why would we assume 0 instead of (quasi-)infinite space in this case?
			// TODO Why would we even catch this?
			Log.ferror("Could not determine free disk space of share.");
			DevNull.swallow(exception);
			return 0;
		}
	}

	@Override
	public boolean isFile() {

		try {
			return entry.isFile();
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public boolean isDirectory() {

		try {
			return entry.isDirectory();
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public Optional<ISmbFile> asFile() {

		if (isFile()) {
			return Optional.of(new JcifsNgSmbFile(entry.getCanonicalPath(), context));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ISmbDirectory> asDirectory() {

		if (isDirectory()) {
			return Optional.of(new JcifsNgSmbDirectory(entry.getCanonicalPath(), context));
		} else {
			return Optional.empty();
		}
	}

	protected ISmbFile wrapFile(SmbFile smbFile) {

		return new JcifsNgSmbFile(smbFile.getCanonicalPath(), context);
	}

	protected ISmbDirectory wrapDirectory(SmbFile smbFile) {

		return new JcifsNgSmbDirectory(smbFile.getCanonicalPath(), context);
	}

	protected ISmbEntry wrapEntry(SmbFile smbFile) {

		return new JcifsNgSmbEntry(smbFile.getCanonicalPath(), context);
	}
}

package com.softicar.platform.core.module.file.smb.jcifs;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

class JcifsSmbFile implements ISmbFile {

	protected final SmbFile file;
	protected final NtlmPasswordAuthentication auth;

	public JcifsSmbFile(SmbFile file, NtlmPasswordAuthentication auth) {

		this.file = file;
		this.auth = auth;
	}

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

	public SmbFile getSmbFile() {

		return file;
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

		try {
			return file.getDiskFreeSpace();
		} catch (SmbException exception) {
			DevNull.swallow(exception);
			return 0;
		}
	}

	@Override
	public long length() {

		try {
			return file.length();
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public DayTime lastModified() {

		try {
			return DayTime.fromDate(new Date(file.lastModified()));
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public Collection<String> listAllFiles(String prefix, Collection<String> filenames) {

		try {
			for (SmbFile file: file.listFiles()) {
				if (file.isDirectory()) {
					String subFolder = Trim.trimRight(file.getName(), '/');
					new JcifsSmbFile(file, auth).listAllFiles(prefix + "/" + subFolder, filenames);
				} else {
					filenames.add(prefix + "/" + file.getName());
				}
			}
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
		return filenames;
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
	public void mkdirs() {

		try {
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
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
}

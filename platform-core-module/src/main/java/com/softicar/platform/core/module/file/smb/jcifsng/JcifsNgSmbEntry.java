package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbIOException;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import jcifs.CIFSContext;
import jcifs.SmbResource;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class JcifsNgSmbEntry implements ISmbEntry {

	private static final String SMB_PROTOCOL_PREFIX = "smb://";
	protected final SmbFile entry;
	protected final CIFSContext context;

	public JcifsNgSmbEntry(String url, CIFSContext context) {

		this(url, context, null, null);
	}

	// TODO remove debug CTOR
	public JcifsNgSmbEntry(String url, CIFSContext context, String uuid, Long start) {

		try {
			// TODO remove
			if (uuid != null && start != null) {
				Log.ferror("%s: %sms: JcifsNgSmbEntry CTOR: CAL", uuid, System.currentTimeMillis() - start);
			}

			assertValidSmbUrl(url);
			Objects.requireNonNull(context);

			// TODO remove
			if (uuid != null && start != null) {
				Log.ferror("%s: %sms: JcifsNgSmbEntry CTOR: creating SmbFile", uuid, System.currentTimeMillis() - start);
			}
			this.entry = new SmbFile(url, context);

			// TODO remove
			if (uuid != null && start != null) {
				Log.ferror("%s: %sms: JcifsNgSmbEntry CTOR: connecting", uuid, System.currentTimeMillis() - start);
			}
			this.entry.connect();

			// TODO remove
			if (uuid != null && start != null) {
				Log.ferror("%s: %sms: JcifsNgSmbEntry CTOR: connected", uuid, System.currentTimeMillis() - start);
			}
			this.context = context;

			// TODO remove
			if (uuid != null && start != null) {
				Log.ferror("%s: %sms: JcifsNgSmbEntry CTOR: RET", uuid, System.currentTimeMillis() - start);
			}
		} catch (IOException exception) {
			throw new SmbIOException(exception);
		}
	}

	JcifsNgSmbEntry(SmbResource parent, String name) {

		try {
			Objects.requireNonNull(name);
			assertNoAdjacentSlashes(name);
			this.entry = new SmbFile(parent, name);
			this.entry.connect();
			this.context = parent.getContext();
		} catch (IOException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public String getName() {

		return Trim.trimRight(entry.getName(), '/');
	}

	@Override
	public String getUrl() {

		String url = entry.getCanonicalPath();
		if (isDirectory()) {
			return Trim.trimRight(entry.getCanonicalPath(), '/').concat("/");
		} else if (isFile()) {
			return Trim.trimRight(entry.getCanonicalPath(), '/');
		} else {
			return url;
		}
	}

	@Override
	public boolean exists() {

		try {
			return entry.exists();
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public void delete() {

		try {
			if (isDirectory()) {
				asDirectoryOrThrow().listFiles().forEach(ISmbFile::delete);
				asDirectoryOrThrow().listDirectories().forEach(ISmbDirectory::delete);
			}
			entry.delete();
		} catch (IOException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public DayTime getLastModifiedDate() {

		try {
			return DayTime.fromDate(new Date(entry.lastModified()));
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public ISmbDirectory getParentDirectory() {

		return new JcifsNgSmbDirectory(entry.getParent(), context);
	}

	@Override
	public long getFreeDiskSpace() {

		try {
			if (entry.exists()) {
				return entry.getDiskFreeSpace();
			} else {
				return 0;
			}
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public boolean isFile() {

		try {
			return entry.isFile();
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public boolean isDirectory() {

		try {
			return entry.isDirectory();
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public Optional<ISmbFile> asFile() {

		if (isFile() || !exists()) {
			return Optional.of(new JcifsNgSmbFile(entry.getCanonicalPath(), context));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<ISmbDirectory> asDirectory() {

		if (isDirectory() || !exists()) {
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

	protected String concatUrl(String prefix, String suffix) {

		Objects.requireNonNull(suffix);
		String url = Trim.trimRight(prefix, '/') + "/" + Trim.trimLeft(suffix, '/');
		return assertValidSmbUrl(url);
	}

	protected String assertValidSmbUrl(String url) {

		Objects.requireNonNull(url);
		if (!url.startsWith(SMB_PROTOCOL_PREFIX)) {
			throw new IllegalArgumentException("The URL must start with 'smb://'.");
		}
		assertNoAdjacentSlashes(url.substring(SMB_PROTOCOL_PREFIX.length(), url.length()));
		return url;
	}

	private void assertNoAdjacentSlashes(String urlToken) {

		if (urlToken.contains("//")) {
			throw new IllegalArgumentException("The URL must not contain adjacent slashes after the protocol prefix.");
		}
	}
}

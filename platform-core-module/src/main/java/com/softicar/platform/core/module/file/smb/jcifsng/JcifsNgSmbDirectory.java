package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbIOException;
import com.softicar.platform.core.module.file.smb.SmbNoDirectoryException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import jcifs.CIFSContext;
import jcifs.SmbResource;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

class JcifsNgSmbDirectory extends JcifsNgSmbEntry implements ISmbDirectory {

	public JcifsNgSmbDirectory(String url, CIFSContext context) {

		this(url, context, null, null);
	}

	// TODO remove debug CTOR
	public JcifsNgSmbDirectory(String url, CIFSContext context, String uuid, Long start) {

		super(appendSlashIfMissing(url), context, uuid, start);
		assertDirectoryIfExists();
	}

	JcifsNgSmbDirectory(SmbResource parent, String name) {

		super(parent, appendSlashIfMissing(name));
		assertDirectoryIfExists();
	}

	@Override
	public String getUrl() {

		return Trim.trimRight(super.getUrl(), '/') + '/';
	}

	@Override
	public ISmbDirectory makeDirectories() {

		try {
			if (!entry.exists()) {
				entry.mkdirs();
			}
			return this;
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public List<ISmbFile> listFiles() {

		return list(this::isFile, this::wrapFile);
	}

	@Override
	public List<ISmbFile> listFilesRecursively() {

		List<ISmbFile> files = new ArrayList<>();
		files.addAll(listFiles());
		for (ISmbDirectory directory: listDirectories()) {
			files.addAll(directory.listFilesRecursively());
		}
		return files;
	}

	@Override
	public List<ISmbDirectory> listDirectories() {

		return list(this::isDirectory, this::wrapDirectory);
	}

	@Override
	public List<ISmbEntry> listEntries() {

		return list(Predicates.always(), this::wrapEntry);
	}

	@Override
	public ISmbFile getFile(String fileName) {

		return new JcifsNgSmbFile(entry, fileName);
	}

	@Override
	public ISmbDirectory getDirectory(String directoryName) {

		return new JcifsNgSmbDirectory(entry, directoryName);
	}

	@Override
	public ISmbDirectory copyContentInto(ISmbDirectory directory) {

		try (SmbFile target = new SmbFile(directory.getUrl(), context)) {
			entry.copyTo(target);
			return wrapDirectory(target);
		} catch (SmbException | MalformedURLException exception) {
			throw new SmbIOException(exception);
		}
	}

	@Override
	public ISmbDirectory moveInto(ISmbDirectory parent) {

		return moveAndRenameTo(parent, getName());
	}

	@Override
	public ISmbDirectory renameTo(String directoryName) {

		return moveAndRenameTo(getParentDirectory(), directoryName);
	}

	private ISmbDirectory moveAndRenameTo(ISmbDirectory parent, String directoryName) {

		try (SmbFile target = new SmbFile(concatUrl(parent.getUrl(), directoryName), context)) {
			entry.renameTo(target);
			return wrapDirectory(target);
		} catch (SmbException | MalformedURLException exception) {
			throw new SmbIOException(exception);
		}
	}

	private <T extends ISmbEntry> List<T> list(Predicate<SmbFile> filter, Function<SmbFile, T> factory) {

		try {
			return Arrays//
				.asList(entry.listFiles())
				.stream()
				.filter(filter)
				.map(factory::apply)
				.sorted(Comparator.comparing(ISmbEntry::getUrl))
				.collect(Collectors.toList());
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	private boolean isFile(SmbFile smbFile) {

		try {
			return smbFile.isFile();
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	private boolean isDirectory(SmbFile smbFile) {

		try {
			return smbFile.isDirectory();
		} catch (SmbException exception) {
			throw new SmbIOException(exception);
		}
	}

	private void assertDirectoryIfExists() {

		if (exists() && !isDirectory()) {
			throw new SmbNoDirectoryException();
		}
	}

	private static String appendSlashIfMissing(String url) {

		Objects.requireNonNull(url);
		return Trim.trimRight(url, '/') + "/";
	}
}

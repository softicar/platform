package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbNoDirectoryException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import jcifs.CIFSContext;
import jcifs.SmbResource;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

class JcifsNgSmbDirectory extends JcifsNgSmbEntry implements ISmbDirectory {

	public JcifsNgSmbDirectory(SmbResource parent, String name) {

		super(parent, appendSlashIfMissing(name));
		assertDirectory();
	}

	public JcifsNgSmbDirectory(String url, CIFSContext context) {

		super(appendSlashIfMissing(url), context);
		assertDirectory();
	}

	@Override
	public void mkdirs() {

		try {
			if (!entry.exists()) {
				entry.mkdirs();
			}
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
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
		for (ISmbDirectory subDirectory: listSubDirectories()) {
			files.addAll(subDirectory.listFilesRecursively());
		}
		return files;
	}

	@Override
	public List<ISmbDirectory> listSubDirectories() {

		return list(this::isDirectory, this::wrapDirectory);
	}

	@Override
	public List<ISmbEntry> listEntries() {

		return list(Predicates.always(), this::wrapEntry);
	}

	@Override
	public ISmbFile getFile(String name) {

		return new JcifsNgSmbFile(entry, name);
	}

	@Override
	public ISmbDirectory getSubDirectory(String name) {

		return new JcifsNgSmbDirectory(entry, name);
	}

	@Override
	public ISmbDirectory copyTo(ISmbDirectory directory) {

		try (SmbFile target = new SmbFile(directory.getUrl(), context)) {
			entry.copyTo(target);
			return wrapDirectory(target);
		} catch (SmbException | MalformedURLException exception) {
			throw new SofticarException(exception);
		}
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

		try (SmbFile target = new SmbFile(parent.getUrl() + name, context)) {
			entry.renameTo(target);
			return wrapDirectory(target);
		} catch (SmbException | MalformedURLException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static String appendSlashIfMissing(String path) {

		return path + (path.endsWith("/")? "" : "/");
	}

	private <T> List<T> list(Predicate<SmbFile> filter, Function<SmbFile, T> factory) {

		try {
			return Arrays//
				.asList(entry.listFiles())
				.stream()
				.filter(filter)
				.map(factory::apply)
				.collect(Collectors.toList());
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private boolean isFile(SmbFile smbFile) {

		try {
			return smbFile.isFile();
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private boolean isDirectory(SmbFile smbFile) {

		try {
			return smbFile.isDirectory();
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void assertDirectory() {

		if (exists() && !isDirectory()) {
			throw new SmbNoDirectoryException();
		}
	}
}

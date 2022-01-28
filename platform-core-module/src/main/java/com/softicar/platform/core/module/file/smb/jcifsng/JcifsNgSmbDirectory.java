package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import jcifs.CIFSContext;
import jcifs.SmbResource;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

class JcifsNgSmbDirectory extends JcifsNgSmbFile implements ISmbDirectory {

	public JcifsNgSmbDirectory(String url, CIFSContext context) {

		super(appendSlashIfMissing(url), context);
	}

	public JcifsNgSmbDirectory(SmbResource parent, String name) {

		super(parent, appendSlashIfMissing(name));
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
	public Collection<String> listFilesRecursively() {

		return listFiles("", file, new ArrayList<>());
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

		return new JcifsNgSmbFile(file, name);
	}

	@Override
	public ISmbDirectory getSubDirectory(String name) {

		return new JcifsNgSmbDirectory(file, name);
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

	@Override
	public InputStream createInputStream() {

		throw new UnsupportedOperationException("Cannot create an InputStream of a directory.");
	}

	private Collection<String> listFiles(String subDirectory, SmbFile directory, Collection<String> filenames) {

		try {
			for (SmbFile file: directory.listFiles()) {
				String path = subDirectory + file.getName();
				if (file.isDirectory()) {
					listFiles(path, file, filenames);
				} else {
					filenames.add(path);
				}
			}
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
		return filenames;
	}

	private ISmbFile wrapSmbFile(SmbFile smbFile) {

		return new JcifsNgSmbFile(smbFile.getCanonicalPath(), context);
	}

	private static String appendSlashIfMissing(String path) {

		return !path.endsWith("/")? path + '/' : path;
	}
}

package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.file.smb.jcifs.JcifsSmbFileUtils;
import com.softicar.platform.core.module.file.stored.server.AGStoredFileServer;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

public class StoredFileSmbContentStore implements IStoredFileContentStore {

	private final Optional<AGStoredFileServer> server;

	public StoredFileSmbContentStore() {

		this(AGStoredFileServer.getInstance());
	}

	public StoredFileSmbContentStore(AGStoredFileServer server) {

		this(Optional.ofNullable(server));
	}

	public StoredFileSmbContentStore(Optional<AGStoredFileServer> server) {

		this.server = server;
	}

	@Override
	public String getUrl() {

		return getServerOrThrow().getUrl();
	}

	@Override
	public boolean isAvailable() {

		try {
			return createSmbFile("/").exists();
		} catch (SmbException exception) {
			DevNull.swallow(exception);
			return false;
		}
	}

	@Override
	public boolean isEnabled() {

		return server.isPresent();
	}

	@Override
	public long getFreeDiskSpace() {

		try {
			return createSmbFile("/").getDiskFreeSpace();
		} catch (SmbException exception) {
			DevNull.swallow(exception);
			return 0;
		}
	}

	@Override
	public OutputStream createFile(String fileName) {

		SmbFile file = createSmbFile(fileName);

		return JcifsSmbFileUtils.createOutputStream(file);
	}

	@Override
	public InputStream readFile(String fileName) {

		SmbFile file = createSmbFile(fileName);

		return JcifsSmbFileUtils.createInputStream(file);
	}

	@Override
	public void moveFile(String sourceName, String targetName) {

		SmbFile source = createSmbFile(sourceName);

		SmbFile target = createSmbFile(targetName);

		try {
			source.renameTo(target);
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public void createFolderIfDoesNotExist(String folderName) {

		SmbFile folder = createSmbFile(folderName);

		try {
			if (!folder.exists()) {
				folder.mkdirs();
			}
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public void removeFile(String fileName) {

		SmbFile file = createSmbFile(fileName);

		try {
			file.delete();
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public boolean exists(String name) {

		SmbFile file = createSmbFile(name);

		try {
			return file.exists();
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public long getFileSize(String filename) {

		SmbFile file = createSmbFile(filename);

		try {
			return file.length();
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}
	}

	@Override
	public Collection<String> getAllFiles() {

		return getAllFiles("");
	}

	@Override
	public Collection<String> getAllFiles(String root) {

		return listAllFiles(createSmbFile(root), Trim.trimRight(root, '/'), new ArrayList<>());
	}

	@Override
	public DayTime getLastModified(String filename) {

		SmbFile file = createSmbFile(filename);

		try {
			return DayTime.fromDate(new Date(file.lastModified()));
		} catch (SmbException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private Collection<String> listAllFiles(SmbFile root, String prefix, Collection<String> filenames) {

		try {
			for (SmbFile file: root.listFiles()) {
				if (file.isDirectory()) {
					String subFolder = Trim.trimRight(file.getName(), '/');
					listAllFiles(file, prefix + "/" + subFolder, filenames);
				} else {
					filenames.add(prefix + "/" + file.getName());
				}
			}
		} catch (SmbException exception) {
			throw new SofticarException(exception);
		}

		return filenames;
	}

	private SmbFile createSmbFile(String name) {

		return JcifsSmbFileUtils.createFile(getServerOrThrow().getUrl() + "/" + name, getAuthentication(getServerOrThrow()));
	}

	private NtlmPasswordAuthentication getAuthentication(AGStoredFileServer fileServer) {

		return new NtlmPasswordAuthentication(//
			fileServer.getDomain(),
			fileServer.getUsername(),
			fileServer.getPassword());
	}

	private AGStoredFileServer getServerOrThrow() {

		return server.orElseThrow(() -> new SofticarDeveloperException("File server was not defined."));
	}
}

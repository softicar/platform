package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.file.smb.CurrentSmbApi;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbCredentials;
import com.softicar.platform.core.module.file.stored.server.AGStoredFileServer;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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
		} catch (Exception exception) {
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

		return createSmbFile("/").getFreeDiskSpace();
	}

	@Override
	public OutputStream createFile(String fileName) {

		return createSmbFile(fileName).createOutputStream();
	}

	@Override
	public InputStream readFile(String fileName) {

		return createSmbFile(fileName).createInputStream();
	}

	@Override
	public void moveFile(String sourceName, String targetName) {

		createSmbFile(sourceName).renameTo(targetName);
	}

	@Override
	public void createFolderIfDoesNotExist(String folderName) {

		createSmbFile(folderName).asDirectory().ifPresent(ISmbDirectory::mkdirs);
	}

	@Override
	public void removeFile(String fileName) {

		createSmbFile(fileName).delete();
	}

	@Override
	public boolean exists(String name) {

		return createSmbFile(name).exists();
	}

	@Override
	public long getFileSize(String filename) {

		return createSmbFile(filename).length();
	}

	@Override
	public Collection<String> getAllFiles() {

		return getAllFiles("");
	}

	@Override
	public Collection<String> getAllFiles(String root) {

		return createSmbFile(root).listAllFiles(Trim.trimRight(root, '/'), new ArrayList<>());
	}

	@Override
	public DayTime getLastModified(String filename) {

		return createSmbFile(filename).lastModified();
	}

	private ISmbFile createSmbFile(String name) {

		AGStoredFileServer fileServer = getServerOrThrow();
		return CurrentSmbApi
			.get()
			.createFile(
				getServerOrThrow().getUrl() + "/" + name,
				new SmbCredentials(fileServer.getDomain(), fileServer.getUsername(), fileServer.getPassword()));
	}

	private AGStoredFileServer getServerOrThrow() {

		return server.orElseThrow(() -> new SofticarDeveloperException("File server was not defined."));
	}
}

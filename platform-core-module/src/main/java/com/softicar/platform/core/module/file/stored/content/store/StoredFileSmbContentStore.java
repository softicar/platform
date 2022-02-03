package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.file.smb.CurrentSmbClient;
import com.softicar.platform.core.module.file.smb.ISmbDirectory;
import com.softicar.platform.core.module.file.smb.ISmbEntry;
import com.softicar.platform.core.module.file.smb.ISmbFile;
import com.softicar.platform.core.module.file.smb.SmbCredentials;
import com.softicar.platform.core.module.file.stored.server.AGStoredFileServer;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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
			return createSmbEntry("/").exists();
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

		return createSmbEntry("/").getFreeDiskSpace();
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

		createSmbEntry(folderName).asDirectory().ifPresent(ISmbDirectory::mkdirs);
	}

	@Override
	public void removeFile(String fileName) {

		createSmbEntry(fileName).delete();
	}

	@Override
	public boolean exists(String name) {

		return createSmbEntry(name).exists();
	}

	@Override
	public long getFileSize(String filename) {

		return createSmbFile(filename).getSize();
	}

	@Override
	public Collection<String> getAllFiles() {

		return getAllFiles("");
	}

	@Override
	public Collection<String> getAllFiles(String root) {

		return createSmbEntry(root)//
			.asDirectory()
			.orElseThrow()
			.listFilesRecursively()
			.stream()
			.map(ISmbFile::getUrl)
			.collect(Collectors.toList());
	}

	@Override
	public DayTime getLastModified(String filename) {

		return createSmbEntry(filename).getLastModifiedDate();
	}

	private ISmbEntry createSmbEntry(String name) {

		return CurrentSmbClient.get().getEntry(createSmbUrl(name), getSmbCredentials());
	}

	private ISmbFile createSmbFile(String name) {

		return createSmbEntry(name).asFileOrThrow();
	}

	private String createSmbUrl(String name) {

		return Trim.trimRight(getServerOrThrow().getUrl(), '/') + "/" + name;
	}

	private SmbCredentials getSmbCredentials() {

		AGStoredFileServer fileServer = getServerOrThrow();
		return new SmbCredentials(fileServer.getDomain(), fileServer.getUsername(), fileServer.getPassword());
	}

	private AGStoredFileServer getServerOrThrow() {

		return server.orElseThrow(() -> new SofticarDeveloperException("File server was not defined."));
	}
}

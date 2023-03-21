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
import com.softicar.platform.core.module.file.smb.SmbIOException;
import com.softicar.platform.core.module.file.stored.repository.AGStoredFileRepository;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * An implementation of {@link IStoredFileContentStore} that stores files in an
 * SMB share.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 * @author Oliver Richers
 */
public class StoredFileSmbContentStore implements IStoredFileContentStore {

	private final Optional<AGStoredFileRepository> repository;

	public StoredFileSmbContentStore() {

		this(AGStoredFileRepository.getPrimary());
	}

	public StoredFileSmbContentStore(AGStoredFileRepository repository) {

		this(Optional.ofNullable(repository));
	}

	public StoredFileSmbContentStore(Optional<AGStoredFileRepository> repository) {

		this.repository = repository;
	}

	@Override
	public String getLocation() {

		return getRepositoryOrThrow().getUrl();
	}

	@Override
	public boolean isEnabled() {

		return repository.isPresent();
	}

	@Override
	public boolean isReady() {

		try {
			return createSmbEntry("/").exists();
		} catch (Exception exception) {
			DevNull.swallow(exception);
			return false;
		}
	}

	@Override
	public long getFreeDiskSpace() {

		return createSmbEntry("/").getFreeDiskSpace();
	}

	@Override
	public void createDirectories(String directoryPath) {

		createSmbEntry(directoryPath).asDirectory().ifPresent(ISmbDirectory::makeDirectories);
	}

	@Override
	public OutputStream getFileOutputStream(String filePath) {

		return createSmbFile(filePath).createOutputStream();
	}

	@Override
	public InputStream getFileInputStream(String filePath) {

		return createSmbFile(filePath).createInputStream();
	}

	@Override
	public void moveFile(String sourceFilePath, String targetFilePath) {

		createSmbFile(sourceFilePath).moveTo(createSmbFile(targetFilePath));
	}

	@Override
	public void deleteFile(String filePath) {

		createSmbEntry(filePath).delete();
	}

	@Override
	public boolean exists(String path) {

		return createSmbEntry(path).exists();
	}

	@Override
	public long getFileSize(String filePath) {

		try {
			return createSmbFile(filePath).getSize();
		} catch (SmbIOException exception) {
			DevNull.swallow(exception);
			return 0;
		}
	}

	@Override
	public DayTime getLastModified(String path) {

		return createSmbEntry(path).getLastModifiedDate();
	}

	@Override
	public Collection<String> getAllFilePaths() {

		return getAllFilePaths("");
	}

	@Override
	public Collection<String> getAllFilePaths(String directoryPath) {

		return createSmbEntry(directoryPath)//
			.asDirectory()
			.orElseThrow()
			.listFilesRecursively()
			.stream()
			.map(ISmbFile::getUrl)
			.collect(Collectors.toList());
	}

	private ISmbEntry createSmbEntry(String name) {

		return CurrentSmbClient.get().getEntry(createSmbUrl(name), getSmbCredentials());
	}

	private ISmbFile createSmbFile(String name) {

		return createSmbEntry(name).asFileOrThrow();
	}

	private String createSmbUrl(String name) {

		return Trim.trimRight(getRepositoryOrThrow().getUrl(), '/') + "/" + Trim.trimLeft(name, '/');
	}

	private SmbCredentials getSmbCredentials() {

		AGStoredFileRepository fileRepository = getRepositoryOrThrow();
		return new SmbCredentials(fileRepository.getDomain(), fileRepository.getUsername(), fileRepository.getPassword());
	}

	private AGStoredFileRepository getRepositoryOrThrow() {

		return repository.orElseThrow(() -> new SofticarDeveloperException("File repository was not defined."));
	}
}

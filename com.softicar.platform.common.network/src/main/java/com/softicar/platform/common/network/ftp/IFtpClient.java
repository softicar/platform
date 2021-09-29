package com.softicar.platform.common.network.ftp;

import com.softicar.platform.common.io.file.IPath;
import java.io.InputStream;
import java.util.List;

public interface IFtpClient extends AutoCloseable {

	void connect();

	void disconnect();

	void reconnect();

	void validateConnection();

	void createDirectory(IPath directoryPath);

	void putFile(byte[] content, IPath filePath);

	void putFile(InputStream is, IPath filePath);

	void moveFile(IPath sourceFilePath, IPath targetFilePath);

	boolean deleteFile(IPath filePath);

	boolean isFileExists(IPath filePath);

	FtpFile getFile(IPath filePath);

	List<FtpFile> getFiles(IPath folderPath);

	byte[] getFileContent(FtpFile ftpFile, boolean reconnectIfNecessary);

	/**
	 * Closes the connection to the FTP client.
	 * <p>
	 * This method only throws {@link RuntimeException}.
	 */
	@Override
	void close();
}

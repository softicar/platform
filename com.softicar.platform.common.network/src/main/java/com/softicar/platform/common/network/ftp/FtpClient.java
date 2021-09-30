package com.softicar.platform.common.network.ftp;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.file.IPath;
import com.softicar.platform.common.io.file.Path;
import com.softicar.platform.common.string.Imploder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpClient implements IFtpClient {

	private final IFtpClientConfig ftpClientConfig;
	private FTPClient client;

	public static IFtpClient create(IFtpClientConfig ftpClientConfig, boolean connect) {

		FtpClient ftpClient = new FtpClient(ftpClientConfig);
		if (connect) {
			ftpClient.connect();
		}
		return ftpClient;
	}

	private FtpClient(IFtpClientConfig ftpClientConfig) {

		this.ftpClientConfig = ftpClientConfig;
	}

	@Override
	public void reconnect() {

		if (client == null || !client.isConnected()) {
			connect();
		}
	}

	@Override
	public void connect() {

		try {
			if (client == null) {
				client = new FTPClient();
			}
			if (!client.isConnected()) {
				applyTimeoutConfigurationValues(client, ftpClientConfig);
				client.connect(ftpClientConfig.getHostname());
				client.login(ftpClientConfig.getUsername(), ftpClientConfig.getPassword());
			}
			if (ftpClientConfig.isPassiveMode()) {
				client.enterLocalPassiveMode();
			} else {
				client.enterLocalActiveMode();
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void disconnect() {

		try {
			if (client != null) {
				client.logout();
				client.disconnect();
				client = null;
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void validateConnection() {

		if (client == null || !client.isConnected()) {
			throw new FtpClientException("Ftp client is not connected to ftp server!");
		}
	}

	@Override
	public void createDirectory(IPath directoryPath) {

		validateConnection();
		try {
			boolean success = client.makeDirectory(directoryPath.getAbsolutePath());
			if (!success) {
				String errorMessage = Imploder.implode(client.getReplyStrings(), "|");
				throw new FtpClientException("Failed to create directory '%s': %s", directoryPath.getAbsolutePath(), errorMessage);
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void putFile(byte[] content, IPath filePath) {

		validateConnection();
		try (InputStream is = new ByteArrayInputStream(content)) {
			putFile(is, filePath);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void putFile(InputStream is, IPath filePath) {

		validateConnection();
		try {
			client.setFileType(FTP.BINARY_FILE_TYPE);
			boolean success = client.storeFile(filePath.toString(), is);
			if (!success) {
				String errorMessage = Imploder.implode(client.getReplyStrings(), "|");
				throw new FtpClientException("Failed to put '%s': %s", filePath.getAbsolutePath(), errorMessage);
			}
			//
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public FtpFile getFile(IPath filePath) {

		validateConnection();
		return new FtpFile(this, filePath);
	}

	@Override
	public byte[] getFileContent(FtpFile ftpFile, boolean reconnectIfNecessary) {

		if (reconnectIfNecessary) {
			reconnect();
		}
		validateConnection();
		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			client.setFileType(FTP.BINARY_FILE_TYPE);
			boolean success = client.retrieveFile(ftpFile.getPath().getAbsolutePath(), buffer);
			if (!success) {
				String errorMessage = Imploder.implode(client.getReplyStrings(), "|");
				throw new FtpClientException("Failed to read '%s': %s", ftpFile.getPath().getAbsolutePath(), errorMessage);
			}
			return buffer.toByteArray();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public boolean isFileExists(IPath filePath) {

		validateConnection();
		try {
			try (InputStream inputStream = client.retrieveFileStream(filePath.toString())) {
				int returnCode = client.getReplyCode();
				if (inputStream == null) {
					return false;
				} else if (returnCode == 550) {
					return false;
				} else {
					return true;
				}
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public List<FtpFile> getFiles(IPath folderPath) {

		validateConnection();
		List<FtpFile> files = new ArrayList<>();
		for (FTPFile file: listFiles(folderPath)) {
			if (file.isFile()) {
				files.add(new FtpFile(this, new Path(folderPath, file.getName())));
			}
		}
		return files;
	}

	@Override
	public void moveFile(IPath sourceFilePath, IPath targetFilePath) {

		validateConnection();
		try {
			client.rename(sourceFilePath.getAbsolutePath(), targetFilePath.getAbsolutePath());
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public boolean deleteFile(IPath filePath) {

		validateConnection();
		try {
			return client.deleteFile(filePath.getAbsolutePath());
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	@Override
	public void close() {

		disconnect();
	}

	private FTPFile[] listFiles(IPath path) {

		try {
			return client.listFiles(path.getAbsolutePath());
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void applyTimeoutConfigurationValues(FTPClient client, IFtpClientConfig ftpClientConfig) {

		try {
			if (ftpClientConfig.getDefaultTimeout().isPresent()) {
				client.setDefaultTimeout(ftpClientConfig.getDefaultTimeout().get());
			}
			if (ftpClientConfig.getSoTimeout().isPresent()) {
				client.setSoTimeout(ftpClientConfig.getSoTimeout().get());
			}
			if (ftpClientConfig.getConnectTimeout().isPresent()) {
				client.setConnectTimeout(ftpClientConfig.getConnectTimeout().get());
			}
			if (ftpClientConfig.getDataTimeout().isPresent()) {
				client.setDataTimeout(ftpClientConfig.getDataTimeout().get());
			}
			if (ftpClientConfig.getControlKeepAliveTimeout().isPresent()) {
				client.setControlKeepAliveTimeout(ftpClientConfig.getControlKeepAliveTimeout().get());
			}
			if (ftpClientConfig.getControlKeepAliveReplyTimeout().isPresent()) {
				client.setControlKeepAliveReplyTimeout(ftpClientConfig.getControlKeepAliveReplyTimeout().get());
			}
		} catch (SocketException exception) {
			throw new SofticarException(exception);
		}
	}
}

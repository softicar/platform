package com.softicar.platform.common.network.ftp;

import com.softicar.platform.common.io.file.IFile;
import com.softicar.platform.common.io.file.IPath;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class FtpFile implements IFile {

	private final FtpClient ftpClient;
	private final IPath path;
	private byte[] content;

	public FtpFile(FtpClient ftpClient, IPath path) {

		this.ftpClient = ftpClient;
		this.path = path;
	}

	@Override
	public String getName() {

		return path.getName();
	}

	@Override
	public IPath getPath() {

		return path;
	}

	@Override
	public InputStream getInputStream() {

		return new ByteArrayInputStream(getContent());
	}

	public byte[] getContent() {

		if (content == null) {
			this.content = ftpClient.getFileContent(this, true);
		}
		return content;
	}

	public boolean delete() {

		return ftpClient.deleteFile(path);
	}
}

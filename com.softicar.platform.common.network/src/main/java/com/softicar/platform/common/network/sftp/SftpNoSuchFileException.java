package com.softicar.platform.common.network.sftp;

import com.jcraft.jsch.SftpException;

public class SftpNoSuchFileException extends SftpRuntimeException {

	private static final long serialVersionUID = 1L;

	public SftpNoSuchFileException(SftpException exception) {

		super(exception);
	}
}

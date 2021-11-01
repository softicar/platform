package com.softicar.platform.common.network.sftp;

import com.jcraft.jsch.SftpException;

public class SftpEndOfFileException extends SftpRuntimeException {

	private static final long serialVersionUID = 1L;

	public SftpEndOfFileException(SftpException exception) {

		super(exception);
	}
}

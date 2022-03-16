package com.softicar.platform.common.network.sftp;

import com.jcraft.jsch.SftpException;

public class SftpRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SftpRuntimeException(SftpException exception) {

		super(exception);
	}
}

package com.softicar.platform.common.network.sftp;

import com.jcraft.jsch.SftpException;

public class SftpNoConnectionException extends SftpRuntimeException {

	private static final long serialVersionUID = 1L;

	public SftpNoConnectionException(SftpException exception) {

		super(exception);
	}
}

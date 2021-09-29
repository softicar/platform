package com.softicar.platform.common.network.sftp;

import com.jcraft.jsch.SftpException;

public class SftpConnectionLostException extends SftpRuntimeException {

	private static final long serialVersionUID = 1L;

	public SftpConnectionLostException(SftpException exception) {

		super(exception);
	}
}

package com.softicar.platform.common.network.sftp;

import com.jcraft.jsch.SftpException;

public class SftpPermissionDeniedException extends SftpRuntimeException {

	private static final long serialVersionUID = 1L;

	public SftpPermissionDeniedException(SftpException exception) {

		super(exception);
	}
}

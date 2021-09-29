package com.softicar.platform.common.network.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class SftpExceptionFatory {

	public static SftpRuntimeException create(SftpException sftpException) {

		switch (sftpException.id) {
		case ChannelSftp.SSH_FX_EOF:
			return new SftpEndOfFileException(sftpException);
		case ChannelSftp.SSH_FX_NO_SUCH_FILE:
			return new SftpNoSuchFileException(sftpException);
		case ChannelSftp.SSH_FX_PERMISSION_DENIED:
			return new SftpPermissionDeniedException(sftpException);
		case ChannelSftp.SSH_FX_NO_CONNECTION:
			return new SftpNoConnectionException(sftpException);
		case ChannelSftp.SSH_FX_CONNECTION_LOST:
			return new SftpConnectionLostException(sftpException);
		default:
			return new SftpRuntimeException(sftpException);
		}
	}
}

package com.softicar.platform.common.network.sftp;

import com.jcraft.jsch.JSchException;
import com.softicar.platform.common.string.formatting.Formatting;

public class JschRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JschRuntimeException(JSchException exception) {

		super(exception);
	}

	public JschRuntimeException(JSchException exception, String message, Object...arguments) {

		super(Formatting.format(message, arguments), exception);
	}
}

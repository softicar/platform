package com.softicar.platform.common.network.ftp;

import com.softicar.platform.common.core.exceptions.SofticarException;

public class FtpClientException extends SofticarException {

	private static final long serialVersionUID = 1L;

	public FtpClientException(String format, Object...args) {

		super(format, args);
	}
}

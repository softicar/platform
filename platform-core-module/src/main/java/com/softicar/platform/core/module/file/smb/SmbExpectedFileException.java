package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarException;

public class SmbExpectedFileException extends SofticarException {

	public SmbExpectedFileException() {

		super("Expected a file.");
	}
}

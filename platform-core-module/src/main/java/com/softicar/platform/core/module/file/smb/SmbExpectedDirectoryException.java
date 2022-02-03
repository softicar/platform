package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarException;

public class SmbExpectedDirectoryException extends SofticarException {

	public SmbExpectedDirectoryException() {

		super("Expected a directory.");
	}
}

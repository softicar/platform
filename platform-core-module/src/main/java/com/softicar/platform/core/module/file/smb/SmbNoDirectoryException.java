package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarException;

public class SmbNoDirectoryException extends SofticarException {

	public SmbNoDirectoryException() {

		super("Expected a directory.");
	}
}

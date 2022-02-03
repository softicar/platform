package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarException;

public class SmbNoFileException extends SofticarException {

	public SmbNoFileException() {

		super("Expected a file.");
	}
}

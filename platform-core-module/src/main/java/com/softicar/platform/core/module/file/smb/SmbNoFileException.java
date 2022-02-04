package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarException;

/**
 * Thrown if an {@link ISmbEntry} was expected to be a file while it was not.
 *
 * @author Alexander Schmidt
 */
public class SmbNoFileException extends SofticarException {

	public SmbNoFileException() {

		super("Expected a file.");
	}
}

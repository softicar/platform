package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarException;

/**
 * Thrown if an {@link ISmbEntry} was expected to be a directory while it was
 * not.
 *
 * @author Alexander Schmidt
 */
public class SmbNoDirectoryException extends SofticarException {

	public SmbNoDirectoryException() {

		super("Expected a directory.");
	}
}

package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.io.IOException;

/**
 * Thrown when an {@link IOException} occurs while an {@link ISmbClient}
 * implementation interacts with an SMB share.
 *
 * @author Alexander Schmidt
 */
public class SmbIOException extends SofticarIOException {

	public SmbIOException(IOException ioException) {

		super(ioException, "An I/O exception occured while using an SMB share: %s", ioException);
	}

	public SmbIOException(IOException ioException, String message) {

		super(ioException, message);
	}
}

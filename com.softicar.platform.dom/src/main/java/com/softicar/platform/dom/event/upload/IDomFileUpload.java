package com.softicar.platform.dom.event.upload;

import java.io.InputStream;

/**
 * Represents a file upload.
 *
 * @author Oliver Richers
 */
public interface IDomFileUpload {

	/**
	 * The name of the file that is being uploaded.
	 * <p>
	 * This is usually the filename without path on Linux.
	 *
	 * @return the filename
	 */
	String getFilename();

	/**
	 * Returns an input stream with the file content.
	 * <p>
	 * You can call this only once.
	 *
	 * @return the file input stream
	 */
	InputStream getStream();

	/**
	 * Returns the content type of the uploaded file.
	 *
	 * @return the content type or null
	 */
	String getContentType();
}

package com.softicar.platform.common.io.classpath.file;

import java.io.InputStream;

/**
 * A proxy to the contents of a file on the class path.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IClasspathFile {

	/**
	 * Opens the file content as an {@link InputStream}.
	 * <p>
	 * The returned stream must be closed by the caller side.
	 *
	 * @return the content of the file as an {@link InputStream}
	 */
	InputStream getInputStream();

	/**
	 * Returns the file name without path.
	 *
	 * @return the name of the file (never null)
	 */
	String getName();

	/**
	 * Returns the file path relative to the class path root.
	 *
	 * @return the file path (never null)
	 */
	String getRelativePath();

	/**
	 * Returns whether this is a directory or not.
	 *
	 * @return <i>true<i> if this is a directory; <i>false</i> otherwise
	 */
	boolean isDirectory();
}

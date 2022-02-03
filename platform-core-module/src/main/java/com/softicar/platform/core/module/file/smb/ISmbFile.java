package com.softicar.platform.core.module.file.smb;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a file on an SMB share.
 *
 * @author Alexander Schmidt
 */
public interface ISmbFile extends ISmbEntry {

	/**
	 * Returns the size of this {@link ISmbFile} in bytes.
	 *
	 * @return the size of the file in bytes
	 */
	long getSize();

	/**
	 * Copies this file to the given target file.
	 *
	 * @param file
	 *            the target file (never <i>null</i>)
	 * @return the new file, after copying (never <i>null</i>)
	 */
	ISmbFile copyTo(ISmbFile file);

	/**
	 * Copies this file into the given target directory.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new file, after copying (never <i>null</i>)
	 */
	ISmbFile copyTo(ISmbDirectory directory);

	/**
	 * Moves this file into the given target directory.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new file, after moving (never <i>null</i>)
	 */
	ISmbFile moveTo(ISmbDirectory directory);

	/**
	 * Renames this file within its parent directory.
	 *
	 * @param fileName
	 *            the new name for this file (never <i>null</i>)
	 * @return the new file, after renaming (never <i>null</i>)
	 */
	ISmbFile renameTo(String fileName);

	/**
	 * Moves this file into the given target directory, and renames it.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @param fileName
	 *            the new name for this file (never <i>null</i>)
	 * @return the new file, after moving and renaming (never <i>null</i>)
	 */
	ISmbFile moveAndRenameTo(ISmbDirectory directory, String fileName);

	/**
	 * Creates this file, if necessary.
	 * <p>
	 * If this file already exists, this method will do nothing.
	 *
	 * @return this file
	 */
	ISmbFile touch();

	/**
	 * Returns an {@link InputStream} to read the contents of this file.
	 * <p>
	 * The caller is obliged to close the returned {@link InputStream} after
	 * use.
	 *
	 * @return an {@link InputStream} of this file (never <i>null</i>)
	 */
	InputStream createInputStream();

	/**
	 * Returns an {@link OutputStream} to write to this file.
	 * <p>
	 * The caller is obliged to close the returned {@link OutputStream} after
	 * use.
	 *
	 * @return an {@link OutputStream} of this file (never <i>null</i>)
	 */
	OutputStream createOutputStream();
}

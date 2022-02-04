package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
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
	 * @throws SofticarIOException
	 *             if this file does not exist
	 */
	long getSize();

	/**
	 * Copies this file to the given target file.
	 *
	 * @param file
	 *            the target file (never <i>null</i>)
	 * @return the new file, after copying (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this file does not exist
	 */
	ISmbFile copyTo(ISmbFile file);

	/**
	 * Copies this file into the given target directory.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new file, after copying (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this file does not exist
	 */
	ISmbFile copyTo(ISmbDirectory directory);

	/**
	 * Moves this file into the given target directory.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new file, after moving (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this file does not exist
	 */
	ISmbFile moveTo(ISmbDirectory directory);

	/**
	 * Moves this file to the given target file.
	 * <p>
	 * The given target file shall <b>not</b> exist. It may have a different
	 * name than this file.
	 *
	 * @param file
	 *            the target file (never <i>null</i>)
	 * @return the new file, after moving (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this file does not exist
	 */
	ISmbFile moveTo(ISmbFile file);

	/**
	 * Renames this file within its parent directory.
	 *
	 * @param fileName
	 *            the new name for this file (never <i>null</i>)
	 * @return the new file, after renaming (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this file does not exist
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
	 * @throws SofticarIOException
	 *             if this file does not exist
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
	 * @throws SofticarIOException
	 *             if this file does not exist
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

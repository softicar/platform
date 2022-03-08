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
	 * Returns the full, canonical URL of this file.
	 * <p>
	 * The returned {@link String} will not have a tailing slash, regardless of
	 * whether this file exists.
	 *
	 * @return the URL of this file (never <i>null</i>)
	 */
	@Override
	String getUrl();

	/**
	 * Returns the size of this {@link ISmbFile} in bytes.
	 *
	 * @return the size of the file in bytes
	 * @throws SmbIOException
	 *             if this file does not exist
	 */
	long getSize();

	/**
	 * Copies this file to the given target file.
	 * <p>
	 * If the target file exists, its content will be overwritten.
	 *
	 * @param file
	 *            the target file (never <i>null</i>)
	 * @return the new file, after copying (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this file does not exist
	 */
	ISmbFile copyTo(ISmbFile file);

	/**
	 * Copies this file into the given target directory.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new file, after copying (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this file does not exist
	 */
	ISmbFile copyTo(ISmbDirectory directory);

	/**
	 * Moves this file to the given target file.
	 * <p>
	 * The given target file must <b>not</b> exist. It may have a different name
	 * than this file.
	 *
	 * @param file
	 *            the target file (never <i>null</i>)
	 * @return the new file, after moving (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this file does not exist, or if the target file already
	 *             exists
	 */
	ISmbFile moveTo(ISmbFile file);

	/**
	 * Moves this file into the given target directory.
	 * <p>
	 * In the given target directory, an equally-named file must <b>not</b>
	 * exist.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new file, after moving (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this file does not exist, or if an equally-named file
	 *             already exists in the target directory
	 */
	ISmbFile moveTo(ISmbDirectory directory);

	/**
	 * Renames this file.
	 * <p>
	 * The target file must <b>not</b> yet exist.
	 * <p>
	 * The given file name may be a <b>relative</b> path to a file in another
	 * directory. In that case, the other directory must exist.
	 *
	 * @param fileName
	 *            the new name for this file (never <i>null</i>)
	 * @return the new file, after renaming (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this file does not exist, or if an equally-named file
	 *             already exists, or if the target file is in a nonexistent
	 *             directory
	 */
	ISmbFile renameTo(String fileName);

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
	 * @throws SmbIOException
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

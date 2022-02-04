package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import java.util.List;

/**
 * Represents a directory on an SMB share.
 *
 * @author Alexander Schmidt
 */
public interface ISmbDirectory extends ISmbEntry {

	/**
	 * Creates this directory and all parent directories, if necessary.
	 * <p>
	 * If this directory already exists, this method will do nothing.
	 */
	void makeDirectories();

	/**
	 * Lists all files in this directory.
	 *
	 * @return the files in this directory (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this directory does not exist
	 */
	List<ISmbFile> listFiles();

	/**
	 * Lists the files in this directory and all of its subdirectories.
	 *
	 * @return the files in this directory and its subdirectories (never
	 *         <i>null</i>)
	 * @throws SofticarIOException
	 *             if this directory does not exist
	 */
	List<ISmbFile> listFilesRecursively();

	/**
	 * Lists all subdirectories in this directory.
	 *
	 * @return the subdirectories in this directory (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this directory does not exist
	 */
	List<ISmbDirectory> listDirectories();

	/**
	 * Lists all entries (files and directories) in this directory.
	 *
	 * @return the entries in this directory (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this directory does not exist
	 */
	List<ISmbEntry> listEntries();

	/**
	 * Fetches the file with the given name from this directory.
	 * <p>
	 * The returned {@link ISmbFile} may or may not exist.
	 *
	 * @param fileName
	 *            the name of the file in this directory (never <i>null</i>)
	 * @return the referenced {@link ISmbFile} (never <i>null</i>)
	 */
	ISmbFile getFile(String fileName);

	/**
	 * Fetches the subdirectory with the given name from this directory.
	 * <p>
	 * An {@link ISmbDirectory} instance is returned even if no such
	 * subdirectory exists.
	 *
	 * @param directoryName
	 *            the name of the subdirectory in this directory (never
	 *            <i>null</i>)
	 * @return the referenced {@link ISmbDirectory} (never <i>null</i>)
	 */
	ISmbDirectory getDirectory(String directoryName);

	/**
	 * Recursively copies this directory to the given target directory.
	 * <p>
	 * Not to be confused with {@link #moveTo(ISmbDirectory)}.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new directory, after copying (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this directory does not exist
	 */
	ISmbDirectory copyTo(ISmbDirectory directory);

	/**
	 * Moves this directory <b>into</b> the given target directory.
	 * <p>
	 * Not to be confused with {@link #copyTo(ISmbDirectory)}.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new directory, after moving (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this directory does not exist
	 */
	ISmbDirectory moveTo(ISmbDirectory directory);

	/**
	 * Renames this directory within its parent directory.
	 *
	 * @param directoryName
	 *            the new name for this directory (never <i>null</i>)
	 * @return the new directory, after renaming (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this directory does not exist
	 */
	ISmbDirectory renameTo(String directoryName);

	/**
	 * Moves this directory <b>into</b> the given target directory, and renames
	 * it.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @param directoryName
	 *            the new name for this directory (never <i>null</i>)
	 * @return the new directory, after moving and renaming (never <i>null</i>)
	 * @throws SofticarIOException
	 *             if this directory does not exist
	 */
	ISmbDirectory moveAndRenameTo(ISmbDirectory directory, String directoryName);
}

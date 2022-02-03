package com.softicar.platform.core.module.file.smb;

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
	void mkdirs();

	/**
	 * Lists all files in this directory.
	 *
	 * @return the files in this directory (never <i>null</i>)
	 */
	List<ISmbFile> listFiles();

	/**
	 * Lists the files in this directory and all of its sub-directories.
	 *
	 * @return the files in this directory and its sub-directories (never
	 *         <i>null</i>)
	 */
	List<ISmbFile> listFilesRecursively();

	/**
	 * Lists all sub-directories in this directory.
	 *
	 * @return the sub-directories in this directory (never <i>null</i>)
	 */
	List<ISmbDirectory> listSubDirectories();

	/**
	 * Lists all entries (files and directories) in this directory.
	 *
	 * @return the entries in this directory (never <i>null</i>)
	 */
	List<ISmbEntry> listEntries();

	/**
	 * Fetches the file with the given name from this directory.
	 * <p>
	 * An {@link ISmbFile} instance is returned even if no such file exists.
	 *
	 * @param fileName
	 *            the name of the file in this directory (never <i>null</i>)
	 * @return the referenced {@link ISmbFile} (never <i>null</i>)
	 */
	ISmbFile getFile(String fileName);

	/**
	 * Fetches the sub-directory with the given name from this directory.
	 * <p>
	 * An {@link ISmbDirectory} instance is returned even if no such
	 * sub-directory exists.
	 *
	 * @param directoryName
	 *            the name of the sub-directory in this directory (never
	 *            <i>null</i>)
	 * @return the referenced {@link ISmbDirectory} (never <i>null</i>)
	 */
	ISmbDirectory getSubDirectory(String directoryName);

	/**
	 * Recursively copies this directory to the given target directory.
	 * <p>
	 * Note to be confused with {@link #moveTo(ISmbDirectory)}.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new directory, after copying (never <i>null</i>)
	 */
	ISmbDirectory copyTo(ISmbDirectory directory);

	/**
	 * Moves this directory <b>into</b> the given target directory.
	 * <p>
	 * Note to be confused with {@link #copyTo(ISmbDirectory)}.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new directory, after moving (never <i>null</i>)
	 */
	ISmbDirectory moveTo(ISmbDirectory directory);

	/**
	 * Renames this directory within its parent directory.
	 *
	 * @param directoryName
	 *            the new name for this directory (never <i>null</i>)
	 * @return the new directory, after renaming (never <i>null</i>)
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
	 */
	ISmbDirectory moveAndRenameTo(ISmbDirectory directory, String directoryName);
}

package com.softicar.platform.core.module.file.smb;

import java.util.List;

/**
 * Represents a directory on an SMB share.
 *
 * @author Alexander Schmidt
 */
public interface ISmbDirectory extends ISmbEntry {

	/**
	 * Returns the full, canonical URL of this directory.
	 * <p>
	 * The returned {@link String} will have a tailing slash, regardless of
	 * whether this directory exists.
	 *
	 * @return the URL of this directory (never <i>null</i>)
	 */
	@Override
	String getUrl();

	/**
	 * Creates this directory and all parent directories, if necessary.
	 * <p>
	 * If this directory already exists, or if invoked on the root of the share,
	 * this method will do nothing.
	 *
	 * @return this directory (never <i>null</i>)
	 */
	ISmbDirectory makeDirectories();

	/**
	 * Lists all files in this directory.
	 *
	 * @return the files in this directory (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this directory does not exist
	 */
	List<ISmbFile> listFiles();

	/**
	 * Lists the files in this directory and all of its subdirectories.
	 *
	 * @return the files in this directory and its subdirectories (never
	 *         <i>null</i>)
	 * @throws SmbIOException
	 *             if this directory does not exist
	 */
	List<ISmbFile> listFilesRecursively();

	/**
	 * Lists all subdirectories in this directory.
	 *
	 * @return the subdirectories in this directory (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this directory does not exist
	 */
	List<ISmbDirectory> listDirectories();

	/**
	 * Lists all entries (files and directories) in this directory.
	 *
	 * @return the entries in this directory (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this directory does not exist
	 */
	List<ISmbEntry> listEntries();

	/**
	 * Fetches the file with the given name.
	 * <p>
	 * The returned {@link ISmbFile} may or may not exist.
	 * <p>
	 * The given file name may be a <b>relative</b> path to a file in a
	 * subdirectory.
	 *
	 * @param fileName
	 *            the name of the file (never <i>null</i>)
	 * @return the referenced {@link ISmbFile} (never <i>null</i>)
	 */
	ISmbFile getFile(String fileName);

	/**
	 * Fetches the subdirectory with the given name.
	 * <p>
	 * The returned {@link ISmbDirectory} may or may not exist.
	 * <p>
	 * The given directory name may be a <b>relative</b> path to a nested
	 * subdirectory.
	 *
	 * @param directoryName
	 *            the name of the subdirectory (never <i>null</i>)
	 * @return the referenced {@link ISmbDirectory} (never <i>null</i>)
	 */
	ISmbDirectory getDirectory(String directoryName);

	/**
	 * Recursively copies the <b>content</b> of this directory <b>into</b> the
	 * given target directory.
	 * <p>
	 * The given target directory may or may not exist.
	 * <p>
	 * If any file in the target directory (or one of its subdirectories)
	 * already exists, its content will be overwritten.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the target directory (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this directory does not exist
	 */
	ISmbDirectory copyContentInto(ISmbDirectory directory);

	/**
	 * Moves this directory <b>into</b> the given target directory.
	 * <p>
	 * The given target directory must exist.
	 * <p>
	 * In the given target directory, an equally-named directory must <b>not</b>
	 * exist.
	 *
	 * @param directory
	 *            the target directory (never <i>null</i>)
	 * @return the new directory, after moving (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this directory does not exist, or if the target directory
	 *             does not exist, or if an equally-named directory already
	 *             exists in the target directory
	 */
	ISmbDirectory moveInto(ISmbDirectory directory);

	/**
	 * Renames this directory.
	 * <p>
	 * The target directory must <b>not</b> yet exist.
	 * <p>
	 * The given directory name may be an <b>absolute</b> path to a nonexistent
	 * subdirectory of an existing directory.
	 *
	 * @param directoryName
	 *            the new name for this directory (never <i>null</i>)
	 * @return the new directory, after renaming (never <i>null</i>)
	 * @throws SmbIOException
	 *             if this directory does not exist, or if an equally-named
	 *             directory already exists, or if the target directory is in a
	 *             nonexistent directory
	 */
	ISmbDirectory renameTo(String directoryName);
}

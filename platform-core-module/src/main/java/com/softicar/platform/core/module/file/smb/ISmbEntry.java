package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.date.DayTime;
import java.util.Optional;

/**
 * Represents an entry (that is, a file or a directory) on an SMB share.
 *
 * @author Alexander Schmidt
 */
public interface ISmbEntry {

	/**
	 * Returns the name of this entry, inside its parent directory.
	 * <p>
	 * The returned {@link String} will never have a tailing slash.
	 * <p>
	 * If this entry refers to the root of the share, the name of the share is
	 * returned.
	 *
	 * @return the name of this entry (never <i>null</i>)
	 */
	String getName();

	/**
	 * Returns the full, canonical URL of this entry.
	 * <p>
	 * If this entry refers to an existing directory, the returned
	 * {@link String} will have a tailing slash.
	 * <p>
	 * If this entry refers to an existing file, the returned {@link String}
	 * will not have a tailing slash.
	 * <p>
	 * If this entry does not exist, the returned {@link String} may or may not
	 * have a tailing slash.
	 *
	 * @return the URL of this entry (never <i>null</i>)
	 */
	String getUrl();

	/**
	 * Determines whether this entry exists on the share.
	 * <p>
	 * If this entry refers to the root of the share, <i>true</i> is returned.
	 *
	 * @return <i>true</i> if this entry exists on the share; <i>false</i>
	 *         otherwise
	 */
	boolean exists();

	/**
	 * Deletes this entry from the share.
	 *
	 * @throws SmbIOException
	 *             if this entry does not exist, or if invoked on the root of
	 *             the share
	 */
	void delete();

	/**
	 * Deletes this entry from the share.
	 * <p>
	 * Does nothing if this entry does not exist.
	 *
	 * @return <i>true</i> if this entry was actually deleted; <i>false</i>
	 *         otherwise
	 * @throws SmbIOException
	 *             if invoked on the root of the share
	 */
	default boolean deleteIfExists() {

		if (exists()) {
			delete();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the modification time stamp of this entry.
	 * <p>
	 * Returns {@link DayTime#get1970()} if this entry does not exist, or if
	 * this entry refers to the root of the share.
	 *
	 * @return the modification time stamp (never <i>null</i>)
	 */
	DayTime getLastModifiedDate();

	/**
	 * Returns the parent directory in which this entry resides.
	 * <p>
	 * If invoked on the root directory of a share, a directory that refers to
	 * the share itself will be returned.
	 * <p>
	 * If invoked on the share itself, a directory that refers to the host name
	 * will be returned.
	 * <p>
	 * The returned {@link ISmbDirectory} may or may not exist.
	 *
	 * @return the parent directory (never <i>null</i>)
	 */
	ISmbDirectory getParentDirectory();

	/**
	 * This method returns the free disk space in bytes of the drive this share
	 * represents or the drive on which the directory or file resides.
	 * <p>
	 * Returns 0 if this entry does not exist.
	 *
	 * @return the free disk space in bytes of the drive on which this file or
	 *         directory resides
	 */
	long getFreeDiskSpace();

	/**
	 * Determines whether this entry is a file.
	 * <p>
	 * Returns <i>false</i> if this entry does not exist.
	 *
	 * @return <i>true</i> if this entry is a file; <i>false</i> otherwise
	 */
	boolean isFile();

	/**
	 * Determines whether this entry is a directory.
	 * <p>
	 * Returns <i>false</i> if this entry does not exist.
	 *
	 * @return <i>true</i> if this entry is a directory; <i>false</i> otherwise
	 */
	boolean isDirectory();

	/**
	 * Tries to convert this entry to an {@link ISmbFile}.
	 * <p>
	 * The returned {@link ISmbFile} may or may not exist.
	 * <p>
	 * Returns {@link Optional#empty()} if this entry exists and is not a file.
	 *
	 * @return this entry as an {@link ISmbFile}
	 */
	Optional<ISmbFile> asFile();

	/**
	 * Tries to convert this entry to an {@link ISmbFile}.
	 * <p>
	 * The returned {@link ISmbFile} may or may not exist.
	 *
	 * @return this entry as an {@link ISmbFile} (never <i>null</i>)
	 * @throws SmbNoFileException
	 *             if this entry exists and is not a file
	 */
	default ISmbFile asFileOrThrow() {

		return asFile().orElseThrow(SmbNoFileException::new);
	}

	/**
	 * Tries to convert this entry to an {@link ISmbDirectory}.
	 * <p>
	 * The returned {@link ISmbDirectory} may or may not exist.
	 * <p>
	 * Returns {@link Optional#empty()} if this entry exists and is not a
	 * directory.
	 *
	 * @return this entry as an {@link ISmbDirectory}
	 */
	Optional<ISmbDirectory> asDirectory();

	/**
	 * Tries to convert this entry to an {@link ISmbDirectory}.
	 * <p>
	 * The returned {@link ISmbDirectory} may or may not exist.
	 *
	 * @return this entry as an {@link ISmbDirectory} (never <i>null</i>)
	 * @throws SmbNoFileException
	 *             if this entry exists and is not a directory
	 */
	default ISmbDirectory asDirectoryOrThrow() {

		return asDirectory().orElseThrow(SmbNoDirectoryException::new);
	}
}

package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.date.DayTime;
import java.util.Optional;

/**
 * Represents a file or directory on an SMB share.
 *
 * @author Alexander Schmidt
 */
public interface ISmbEntry {

	/**
	 * Returns the name of this entry, inside its parent directory.
	 * <p>
	 * If this entry a directory, the returned {@link String} will have a
	 * tailing slash.
	 *
	 * @return the name of this entry (never <i>null</i>)
	 */
	String getName();

	/**
	 * Returns the full, canonical URL of this entry.
	 *
	 * @return the URL of this entry (never <i>null</i>)
	 */
	String getUrl();

	/**
	 * Determines whether this entry exists on the share.
	 *
	 * @return <i>true</i> if this entry exists on the share; <i>false</i>
	 *         otherwise
	 */
	boolean exists();

	/**
	 * Deletes this entry from the share.
	 *
	 * @throws SofticarIOException
	 *             if this entry does not exist
	 */
	void delete();

	/**
	 * Deletes this entry from the share.
	 * <p>
	 * Does nothing if this entry does not exist
	 */
	default void deleteIfExists() {

		if (exists()) {
			delete();
		}
	}

	/**
	 * Returns the modification time stamp of this entry.
	 * <p>
	 * If this entry does not exist, {@link DayTime#get1970()} is returned.
	 *
	 * @return the modification time stamp (never <i>null</i>)
	 */
	DayTime getLastModifiedDate();

	/**
	 * Returns the parent directory in which this entry resides.
	 * <p>
	 * If invoked on the root directory of a share, a directory that represents
	 * the share name will be returned.
	 * <p>
	 * If invoked on the share itself, a directory that represents the host name
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
	 * Returns {@link Optional#empty()} if this entry is not a file, or if it
	 * does not exist.
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
	 * Returns {@link Optional#empty()} if this entry is not a directory, or if
	 * it does not exist.
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

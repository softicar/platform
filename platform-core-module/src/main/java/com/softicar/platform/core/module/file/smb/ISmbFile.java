package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.date.DayTime;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

/**
 * TODO add Javadoc as soon as this interface has stabilized
 */
public interface ISmbFile {

	String getName();

	String getCanonicalPath();

	boolean exists();

	boolean isDirectory();

	boolean isFile();

	/**
	 * This method returns the free disk space in bytes of the drive this share
	 * represents or the drive on which the directory or file resides.
	 *
	 * @return the free disk space in bytes of the drive on which this file or
	 *         directory resides
	 */
	long getFreeDiskSpace();

	/**
	 * Returns the size of this {@link ISmbFile} in bytes.
	 *
	 * @return the size of the file in bytes
	 */
	long getSize();

	DayTime getLastModifiedDate();

	Optional<ISmbDirectory> asDirectory();

	ISmbDirectory getParentDirectory();

	void delete();

	ISmbFile moveTo(ISmbDirectory parent);

	void copyTo(String path);

	ISmbFile renameTo(String name);

	ISmbFile moveAndRenameTo(ISmbDirectory parent, String name);

	InputStream createInputStream();

	OutputStream createOutputStream();
}

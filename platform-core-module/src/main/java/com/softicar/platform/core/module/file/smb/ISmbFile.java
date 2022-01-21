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
	 * Returns the length of this <tt>ISmbFile</tt> in bytes.
	 *
	 * @return The length of the file in bytes
	 */
	long getLength();

	DayTime getLastModifiedDate();

	Optional<ISmbDirectory> asDirectory();

	ISmbDirectory getParentDirectory();

	void delete();

	ISmbFile moveTo(ISmbDirectory parent);

	ISmbFile renameTo(String name);

	ISmbFile moveAndRenameTo(ISmbDirectory parent, String name);

	InputStream createInputStream();

	OutputStream createOutputStream();
}

package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.date.DayTime;
import java.io.InputStream;
import java.util.Collection;
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

	long getFreeDiskSpace();

	long length();

	DayTime lastModified();

	Collection<String> listAllFiles(String prefix, Collection<String> filenames);

	InputStream getInputStream();

	Optional<ISmbDirectory> asDirectory();

	ISmbDirectory getParentDirectory();

	void mkdirs();

	void delete();

	ISmbFile moveTo(ISmbDirectory parent);

	ISmbFile renameTo(String name);

	ISmbFile moveAndRenameTo(ISmbDirectory parent, String name);
}

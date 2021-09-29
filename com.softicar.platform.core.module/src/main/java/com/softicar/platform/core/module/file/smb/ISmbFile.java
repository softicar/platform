package com.softicar.platform.core.module.file.smb;

import java.io.InputStream;
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

	InputStream getInputStream();

	Optional<ISmbDirectory> asDirectory();

	ISmbDirectory getParentDirectory();

	ISmbFile moveTo(ISmbDirectory parent);

	ISmbFile renameTo(String name);

	ISmbFile moveAndRenameTo(ISmbDirectory parent, String name);
}

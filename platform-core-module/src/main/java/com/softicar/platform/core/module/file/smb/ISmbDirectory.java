package com.softicar.platform.core.module.file.smb;

import java.util.Collection;
import java.util.List;

/**
 * TODO add Javadoc as soon as this interface has stabilized
 */
public interface ISmbDirectory extends ISmbFile {

	/**
	 * Creates this directory and all parent directories, if necessary.
	 * <p>
	 * If this directory already exists, this method will do nothing.
	 */
	void mkdirs();

	List<ISmbFile> listFiles();

	Collection<String> listFilesRecursively(String subDirectory);

	ISmbFile getFile(String name);

	ISmbDirectory getSubDirectory(String name);

	@Override
	ISmbDirectory moveTo(ISmbDirectory parent);

	@Override
	ISmbDirectory renameTo(String name);

	@Override
	ISmbDirectory moveAndRenameTo(ISmbDirectory parent, String name);
}

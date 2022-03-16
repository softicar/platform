package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import java.io.File;

/**
 * Determines the source folder for a given reference class.
 * <p>
 * This class is only used internally by the {@link JavaCodePrinter}. The use of
 * this class makes only sense in a development environment like Eclipse. For
 * this reason, we cannot write a unit test for it.
 * <p>
 * This class assumes that the source code of the given reference class is
 * available in the file system. It is assumed that the name of the source
 * folder is either <i>src/main/java</i> or <i>src</i>. The former is preferred
 * when available.
 *
 * @author Oliver Richers
 */
class InternalSourceFolderFinder {

	private final Class<?> referenceClass;

	public InternalSourceFolderFinder(Class<?> referenceClass) {

		this.referenceClass = referenceClass;
	}

	public File findSourceFolder() {

		return findSourceFolder(getClasspathRootFolder());
	}

	private File getClasspathRootFolder() {

		File absoluteFolder = new File(referenceClass.getResource(".").getFile());
		File relativeFolder = new File(new JavaPackageName(referenceClass).getName("/"));

		while (relativeFolder != null && absoluteFolder.getName().equals(relativeFolder.getName())) {
			absoluteFolder = absoluteFolder.getParentFile();
			relativeFolder = relativeFolder.getParentFile();
		}

		return absoluteFolder;
	}

	private File findSourceFolder(File startFolder) {

		File sourceFolder = findFolder(startFolder, "src");

		File sourceMainJavaFolder = new File(sourceFolder, "main/java");
		if (sourceMainJavaFolder.exists() && sourceMainJavaFolder.isDirectory()) {
			return sourceMainJavaFolder;
		} else {
			return sourceFolder;
		}
	}

	private File findFolder(File startFolder, String folderName) {

		File folder = startFolder;
		while (folder != null) {
			File sourceFolder = new File(folder, folderName);
			if (sourceFolder.exists() && sourceFolder.isDirectory()) {
				return sourceFolder;
			}
			folder = folder.getParentFile();
		}
		throw new SofticarException("Failed to find '%s' sub-folder in '%s' or any of its parents.", folderName, startFolder);
	}
}

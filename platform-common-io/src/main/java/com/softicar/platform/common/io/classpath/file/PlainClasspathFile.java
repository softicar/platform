package com.softicar.platform.common.io.classpath.file;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.classpath.ClasspathFileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

/**
 * A proxy to the contents of a plain file.
 *
 * @author Alexander Schmidt
 */
public class PlainClasspathFile implements IClasspathFile {

	private final File classpath;
	private final File file;

	/**
	 * @param classPath
	 *            the class path in which the given file resides
	 * @param file
	 *            a file that resides in the given class path, or the given
	 *            class path itself
	 * @throws IllegalArgumentException
	 *             if the given file does not reside in the given class path
	 */
	public PlainClasspathFile(File classPath, File file) {

		Objects.requireNonNull(classPath);
		Objects.requireNonNull(file);
		if (!file.getPath().startsWith(classPath.getPath())) {
			throw new IllegalArgumentException("The given file does not reside in the given class path.");
		}
		this.classpath = classPath;
		this.file = file;
	}

	@Override
	public InputStream getInputStream() {

		if (!file.isDirectory()) {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException exception) {
				throw new SofticarIOException(exception);
			}
		} else {
			return null;
		}
	}

	@Override
	public String getName() {

		return file.getName();
	}

	@Override
	public String getRelativePath() {

		return classpath.toPath().relativize(file.toPath()).toString();
	}

	@Override
	public boolean isDirectory() {

		return file.isDirectory();
	}

	/**
	 * @return the {@link File} that represents the class path. never
	 *         <i>null</i>.
	 */
	public File getClasspath() {

		return classpath;
	}

	/**
	 * @return the {@link File} inside the class path. never <i>null</i>.
	 */
	public File getFile() {

		return file;
	}

	/**
	 * @return <i>true</i> if the file is a .jar archive. <i>false</i>
	 *         otherwise.
	 */
	public boolean isJarFile() {

		return getExtension().equals(".jar");
	}

	/**
	 * @return the lower case extension of the file name, with a leading dot.
	 *         <i>empty</i> if the file name has no extension.
	 */
	public String getExtension() {

		return ClasspathFileUtils.getExtension(file.getName());
	}

	/**
	 * @return the path of the file (without leading slash), inside the class
	 *         path. <i>empty</i> if file and class path are the same.
	 */
	public String getFilePathInsideClassPath() {

		String substring = file.getPath().substring(classpath.getPath().length());
		if (substring.startsWith("/")) {
			return substring.substring(1);
		} else {
			return substring;
		}
	}
}

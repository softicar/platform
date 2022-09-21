package com.softicar.platform.common.core.java.classpath;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.IAnalyzedJavaClassProvider;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import java.io.File;
import java.util.Collection;

/**
 * Represents a root entry of the {@link JavaClasspath}.
 * <p>
 * A root entry is typically a folder or a JAR file.
 *
 * @author Oliver Richers
 */
public interface IJavaClasspathRoot extends IAnalyzedJavaClassProvider {

	/**
	 * Returns the {@link File} representing this root entry.
	 *
	 * @return the {@link File} (never null)
	 */
	File getFile();

	/**
	 * Returns all {@link IClasspathFile} instances of this
	 * {@link IJavaClasspathRoot}.
	 *
	 * @return all {@link IClasspathFile} instances (never <i>null</i>)
	 */
	Collection<IClasspathFile> getAllFiles();

	/**
	 * Returns all {@link IClasspathFile} instances that are not class files.
	 *
	 * @return all resource paths (never <i>null</i>)
	 */
	Collection<IClasspathFile> getClassFiles();

	/**
	 * Returns all {@link IClasspathFile} instances that are not class files.
	 *
	 * @return all resource paths (never <i>null</i>)
	 */
	Collection<IClasspathFile> getResourceFiles();

	/**
	 * Returns all classes provided by this {@link IJavaClasspathRoot}.
	 *
	 * @return the {@link AnalyzedJavaClass} objects (never null)
	 */
	Collection<AnalyzedJavaClass> getAnalyzedClasses();

	/**
	 * Tests whether this {@link IJavaClasspathRoot} is a folder.
	 *
	 * @return <i>true</i> if this is a folder; <i>false</i> otherwise
	 */
	boolean isFolder();

	/**
	 * Tests whether this {@link IJavaClasspathRoot} is a JAR file.
	 *
	 * @return <i>true</i> if this is a JAR file; <i>false</i> otherwise
	 */
	boolean isJar();
}

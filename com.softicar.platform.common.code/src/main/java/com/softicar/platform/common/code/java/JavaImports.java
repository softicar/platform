package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class manages imports for a Java class file.
 *
 * @author Oliver Richers
 */
public class JavaImports implements Iterable<JavaClassName> {

	private final static JavaPackageName JAVA_LANGUAGE_PACKAGE = new JavaPackageName("java.lang");
	private final Set<JavaClassName> imports = new TreeSet<>();

	/**
	 * Returns true if the set of imports is empty.
	 *
	 * @return true no imports exist
	 */
	public boolean isEmpty() {

		return imports.isEmpty();
	}

	@Override
	public Iterator<JavaClassName> iterator() {

		return getImports().iterator();
	}

	/**
	 * Returns all imports as a set of strings.
	 *
	 * @return all imports
	 */
	public Set<JavaClassName> getImports() {

		return Collections.unmodifiableSet(imports);
	}

	public void addImport(JavaClass javaClass) {

		addImport(javaClass.getName());
		addImports(javaClass.getImports());
	}

	/**
	 * Adds an import for the given class.
	 * <p>
	 * Imports for primitive types and array types will be ignored.
	 *
	 * @param javaClass
	 *            the class to import
	 */
	public void addImport(Class<?> javaClass) {

		// ignore classes than cannot be imported
		if (javaClass.isPrimitive() || javaClass.isArray()) {
			return;
		}

		addImport(new JavaClassName(javaClass));
	}

	/**
	 * Adds the given imports using the {@link #addImport(Class)} method.
	 *
	 * @param classImports
	 *            iterable of class imports
	 */
	public void addImports(Collection<Class<?>> classImports) {

		for (Class<?> classImport: classImports) {
			addImport(classImport);
		}
	}

	/**
	 * Adds an import for the given class.
	 * <p>
	 * Imports for arrays and imports from the package <i>java.lang</i> will be
	 * ignored.
	 *
	 * @param className
	 *            the class name to all
	 */
	public void addImport(JavaClassName className) {

		// ignore imports for arrays
		String simpleName = className.getSimpleName();
		if (simpleName.startsWith("[") || simpleName.endsWith("[]")) {
			return;
		}

		// ignore imports for default package
		JavaPackageName packageName = className.getPackageName();
		if (packageName == null) {
			return;
		}

		// ignore imports for Java language package
		if (packageName.equals(JAVA_LANGUAGE_PACKAGE)) {
			return;
		}

		doAddImport(className);
	}

	/**
	 * Adds the given imports using the {@link #addImport(JavaClassName)}
	 * method.
	 *
	 * @param classNames
	 *            iterable of class names
	 */
	public void addImports(Iterable<JavaClassName> classNames) {

		for (JavaClassName className: classNames) {
			addImport(className);
		}
	}

	/**
	 * Adds the given imports using the {@link #addImport(JavaClassName)}
	 * method.
	 * <p>
	 * The second set specifies a set of simple class names, for filtering the
	 * class imports. Only those class imports will be added, which are
	 * referencing a class in the second set.
	 *
	 * @param classNames
	 *            set of class names
	 * @param simpleNames
	 *            a set of simple class names used for filtering the imports
	 */
	public void addImportsFor(Set<JavaClassName> classNames, Set<String> simpleNames) {

		for (JavaClassName className: classNames) {
			String simpleName = className.getSimpleName();
			if (simpleNames.contains(simpleName)) {
				addImport(className);
			}
		}
	}

	/**
	 * This method can be overridden to add filtering or mapping of imports.
	 *
	 * @param className
	 *            the {@link JavaClassName} to all (never <i>null</i>)
	 */
	protected void doAddImport(JavaClassName className) {

		imports.add(className);
	}
}

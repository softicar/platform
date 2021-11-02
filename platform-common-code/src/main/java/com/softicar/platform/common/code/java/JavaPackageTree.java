package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;

/**
 * A Java package tree.
 * <p>
 * A Java package tree is essentially defined by the root package of the tree.
 * All {@link JavaPackageName} objects, starting with the same root package are
 * considered to be a part of the package tree.
 *
 * @author Oliver Richers
 */
public class JavaPackageTree implements Comparable<JavaPackageTree> {

	private final JavaPackageName rootPackage;

	/**
	 * Constructs a new package tree from the given root package.
	 *
	 * @param rootPackage
	 *            the qualified name of the root package (never null)
	 */
	public JavaPackageTree(String rootPackage) {

		this(new JavaPackageName(rootPackage));
	}

	/**
	 * Constructs a new package tree from the given root package.
	 *
	 * @param rootPackage
	 *            the {@link JavaPackageName} of the root package (never null)
	 */
	public JavaPackageTree(JavaPackageName rootPackage) {

		this.rootPackage = rootPackage;
	}

	/**
	 * Tests whether the given {@link JavaClassName} is contained in this
	 * {@link JavaPackageTree}.
	 *
	 * @param className
	 *            the {@link JavaClassName} to test (never null)
	 * @return <i>true</i> if the name is contained; <i>false</i> otherwise
	 */
	public boolean contains(JavaClassName className) {

		return contains(className.getPackageName());
	}

	/**
	 * Tests whether the given {@link JavaPackageName} is contained in this
	 * {@link JavaPackageTree}.
	 *
	 * @param packageName
	 *            the JavaPackageName to test (never null)
	 * @return <i>true</i> if the name is contained; <i>false</i> otherwise
	 */
	public boolean contains(JavaPackageName packageName) {

		return packageName.equals(rootPackage) || packageName.isChildOf(rootPackage);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof JavaPackageTree) {
			return rootPackage.equals(((JavaPackageTree) object).rootPackage);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return rootPackage.hashCode();
	}

	@Override
	public String toString() {

		return rootPackage.toString();
	}

	@Override
	public int compareTo(JavaPackageTree other) {

		return rootPackage.compareTo(other.rootPackage);
	}
}

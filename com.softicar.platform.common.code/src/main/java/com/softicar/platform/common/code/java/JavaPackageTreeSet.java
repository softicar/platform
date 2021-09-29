package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import java.util.Set;
import java.util.TreeSet;

/**
 * A set of {@link JavaPackageTree} elements.
 * <p>
 * Various {@link JavaPackageTree} elements can be added to easily check if a
 * {@link JavaPackageName} is contained in one of them.
 *
 * @author Oliver Richers
 */
public class JavaPackageTreeSet {

	private final Set<JavaPackageTree> packageTrees;

	/**
	 * Constructs and empty set of {@link JavaPackageTree} elements.
	 */
	public JavaPackageTreeSet() {

		this.packageTrees = new TreeSet<>();
	}

	/**
	 * Adds the given {@link JavaPackageTree} to this set.
	 *
	 * @param packageTree
	 *            the {@link JavaPackageTree} to add (never null)
	 */
	public void add(JavaPackageTree packageTree) {

		packageTrees.add(packageTree);
	}

	/**
	 * Adds the given {@link JavaPackageTreeSet} to this set.
	 *
	 * @param packageTreeSet
	 *            the {@link JavaPackageTreeSet} to add (never null)
	 */
	public void addAll(JavaPackageTreeSet packageTreeSet) {

		packageTrees.addAll(packageTreeSet.packageTrees);
	}

	/**
	 * Returns a {@link Set} of all contained {@link JavaPackageTree} elements.
	 *
	 * @return all {@link JavaPackageTree} elements (never null)
	 */
	public Set<JavaPackageTree> getPackageTrees() {

		return packageTrees;
	}

	/**
	 * Tests whether the given {@link JavaClassName} is contained in this
	 * {@link JavaPackageTreeSet}.
	 *
	 * @param className
	 *            the {@link JavaClassName} to test (never null)
	 * @return <i>true</i> if the name is contained; <i>false</i> otherwise
	 */
	public boolean contains(JavaClassName className) {

		return packageTrees//
			.stream()
			.anyMatch(tree -> tree.contains(className));
	}

	/**
	 * Tests whether the given {@link JavaPackageName} is contained in this
	 * {@link JavaPackageTreeSet}.
	 *
	 * @param packageName
	 *            the {@link JavaPackageName} to test (never null)
	 * @return <i>true</i> if the name is contained; <i>false</i> otherwise
	 */
	public boolean contains(JavaPackageName packageName) {

		return packageTrees//
			.stream()
			.anyMatch(tree -> tree.contains(packageName));
	}
}

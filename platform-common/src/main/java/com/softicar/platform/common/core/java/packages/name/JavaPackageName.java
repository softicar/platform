package com.softicar.platform.common.core.java.packages.name;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the qualified name of a Java package.
 *
 * @author Oliver Richers
 */
public class JavaPackageName implements Comparable<JavaPackageName> {

	private static final JavaPackageName EMPTY = new JavaPackageName(Collections.emptyList());
	private final List<String> elements;

	/**
	 * Creates a new {@link JavaPackageName} from the given {@link Package}.
	 *
	 * @param thePackage
	 *            the Java package (never null)
	 */
	public JavaPackageName(Package thePackage) {

		this(thePackage.getName());
	}

	/**
	 * Creates a new {@link JavaPackageName} from the {@link Package} of the
	 * given {@link Class}.
	 *
	 * @param theClass
	 *            the class (never null)
	 */
	public JavaPackageName(Class<?> theClass) {

		this(theClass.getPackage());
	}

	/**
	 * Creates a new {@link JavaPackageName} from the given fully qualified
	 * package name.
	 *
	 * @param qualifiedName
	 *            the fully qualified package name (never null)
	 */
	public JavaPackageName(String qualifiedName) {

		this(Arrays.asList(qualifiedName.split("\\.")));
	}

	/**
	 * Creates a new {@link JavaPackageName} of a child package.
	 *
	 * @param parent
	 *            the {@link JavaPackageName} of the parent package (never null)
	 * @param childName
	 *            the simple name of the child package (never null)
	 */
	public JavaPackageName(JavaPackageName parent, String childName) {

		this(concat(parent.elements, childName));
	}

	/**
	 * Creates a new {@link JavaPackageName} from the name elements.
	 *
	 * @param elements
	 *            the package name elements (never null)
	 */
	public JavaPackageName(Collection<String> elements) {

		this.elements = new ArrayList<>(elements);
	}

	/**
	 * Returns the fully qualified name of this {@link JavaPackageName}.
	 * <p>
	 * The package name elements are separated by a dot (.).
	 *
	 * @return the fully qualified name (never null)
	 */
	public String getName() {

		return getName(".");
	}

	/**
	 * Returns the fully qualified name of this {@link JavaPackageName}.
	 * <p>
	 * The package name elements are separated by the given separator.
	 *
	 * @param separator
	 *            the separator to use
	 * @return the fully qualified name (never null)
	 */
	public String getName(String separator) {

		return elements.stream().collect(Collectors.joining(separator));
	}

	/**
	 * Returns the simple name of this {@link JavaPackageName}.
	 * <p>
	 * The simple name of a {@link JavaPackageName} is the last element of the
	 * fully qualified package name.
	 *
	 * @return the simple name (never null)
	 */
	public String getSimpleName() {

		return elements.get(elements.size() - 1);
	}

	/**
	 * Returns the {@link JavaPackageName} of the direct parent package.
	 * <p>
	 * If this is {@link JavaPackageName} has only one name element or less then
	 * {@link #getEmpty()} is returned.
	 *
	 * @return the parent {@link JavaPackageName} (never null)
	 */
	public JavaPackageName getParent() {

		if (elements.size() > 1) {
			return new JavaPackageName(elements.subList(0, elements.size() - 1));
		} else {
			return getEmpty();
		}
	}

	/**
	 * Returns the empty {@link JavaPackageName}
	 *
	 * @return the empty name (never null)
	 */
	public static JavaPackageName getEmpty() {

		return EMPTY;
	}

	/**
	 * Tests whether this {@link JavaPackageName} is a child package of the
	 * given {@link JavaPackageName}.
	 * <p>
	 * This does not only return <i>true</i> for direct children, but also
	 * indirect children.
	 *
	 * @param parentPackageName
	 *            the parent {@link JavaPackageName} (never null)
	 * @return <i>true</i> if this a direct or indirect child package;
	 *         <i>false</i> otherwise
	 */
	public boolean isChildOf(JavaPackageName parentPackageName) {

		int parentLength = parentPackageName.elements.size();
		return parentLength < elements.size() && parentPackageName.equals(new JavaPackageName(elements.subList(0, parentLength)));
	}

	/**
	 * Tests whether this {@link JavaPackageName} is a parent package of the
	 * given {@link JavaPackageName}.
	 * <p>
	 * This does not only return <i>true</i> for direct children, but also
	 * indirect children.
	 *
	 * @param childPackageName
	 *            the child {@link JavaPackageName} (never null)
	 * @return <i>true</i> if this a direct or indirect parent package;
	 *         <i>false</i> otherwise
	 */
	public boolean isParentOf(JavaPackageName childPackageName) {

		return childPackageName.isChildOf(this);
	}

	/**
	 * Tests whether this {@link JavaPackageName} is empty or not.
	 *
	 * @return <i>true</i> if this {@link JavaPackageName} is empty;
	 *         <i>false</i> otherwise
	 */
	public boolean isEmpty() {

		return elements.isEmpty();
	}

	/**
	 * Creates a new {@link JavaClassName} from this {@link JavaPackageName}.
	 *
	 * @param className
	 *            the simple class name (never null)
	 * @return the new {@link JavaClassName} (never null)
	 * @see JavaClassName#JavaClassName(JavaPackageName, String)
	 */
	public JavaClassName getClassName(String className) {

		return new JavaClassName(this, className);
	}

	@Override
	public int hashCode() {

		return getName().hashCode();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof JavaPackageName) {
			return getName().equals(((JavaPackageName) object).getName());
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(JavaPackageName other) {

		return getName().compareTo(other.getName());
	}

	@Override
	public String toString() {

		return getName();
	}

	private static List<String> concat(List<String> elements, String childName) {

		List<String> result = new ArrayList<>(elements);
		result.add(childName);
		return result;
	}
}

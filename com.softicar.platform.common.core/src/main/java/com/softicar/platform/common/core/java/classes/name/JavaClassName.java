package com.softicar.platform.common.core.java.classes.name;

import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Represents a Java class name.
 *
 * @author Oliver Richers
 */
public class JavaClassName implements Comparable<JavaClassName> {

	private final String name;
	private final String canonicalName;
	private final String simpleName;
	private final JavaPackageName packageName;

	/**
	 * Constructs this {@link JavaClassName} from the given {@link String}.
	 * <p>
	 * The dot (.) as well as the slash (/) may be used as package separator.
	 * Only the dollar ($) is allowed as inner class separator, otherwise inner
	 * class names cannot be determined correctly, that is, the outer class
	 * names would be treated as package names.
	 *
	 * @param name
	 *            the name to parse (never null)
	 */
	public JavaClassName(String name) {

		this.name = name.replace('/', '.');
		this.simpleName = determineSimpleName(this.name);
		this.canonicalName = this.name.replace('$', '.');
		this.packageName = new JavaPackageName(determinePackageName(this.name));
	}

	/**
	 * Constructs this {@link JavaClassName} from the given {@link Class}.
	 *
	 * @param theClass
	 *            the {@link Class} to retrieve the name from (never null)
	 */
	public JavaClassName(Class<?> theClass) {

		this.name = theClass.getName();
		this.simpleName = theClass.getSimpleName();
		this.canonicalName = theClass.getCanonicalName();
		this.packageName = Optional//
			.ofNullable(theClass.getPackage())
			.map(JavaPackageName::new)
			.orElse(JavaPackageName.getEmpty());
	}

	/**
	 * Constructs this {@link JavaClassName} from the given
	 * {@link JavaPackageName} and class name.
	 * <p>
	 * To create {@link JavaClassName} for inner classes, the dollar sign ($)
	 * should be used as separator in the class name.
	 *
	 * @param packageName
	 *            the {@link JavaPackageName} (never null)
	 * @param className
	 *            the unqualified class name (never null)
	 */
	public JavaClassName(JavaPackageName packageName, String className) {

		this(packageName.getName() + '.' + className);
	}

	/**
	 * Returns the class name as {@link String}.
	 * <p>
	 * This returns the name as {@link Class#getName()} would do, that is, the
	 * dot (.) is used as package separator and the ($) is used as inner class
	 * separator.
	 *
	 * @return the class name (never null)
	 */
	public String getName() {

		return name;
	}

	/**
	 * Returns the simple class name.
	 * <p>
	 * This returns the simple name as {@link Class#getSimpleName()} would do,
	 * that is, for inner classes, the simple name is the name of the inner
	 * class, excluding the name of the outer class.
	 *
	 * @return the simple class name (never null)
	 */
	public String getSimpleName() {

		return simpleName;
	}

	/**
	 * Returns the canonical class name.
	 * <p>
	 * This returns the canonical name as {@link Class#getCanonicalName()} would
	 * do, that is, only the dot (.) is used as separator for package names,
	 * class name and inner class names.
	 *
	 * @return the canonical class name (never null)
	 */
	public String getCanonicalName() {

		return canonicalName;
	}

	/**
	 * Returns the canonical class name.
	 * <p>
	 * This returns the canonical name as {@link Class#getCanonicalName()} would
	 * do, that is, only the given separator is used to separate package names,
	 * class name and inner class names.
	 *
	 * @param separator
	 *            the name separator to use (never null)
	 * @return the canonical class name (never null)
	 */
	public String getCanonicalName(String separator) {

		return canonicalName.replace(".", separator);
	}

	/**
	 * Returns the {@link JavaPackageName} of this {@link JavaClassName}.
	 *
	 * @return the package name (never null)
	 */
	public JavaPackageName getPackageName() {

		return packageName;
	}

	/**
	 * Tests whether this {@link JavaClassName} represents an inner class or
	 * not.
	 *
	 * @return <i>true</i> if this represents an inner class; <i>false</i>
	 *         otherwise
	 */
	public boolean isInnerClass() {

		return name.contains("$");
	}

	/**
	 * Tests whether this {@link JavaClassName} represents an array or not.
	 *
	 * @return <i>true</i> if this represents an array; <i>false</i> otherwise
	 */
	public boolean isArray() {

		return name.startsWith("[");
	}

	@Override
	public int hashCode() {

		return getName().hashCode();
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof JavaClassName) {
			return getName().equals(((JavaClassName) object).getName());
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(JavaClassName other) {

		return getName().compareTo(other.getName());
	}

	@Override
	public String toString() {

		return getName();
	}

	private static String determineSimpleName(String name) {

		List<String> elements = Arrays.asList(name.replace('.', '/').replace('$', '/').split("/"));
		return elements.get(elements.size() - 1);
	}

	private static List<String> determinePackageName(String name) {

		List<String> elements = Arrays.asList(name.replace('.', '/').split("/"));
		return elements.subList(0, elements.size() - 1);
	}
}

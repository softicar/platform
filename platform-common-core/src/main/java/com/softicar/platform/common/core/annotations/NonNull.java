package com.softicar.platform.common.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation defines a Java reference to be non-null.
 * <p>
 * Valid references that can be marked as non-null include method return values,
 * method parameters and local variables. Such an annotation has two
 * consequences:
 * <ul>
 * <li>An attempt to bind a <code>null</code> value to the reference is a
 * compile time error.</li>
 * <li>Dereferencing the reference is safe, i.e., no
 * <code>NullPointerException</code> can occur at runtime.</li>
 * </ul>
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE_USE)
public @interface NonNull {
	// marker annotation with no members
}

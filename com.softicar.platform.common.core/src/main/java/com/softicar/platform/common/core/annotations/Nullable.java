package com.softicar.platform.common.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation defines a Java reference to be null-able.
 * <p>
 * Valid references that can be marked as null-able include method return
 * values, method parameters and local variables. Such an annotation has two
 * consequences:
 * <ul>
 * <li>Binding a <code>null</code> value to the reference is legal.</li>
 * <li>Dereferencing the reference is unsafe, i.e., a
 * <code>NullPointerException</code> can occur at runtime.</li>
 * </ul>
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE })
public @interface Nullable {
	// marker annotation with no members
}


package com.softicar.platform.common.core.java.code.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for Java code validator classes.
 * <p>
 * Classes annotated with this annotation, must implement the interface
 * {@link IJavaCodeValidator}.
 *
 * @author Oliver Richers
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JavaCodeValidator {

	// nothing to add
}

package com.softicar.platform.common.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Designates a {@link Class} as being exclusively used for unit tests.
 *
 * @author Oliver Richers
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestingOnly {

	// nothing to add
}

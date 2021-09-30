package com.softicar.platform.common.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be applied to a package, type, method or constructor in
 * order to define that contained entities for which a null annotation is
 * otherwise lacking should be considered as {@link NonNull}. Entities affected
 * by <code>@NonNullByDefault</code> are:
 * <ul>
 * <li>method return values</li>
 * <li>parameters of a method or constructor.</li>
 * </ul>
 * Local variables are <em>not</em> affected.
 * <dl>
 * <dt>Canceling a default</dt>
 * <dd>By using a <code>@NonNullByDefault</code> annotation with the argument
 * <code>false</code>, a default from any enclosing scope can be canceled for
 * the element being annotated.
 * <dt>Nested defaults</dt>
 * <dd>If a <code>@NonNullByDefault</code> annotation is used within the scope
 * of another <code>@NonNullByDefault</code> annotation, the innermost
 * annotation defines the default applicable at any given position (depending on
 * the parameter {@link #value()}).</dd>
 * </dl>
 * Note that for applying an annotation to a package, a file by the name
 * <code>package-info.java</code> is used.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE })
public @interface NonNullByDefault {

	/**
	 * When parameterized with <code>false</code>, the annotation specifies that
	 * the current element should not apply any default to un-annotated types.
	 */
	boolean value() default true;
}

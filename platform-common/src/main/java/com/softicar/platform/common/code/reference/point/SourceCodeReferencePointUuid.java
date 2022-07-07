package com.softicar.platform.common.code.reference.point;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

/**
 * This annotation is used to define {@link UUID}s for classes implementing
 * {@link ISourceCodeReferencePoint}.
 * <p>
 * Classes with this annotation have these restrictions:
 * <ul>
 * <li>must not be abstract</li>
 * <li>must not be interfaces</li>
 * <li>must implement the interface {@link ISourceCodeReferencePoint}</li>
 * <li>must not define constructors (including all base classes)</li>
 * <li>must not define non-static fields (including all base classes)</li>
 * </ul>
 *
 * @author Oliver Richers
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SourceCodeReferencePointUuid {

	String value();
}

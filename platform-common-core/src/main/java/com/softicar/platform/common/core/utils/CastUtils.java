package com.softicar.platform.common.core.utils;

import java.util.Optional;

/**
 * This class contains static utility methods for type casts.
 *
 * @author Oliver Richers
 */
public class CastUtils {

	/**
	 * Casts an object to another type.
	 * <p>
	 * The target type is determined through type inference.
	 * <p>
	 * The purpose of this method is to centralize the warnings caused by
	 * unchecked but necessary generic type casts. Use this method only if you
	 * know what you are doing!
	 *
	 * @param object
	 *            the object to cast
	 * @return the same object casted to the target type
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object object) {

		return (T) object;
	}

	/**
	 * Attempts to cast the given object into an instance of the given class.
	 *
	 * @param <T>
	 *            the target type
	 * @param object
	 *            the object to cast (may be <i>null</i>)
	 * @param targetClass
	 *            the target class to cast the object to (never <i>null</i>)
	 * @return an {@link Optional} of the casted object
	 */
	public static <T> Optional<T> tryCast(Object object, Class<T> targetClass) {

		return Optional.ofNullable(object).filter(targetClass::isInstance).map(targetClass::cast);
	}

	/**
	 * Attempts to cast the given object into an instance of the given class.
	 *
	 * @param <T>
	 *            the target type
	 * @param object
	 *            the object to cast (may be <i>null</i>)
	 * @param targetClass
	 *            the target class to cast the object to (never <i>null</i>)
	 * @return the casted object or <i>null</i>
	 */
	public static <T> T castOrNull(Object object, Class<T> targetClass) {

		return tryCast(object, targetClass).orElse(null);
	}
}

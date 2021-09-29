package com.softicar.platform.common.core.language;

/**
 * Improved interface for class loaders.
 * <p>
 * This interface decouples the user of a class loader from the specific
 * implementation of the class loading algorithm. The interface is also an
 * improvement of the standard {@link ClassLoader}, because it will not throw
 * any checked exceptions. Instead, if no matching class can be found,
 * implementations of this interface either throw a runtime exception or return
 * a default class.
 * 
 * @author Oliver Richers
 */
public interface IClassLoader {

	/**
	 * Loads the class with the specified class name.
	 * <p>
	 * If no matching class can be found, an exception will be thrown.
	 * 
	 * @param className
	 *            the name of the class to load
	 * @return the loaded class, never null
	 * @throws ClassNotFoundRuntimeException
	 *             if no matching class could be found
	 */
	Class<?> loadClass(String className);

	/**
	 * Tries to load the class with the specified name.
	 * <p>
	 * In contrast to {@link #loadClass(String)}, this method will not throw an
	 * exception if no matching class could be found, but will return a default
	 * class instead.
	 * 
	 * @param className
	 *            the name of the class to load
	 * @param defaultClass
	 *            the class to return if no class with the specified name could
	 *            be found
	 * @return the loaded class, or the specified default class
	 */
	Class<?> loadClass(String className, Class<?> defaultClass);
}

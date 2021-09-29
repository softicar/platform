package com.softicar.platform.common.core.language;

import com.softicar.platform.common.core.utils.DevNull;

/**
 * This is the most basic implementation of {@link IClassLoader}.
 *
 * @author Oliver Richers
 */
public class SimpleClassLoader implements IClassLoader {

	private final ClassLoader classLoader;

	/**
	 * Constructs an instance of this class.
	 * <p>
	 * The class loader of this class will be used internally for class loading.
	 */
	public SimpleClassLoader() {

		this(SimpleClassLoader.class.getClassLoader());
	}

	/**
	 * Constructs an instance of this class.
	 * <p>
	 * The specified class loader will be used internally for class loading.
	 *
	 * @param classLoader
	 *            the actual class loader to use
	 */
	public SimpleClassLoader(ClassLoader classLoader) {

		this.classLoader = classLoader;
	}

	@Override
	public Class<?> loadClass(String className) {

		try {
			return classLoader.loadClass(className);
		} catch (ClassNotFoundException exception) {
			throw new ClassNotFoundRuntimeException(exception);
		}
	}

	@Override
	public Class<?> loadClass(String className, Class<?> defaultClass) {

		try {
			return classLoader.loadClass(className);
		} catch (ClassNotFoundException exception) {
			DevNull.swallow(exception);
			return defaultClass;
		}
	}
}

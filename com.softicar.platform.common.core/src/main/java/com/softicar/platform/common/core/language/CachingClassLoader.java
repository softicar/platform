package com.softicar.platform.common.core.language;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.Map;
import java.util.TreeMap;

/**
 * This is a caching implementation of {@link IClassLoader}.
 * <p>
 * Loading the same class multiple times via the standard {@link ClassLoader} is
 * not very efficient, because it is calling a native method (Jdk 7). Using a
 * class loader cache can greatly improve the performance, according to
 * empirical measurements.
 *
 * @author Oliver Richers
 */
public class CachingClassLoader extends SimpleClassLoader {

	private final Map<String, Class<?>> classMap = new TreeMap<>();

	public CachingClassLoader() {

		super();
	}

	public CachingClassLoader(ClassLoader classLoader) {

		super(classLoader);
	}

	/**
	 * Loads the class with the specified class name.
	 * <p>
	 * This class first checks an internal cache if the corresponding class has
	 * already been loaded. Only then, if no such class can be found in the
	 * cache, the actual class loader is called to load the class.
	 *
	 * @param className
	 *            the name of the class to load
	 * @return the loaded class, never null
	 * @throws ClassNotFoundRuntimeException
	 *             if no matching class could be found
	 */
	@Override
	public Class<?> loadClass(String className) {

		// check class map
		Class<?> loadedClass = classMap.get(className);
		if (loadedClass != null) {
			return loadedClass;
		}

		// load class via class loader and put into map
		loadedClass = super.loadClass(className);
		classMap.put(className, loadedClass);

		return loadedClass;
	}

	@Override
	public Class<?> loadClass(String className, Class<?> defaultClass) {

		try {
			return this.loadClass(className);
		} catch (ClassNotFoundRuntimeException exception) {
			DevNull.swallow(exception);
			return defaultClass;
		}
	}
}

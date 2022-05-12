package com.softicar.platform.common.servlet.hot;

import com.softicar.platform.common.core.java.classpath.finder.JavaClasspathFinder;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class loader is a simple replacement of the system class loader.
 *
 * @author Oliver Richers
 */
class HotHttpServiceClassLoader extends URLClassLoader {

	private final Map<String, Class<?>> loadedClasses = new TreeMap<>();
	private final Set<String> ignoredPrefixes = new TreeSet<>();
	private final Set<String> ignoredClassNames = new TreeSet<>();

	public HotHttpServiceClassLoader() {

		super(new URL[] {});

		addIgnoredPrefix("java.");
		addIgnoredPrefix("javax.");

		new JavaClasspathFinder().findAll().forEach(this::addPath);
	}

	public void addIgnoredPrefix(String prefix) {

		ignoredPrefixes.add(prefix);
	}

	public void addIgnoredClass(Class<?> ignoredClass) {

		ignoredClassNames.add(ignoredClass.getCanonicalName());
	}

	private void addPath(File path) {

		try {
			addURL(new URL("file", "", getAbsolutePath(path)));
		} catch (MalformedURLException exception) {
			throw new RuntimeException(exception);
		}
	}

	private String getAbsolutePath(File path) {

		String absolutePath = path.getAbsolutePath();
		if (path.isDirectory() && !absolutePath.endsWith("/")) {
			absolutePath = absolutePath + "/";
		}
		return absolutePath;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {

		synchronized (getClassLoadingLock(name)) {
			Class<?> loadedClass = loadedClasses.get(name);
			if (loadedClass == null) {
				if (isSkipped(name)) {
					Log.fverbose("SKIP: %s", name);
					loadedClass = super.loadClass(name);
				} else {
					try {
						loadedClass = findClass(name);
						Log.fverbose("LOAD: %s", name);
					} catch (ClassNotFoundException exception) {
						DevNull.swallow(exception);
						loadedClass = getParent().loadClass(name);
						Log.fverbose("NOT FOUND: %s", name);
					}
				}
				loadedClasses.put(name, loadedClass);
			} else {
				Log.fverbose("RELOAD: %s", name);
			}
			return loadedClass;
		}
	}

	private boolean isSkipped(String name) {

		for (String ignoredPrefix: ignoredPrefixes) {
			if (name.startsWith(ignoredPrefix)) {
				return true;
			}
		}
		if (ignoredClassNames.contains(name)) {
			return true;
		}
		return false;
	}
}

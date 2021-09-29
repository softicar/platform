package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.singleton.Singleton;
import java.util.Set;
import java.util.TreeSet;

public class IgnoredPackagesForLogging {

	private static final Singleton<IgnoredPackagesForLogging> PACKAGES = new Singleton<>(IgnoredPackagesForLogging::new);
	private final Set<String> packagePrefixes = new TreeSet<>();

	private IgnoredPackagesForLogging() {

		packagePrefixes.add("com.softicar.platform.");
		packagePrefixes.add("java.");
		packagePrefixes.add("javax.");
		packagePrefixes.add("org.junit.");
		packagePrefixes.add("sun.");
	}

	public static IgnoredPackagesForLogging get() {

		return PACKAGES.get();
	}

	public void addPackagePrefix(String packagePrefix) {

		packagePrefixes.add(packagePrefix);
	}

	public void clear() {

		packagePrefixes.clear();
	}

	public boolean isIgnored(String canonicalName) {

		for (String packagePrefix: packagePrefixes) {
			if (canonicalName.startsWith(packagePrefix)) {
				return true;
			}
		}
		return false;
	}
}

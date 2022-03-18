package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;

public class AnalyzedJavaClassCache implements IAnalyzedJavaClassProvider {

	private final JavaClasspath classPath;
	private final Map<JavaClassName, CacheEntry> cacheMap;
	private long queryCount;
	private long entryCreationCount;

	public AnalyzedJavaClassCache(JavaClasspath classPath) {

		this.classPath = Objects.requireNonNull(classPath);
		this.cacheMap = new TreeMap<>();
		this.queryCount = 0;
		this.entryCreationCount = 0;
	}

	@Override
	public Optional<AnalyzedJavaClass> getAnalyzedClass(JavaClassName className) {

		Objects.requireNonNull(className);
		++queryCount;
		return cacheMap//
			.computeIfAbsent(className, CacheEntry::new)
			.getJavaClass();
	}

	long getQueryCount() {

		return queryCount;
	}

	long getEntryCreationCount() {

		return entryCreationCount;
	}

	private class CacheEntry {

		private final Optional<AnalyzedJavaClass> javaClass;

		public CacheEntry(JavaClassName className) {

			this.javaClass = classPath//
				.getAllRoots()
				.stream()
				.map(root -> new AnalyzedJavaClassFetcher().fetchJavaClass(root, className))
				.flatMap(Optional::stream)
				.findFirst();
			++entryCreationCount;
		}

		public Optional<AnalyzedJavaClass> getJavaClass() {

			return javaClass;
		}
	}
}

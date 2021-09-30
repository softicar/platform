package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.classpath.JavaClasspathLoader;
import org.junit.Assert;
import org.junit.Test;

public class AnalyzedJavaClassCacheTest extends Assert {

	private static final JavaClasspath JAVA_CLASSPATH = new JavaClasspathLoader().load();
	private final AnalyzedJavaClassCache cache;

	public AnalyzedJavaClassCacheTest() {

		this.cache = new AnalyzedJavaClassCache(JAVA_CLASSPATH);
	}

	@Test
	public void testGetAnalyzedClassWithClassInRoot() {

		var javaClass = cache.getAnalyzedClass(new JavaClassName(AnalyzedJavaClassCache.class));

		assertTrue(javaClass.isPresent());
		assertEquals(1, cache.getQueryCount());
		assertEquals(1, cache.getEntryCreationCount());
	}

	@Test
	public void testGetAnalyzedClassWithClassInRootAndRepeatedLookup() {

		cache.getAnalyzedClass(new JavaClassName(AnalyzedJavaClassCache.class));
		cache.getAnalyzedClass(new JavaClassName(AnalyzedJavaClassCache.class));
		var javaClass = cache.getAnalyzedClass(new JavaClassName(AnalyzedJavaClassCache.class));

		assertTrue(javaClass.isPresent());
		assertEquals(3, cache.getQueryCount());
		assertEquals(1, cache.getEntryCreationCount());
	}

	@Test
	public void testGetAnalyzedClassWithClassOutsideRoot() {

		var javaClass = cache.getAnalyzedClass(new JavaClassName(String.class));

		assertFalse(javaClass.isPresent());
		assertEquals(1, cache.getQueryCount());
		assertEquals(1, cache.getEntryCreationCount());
	}

	@Test
	public void testGetAnalyzedClassWithClassOutsideRootAndRepeatedLookup() {

		cache.getAnalyzedClass(new JavaClassName(String.class));
		cache.getAnalyzedClass(new JavaClassName(String.class));
		var javaClass = cache.getAnalyzedClass(new JavaClassName(String.class));

		assertFalse(javaClass.isPresent());
		assertEquals(3, cache.getQueryCount());
		assertEquals(1, cache.getEntryCreationCount());
	}

	@Test
	public void testGetAnalyzedClassWithNonexistentClass() {

		var javaClass = cache.getAnalyzedClass(new JavaClassName("qweasdzxc"));

		assertFalse(javaClass.isPresent());
		assertEquals(1, cache.getQueryCount());
		assertEquals(1, cache.getEntryCreationCount());
	}

	@Test
	public void testGetAnalyzedClassWithNonexistentClassAndRepeatedLookup() {

		cache.getAnalyzedClass(new JavaClassName("qweasdzxc"));
		cache.getAnalyzedClass(new JavaClassName("qweasdzxc"));
		var javaClass = cache.getAnalyzedClass(new JavaClassName("qweasdzxc"));

		assertFalse(javaClass.isPresent());
		assertEquals(3, cache.getQueryCount());
		assertEquals(1, cache.getEntryCreationCount());
	}

	@Test(expected = NullPointerException.class)
	public void testGetAnalyzedClassWithNull() {

		cache.getAnalyzedClass(null);
	}
}

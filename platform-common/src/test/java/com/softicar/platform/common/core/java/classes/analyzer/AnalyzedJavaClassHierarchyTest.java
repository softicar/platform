package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.classpath.JavaClasspathLoader;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class AnalyzedJavaClassHierarchyTest extends AbstractTest {

	private static final String TEST_SUPER = "com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassHierarchyTestSuperClass";
	private static final String TEST_INTERMEDIATE = "com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassHierarchyTestClass";
	private static final String TEST_SUB = "com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassHierarchyTestSubClass";
	private static final String JAVA_ITERABLE = "java.util.Iterable";
	private static final String JAVA_COLLECTION = "java.util.Collection";
	private static final String JAVA_SET = "java.util.Set";

	private static final JavaClasspath JAVA_CLASSPATH = new JavaClasspathLoader().load();
	private final AnalyzedJavaClassHierarchy hierarchy;

	public AnalyzedJavaClassHierarchyTest() {

		var cache = new AnalyzedJavaClassCache(JAVA_CLASSPATH);
		this.hierarchy = new AnalyzedJavaClassHierarchy(cache);
	}

	@Test
	public void testIsInstanceWithClassesInRoot() {

		assertTrue(isInstance(javaClass(TEST_SUB), className(TEST_SUB)));
		assertTrue(isInstance(javaClass(TEST_SUB), className(TEST_INTERMEDIATE)));
		assertTrue(isInstance(javaClass(TEST_SUB), className(TEST_SUPER)));

		assertFalse(isInstance(javaClass(TEST_INTERMEDIATE), className(TEST_SUB)));
		assertTrue(isInstance(javaClass(TEST_INTERMEDIATE), className(TEST_INTERMEDIATE)));
		assertTrue(isInstance(javaClass(TEST_INTERMEDIATE), className(TEST_SUPER)));

		assertFalse(isInstance(javaClass(TEST_SUPER), className(TEST_SUB)));
		assertFalse(isInstance(javaClass(TEST_SUPER), className(TEST_INTERMEDIATE)));
		assertTrue(isInstance(javaClass(TEST_SUPER), className(TEST_SUPER)));
	}

	@Test
	public void testIsInstanceWithClassesOutsideRoot() {

		assertFalse(isInstance(javaClass(JAVA_SET), className(JAVA_SET)));
		assertFalse(isInstance(javaClass(JAVA_SET), className(JAVA_COLLECTION)));
		assertFalse(isInstance(javaClass(JAVA_SET), className(JAVA_ITERABLE)));

		assertFalse(isInstance(javaClass(JAVA_COLLECTION), className(JAVA_SET)));
		assertFalse(isInstance(javaClass(JAVA_COLLECTION), className(JAVA_COLLECTION)));
		assertFalse(isInstance(javaClass(JAVA_COLLECTION), className(JAVA_ITERABLE)));

		assertFalse(isInstance(javaClass(JAVA_ITERABLE), className(JAVA_SET)));
		assertFalse(isInstance(javaClass(JAVA_ITERABLE), className(JAVA_COLLECTION)));
		assertFalse(isInstance(javaClass(JAVA_ITERABLE), className(JAVA_ITERABLE)));
	}

	@Test
	public void testIsInstanceWithNonexistentClasses() {

		assertFalse(isInstance(javaClass("qweasdzxc"), className("rtyfghvbn")));
	}

	@Test(expected = NullPointerException.class)
	public void testIsInstanceWithNullJavaClass() {

		assertFalse(isInstance(null, className(JAVA_COLLECTION)));
	}

	@Test(expected = NullPointerException.class)
	public void testIsInstanceWithNullClassName() {

		assertFalse(isInstance(javaClass(JAVA_COLLECTION), null));
	}

	private boolean isInstance(AnalyzedJavaClass javaClass, JavaClassName superClassName) {

		return hierarchy.isInstance(javaClass, superClassName);
	}

	private static JavaClassName className(String className) {

		return new JavaClassName(className);
	}

	private static AnalyzedJavaClass javaClass(String className) {

		AnalyzedJavaClass javaClass = new AnalyzedJavaClass();
		javaClass.setClassName(className);
		return javaClass;
	}
}

package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class JavaClassAnalyzerTestForAccessFlags extends AbstractTest {

	private AnalyzedJavaClass javaClass;

	@Test
	public void testAccessFlagsWithTestEnum() {

		analyze(TestEnum.class);

		assertFalse(javaClass.isAbstract());
		assertFalse(javaClass.isAnnotation());
		assertTrue(javaClass.isEnum());
		assertTrue(javaClass.isFinal());
		assertFalse(javaClass.isInterface());
		assertFalse(javaClass.isPublic());
	}

	@Test
	public void testAccessFlagsWithAbstractList() {

		analyze(AbstractList.class);

		assertTrue(javaClass.isAbstract());
		assertFalse(javaClass.isAnnotation());
		assertFalse(javaClass.isEnum());
		assertFalse(javaClass.isFinal());
		assertFalse(javaClass.isInterface());
		assertTrue(javaClass.isPublic());
	}

	@Test
	public void testAccessFlagsWithArrayList() {

		analyze(ArrayList.class);

		assertFalse(javaClass.isAbstract());
		assertFalse(javaClass.isAnnotation());
		assertFalse(javaClass.isEnum());
		assertFalse(javaClass.isFinal());
		assertFalse(javaClass.isInterface());
		assertTrue(javaClass.isPublic());
	}

	@Test
	public void testAccessFlagsWithListInterface() {

		analyze(List.class);

		assertTrue(javaClass.isAbstract());
		assertFalse(javaClass.isAnnotation());
		assertFalse(javaClass.isEnum());
		assertFalse(javaClass.isFinal());
		assertTrue(javaClass.isInterface());
		assertTrue(javaClass.isPublic());
	}

	@Test
	public void testAccessFlagsWithDeprecatedAnnotation() {

		analyze(Deprecated.class);

		assertTrue(javaClass.isAbstract());
		assertTrue(javaClass.isAnnotation());
		assertFalse(javaClass.isEnum());
		assertFalse(javaClass.isFinal());
		assertTrue(javaClass.isInterface());
		assertTrue(javaClass.isPublic());
	}

	private void analyze(Class<?> classToAnalyze) {

		this.javaClass = new JavaClassAnalyzer(classToAnalyze).analyze();
	}
}

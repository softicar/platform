package com.softicar.platform.common.core.java.classpath.linking;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.JavaClassAnalyzer;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

public class JavaClasspathLinkingValidatorTest extends AbstractTest {

	private final Map<JavaClassName, AnalyzedJavaClass> classes;

	public JavaClasspathLinkingValidatorTest() {

		this.classes = new TreeMap<>();
	}

	@Test
	public void testWithUnresolvedSuperClass() {

		addClass("ClassWithUnresolvedSuperClass.class.bin");

		JavaClasspathLinkingValidatorResult validatorResult = new JavaClasspathLinkingValidator(classes).validate();

		assertEquals(//
			"[com.example.MissingClass]",
			validatorResult.getMissingClasses().keySet().toString());

		assertEquals(//
			"[com.example.MissingClass.<init>()V]",
			validatorResult.getUnresolvedMethodReferences().keySet().toString());
	}

	@Test
	public void testWithUnresolvedMethodReference() {

		addClass("EmptyClass.class.bin");
		addClass("ClassWithUnresolvedMethodReference.class.bin");

		JavaClasspathLinkingValidatorResult validatorResult = new JavaClasspathLinkingValidator(classes).validate();

		assertEquals(//
			"[]",
			validatorResult.getMissingClasses().keySet().toString());

		assertEquals(//
			"[com.example.EmptyClass.bar()V]",
			validatorResult.getUnresolvedMethodReferences().keySet().toString());
	}

	private void addClass(String className) {

		AnalyzedJavaClass javaClass = new JavaClassAnalyzer(() -> getClass().getResourceAsStream(className)).analyze();
		classes.put(javaClass.getClassName(), javaClass);
	}
}

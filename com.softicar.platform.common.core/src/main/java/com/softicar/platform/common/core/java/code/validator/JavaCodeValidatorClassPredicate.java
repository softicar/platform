package com.softicar.platform.common.core.java.code.validator;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import java.util.function.Predicate;

public class JavaCodeValidatorClassPredicate implements Predicate<AnalyzedJavaClass> {

	private static final Class<JavaCodeValidator> ANNOTATION_CLASS = JavaCodeValidator.class;

	@Override
	public boolean test(AnalyzedJavaClass javaClass) {

		return javaClass.getAnnotations().stream().anyMatch(annotation -> annotation.is(ANNOTATION_CLASS));
	}
}

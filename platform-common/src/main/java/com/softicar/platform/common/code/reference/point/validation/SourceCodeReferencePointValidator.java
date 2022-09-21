package com.softicar.platform.common.code.reference.point.validation;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.code.reference.point.ISourceCodeReferencePoint;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.code.validator.AbstractJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaClassValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;

/**
 * Validates all classes with an {@link SourceCodeReferencePointUuid}
 * annotation.
 *
 * @author Oliver Richers
 */
@JavaCodeValidator
public class SourceCodeReferencePointValidator extends AbstractJavaCodeValidator {

	public SourceCodeReferencePointValidator() {

		setClassFilter(this::isSourceCodeReferencePoint);
		addClassValidator(this::validateClass);
	}

	private boolean isSourceCodeReferencePoint(AnalyzedJavaClass javaClass) {

		return hasReferencePointAnnotation(javaClass) || implementsReferencePointInterface(javaClass);
	}

	private boolean hasReferencePointAnnotation(AnalyzedJavaClass javaClass) {

		return javaClass.hasAnnotation(SourceCodeReferencePointUuid.class);
	}

	private boolean implementsReferencePointInterface(AnalyzedJavaClass javaClass) {

		// TODO check transitively implemented interfaces
		return javaClass.implementsInterface(ISourceCodeReferencePoint.class) && !javaClass.isAbstract() && !javaClass.isInterface();
	}

	private void validateClass(Class<?> referencePointClass) {

		new JavaClassValidator(this, referencePointClass)//
			.assertIsNotAbstract()
			.assertIsNotInterface()
			.assertImplementsInterface(ISourceCodeReferencePoint.class)
			.assertHasAnnotation(SourceCodeReferencePointUuid.class)
			.assertHasParameterlessConstructor()
			.assertHasNoNonStaticFieldsDeep();
	}
}

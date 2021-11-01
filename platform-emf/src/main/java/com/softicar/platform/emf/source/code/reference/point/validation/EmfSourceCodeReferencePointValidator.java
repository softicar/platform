package com.softicar.platform.emf.source.code.reference.point.validation;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.code.validator.AbstractJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaClassValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.source.code.reference.point.IEmfSourceCodeReferencePoint;

/**
 * Validates all classes with an {@link EmfSourceCodeReferencePointUuid}
 * annotation.
 *
 * @author Oliver Richers
 */
@JavaCodeValidator
public class EmfSourceCodeReferencePointValidator extends AbstractJavaCodeValidator {

	public EmfSourceCodeReferencePointValidator() {

		setClassFilter(this::isReferencePointClass);
		addClassValidator(this::validateClass);
	}

	private boolean isReferencePointClass(AnalyzedJavaClass javaClass) {

		return hasReferencePointAnnotation(javaClass) || implementsReferencePointInterface(javaClass);
	}

	private boolean hasReferencePointAnnotation(AnalyzedJavaClass javaClass) {

		return javaClass.hasAnnotation(EmfSourceCodeReferencePointUuid.class);
	}

	private boolean implementsReferencePointInterface(AnalyzedJavaClass javaClass) {

		// TODO check transitively implemented interfaces
		return javaClass.implementsInterface(IEmfSourceCodeReferencePoint.class) && !javaClass.isAbstract() && !javaClass.isInterface();
	}

	private void validateClass(Class<?> referencePointClass) {

		new JavaClassValidator(this, referencePointClass)//
			.assertIsNotAbstract()
			.assertIsNotInterface()
			.assertImplementsInterface(IEmfSourceCodeReferencePoint.class)
			.assertHasAnnotation(EmfSourceCodeReferencePointUuid.class)
			.assertHasParameterlessConstructor()
			.assertHasNoNonStaticFieldsDeep();
	}
}

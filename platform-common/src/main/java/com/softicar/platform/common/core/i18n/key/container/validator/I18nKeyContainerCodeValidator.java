package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Validates all classes that construct {@link II18nKey} instances.
 *
 * @author Oliver Richers
 */
@JavaCodeValidator
public class I18nKeyContainerCodeValidator implements IJavaCodeValidator {

	private JavaCodeValidationEnvironment environment;
	private ConstantContainerValidatorResult<II18nKey> result;
	private Set<String> mandatoryTranslations;

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		this.environment = environment;
		this.mandatoryTranslations = new TreeSet<>(
			environment//
				.getConfigurationJsonValueReader()
				.readList("$.mandatoryTranslations"));
		this.result = new ConstantContainerValidatorResult<>();

		for (var root: JavaClasspath.getInstance().getPayloadRoots()) {
			root//
				.getAnalyzedClasses()
				.stream()
				.filter(this::isI18nContainer)
				.forEach(this::validateI18nContainer);
		}

		result.throwExceptionIfNotEmpty();
	}

	private boolean isI18nContainer(AnalyzedJavaClass javaClass) {

		return I18nKeyConstructorReference//
			.getAll()
			.stream()
			.anyMatch(javaClass.getReferencedMethods()::contains);
	}

	private void validateI18nContainer(AnalyzedJavaClass javaClass) {

		environment.logVerbose("Validating i18n container: %s", javaClass.getClassName());

		new I18nKeyContainerValidator(javaClass.loadClass())//
			.addMandatoryLanguages(mandatoryTranslations)
			.validate(result);
	}
}

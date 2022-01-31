package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.constant.container.validator.AbstractConstantContainersValidator;
import com.softicar.platform.common.core.constant.container.validator.IConstantContainerValidator;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassFetcher;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implements {@link IJavaCodeValidator} for I18N classes.
 * <p>
 * Checks all classes below all {@link JavaClasspath} root folders. Every file
 * that instantiates an {@link II18nKey} class is assumed to be an I18N class.
 * All such classes are validated using {@link I18nKeyContainerValidator}.
 *
 * @author Oliver Richers
 */
@JavaCodeValidator
public class I18nKeyContainersValidator extends AbstractConstantContainersValidator<II18nKey> {

	private Set<String> mandatoryTranslations;

	public I18nKeyContainersValidator() {

		super(II18nKey.class);
	}

	@Override
	protected void prepareForEnvironment(JavaCodeValidationEnvironment environment) {

		var reader = environment.getConfigurationJsonValueReader();
		List<String> languages = reader.readList("$.mandatoryTranslations");
		this.mandatoryTranslations = new TreeSet<>(languages);
	}

	@Override
	protected AnalyzedJavaClassFetcher createClassFetcher() {

		return new AnalyzedJavaClassFetcher().setFilter(this::isRelevant);
	}

	@Override
	protected IConstantContainerValidator<II18nKey> createValidator(Class<?> containerClass) {

		return new I18nKeyContainerValidator(containerClass)//
			.addMandatoryLanguages(mandatoryTranslations);
	}

	private boolean isRelevant(AnalyzedJavaClass javaClass) {

		return I18nKeyConstructorReference//
			.getAll()
			.stream()
			.anyMatch(javaClass.getReferencedMethods()::contains);
	}
}

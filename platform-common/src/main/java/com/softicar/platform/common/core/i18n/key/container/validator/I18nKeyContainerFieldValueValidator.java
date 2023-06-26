package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.AbstractConstantContainerFieldValueValidator;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldUnexpectedNameError;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;
import com.softicar.platform.common.core.i18n.I18n3;
import com.softicar.platform.common.core.i18n.I18n4;
import com.softicar.platform.common.core.i18n.I18n5;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.i18n.key.computer.I18nKeyComputer;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldIllegalEnglishStringError;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldMissingMandatoryTranslationError;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldWrongFormatSpecifierCountError;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldWrongTypeError;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

class I18nKeyContainerFieldValueValidator extends AbstractConstantContainerFieldValueValidator<II18nKey> {

	private final Collection<LanguageEnum> mandatoryLanguages;
	private String defaultEnglishString;
	private int formatSpecifierCount;
	private ConstantContainerValidatorResult<II18nKey> result;
	private II18nKey fieldValue;

	public I18nKeyContainerFieldValueValidator(IConstantContainerField<II18nKey> field, Collection<LanguageEnum> mandatoryLanguages) {

		super(field);
		this.mandatoryLanguages = mandatoryLanguages;
	}

	@Override
	protected void validate(ConstantContainerValidatorResult<II18nKey> result, II18nKey fieldValue) {

		this.result = result;
		this.fieldValue = fieldValue;

		this.defaultEnglishString = fieldValue.getDefault();
		this.formatSpecifierCount = new I18nKeyComputer(defaultEnglishString).computeArgumentCount();

		validateEnglishString();
		validateMandatoryTranslations();
		validateFormatSpecifiersOfTranslations();
		validateFieldType();
		validateFieldName();
	}

	private void validateEnglishString() {

		if (defaultEnglishString.isEmpty()) {
			result.addError(new I18nKeyContainerFieldIllegalEnglishStringError(field, "Default English string is empty."));
		} else if (!defaultEnglishString.trim().equals(defaultEnglishString)) {
			result.addError(new I18nKeyContainerFieldIllegalEnglishStringError(field, "Default English string has leading or trailing whitespace."));
		} else if (isMoreThanOneSentence(defaultEnglishString)) {
			result.addError(new I18nKeyContainerFieldIllegalEnglishStringError(field, "Default English string contains more than one sentence."));
		}
	}

	private void validateFormatSpecifiersOfTranslations() {

		fieldValue//
			.getLanguages()
			.stream()
			.filter(language -> language != LanguageEnum.ENGLISH)
			.forEach(this::validateTranslation);
	}

	private void validateMandatoryTranslations() {

		mandatoryLanguages.forEach(this::validateMandatoryTranslation);
	}

	private void validateMandatoryTranslation(LanguageEnum languageEnum) {

		if (!fieldValue.toLanguage(languageEnum).isPresent()) {
			result.addError(new I18nKeyContainerFieldMissingMandatoryTranslationError(field, languageEnum));
		}
	}

	private void validateTranslation(LanguageEnum languageEnum) {

		fieldValue.toLanguage(languageEnum).ifPresent(text -> validateTranslation(languageEnum, text));
	}

	private void validateTranslation(LanguageEnum languageEnum, String text) {

		if (new I18nKeyComputer(text).computeArgumentCount() != formatSpecifierCount) {
			result.addError(new I18nKeyContainerFieldWrongFormatSpecifierCountError(field, languageEnum));
		}
	}

	private void validateFieldType() {

		getExpectedFieldTypeAsOptional().ifPresent(this::validateFieldType);
	}

	private void validateFieldType(Class<?> expectedFieldType) {

		if (!expectedFieldType.isAssignableFrom(field.getType())) {
			result.addError(new I18nKeyContainerFieldWrongTypeError(field, expectedFieldType));
		}
	}

	private void validateFieldName() {

		String expectedName = new I18nKeyComputer(defaultEnglishString).compute();
		if (!field.getName().equals(expectedName)) {
			result.addError(new ConstantContainerFieldUnexpectedNameError<>(field, expectedName));
		}
	}

	// ------------------------------ utilities ------------------------------ //

	private boolean isMoreThanOneSentence(String text) {

		text = text.replaceAll("[\\.\\?!]", "!");
		if (Arrays.asList(text.split("!")).stream().map(String::trim).filter(it -> !it.isEmpty()).count() > 1) {
			return true;
		} else {
			return false;
		}
	}

	private Optional<Class<?>> getExpectedFieldTypeAsOptional() {

		Class<?> expectedFieldType = getExpectedFieldType();
		if (expectedFieldType != null) {
			return Optional.of(expectedFieldType);
		} else {
			result.addError(new I18nKeyContainerFieldIllegalEnglishStringError(field, "Key string has too many format specifiers."));
			return Optional.empty();
		}
	}

	private Class<?> getExpectedFieldType() {

		switch (formatSpecifierCount) {
		case 0:
			return I18n0.class;
		case 1:
			return I18n1.class;
		case 2:
			return I18n2.class;
		case 3:
			return I18n3.class;
		case 4:
			return I18n4.class;
		case 5:
			return I18n5.class;
		}
		return null;
	}
}

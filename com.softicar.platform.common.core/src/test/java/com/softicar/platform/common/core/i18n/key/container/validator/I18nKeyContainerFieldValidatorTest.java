package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResultAsserter;
import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldMissingMandatoryTranslationError;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;

public class I18nKeyContainerFieldValidatorTest {

	@Test
	public void testWithMissingGermanTranslation() {

		I18n0 key = new I18n0("English Only");
		I18nKeyContainerTestField field = new I18nKeyContainerTestField("ENGLISH_ONLY", key);

		validate(field, Arrays.asList(LanguageEnum.GERMAN))//
			.assertError(I18nKeyContainerFieldMissingMandatoryTranslationError.class)
			.assertNoErrors();
	}

	@Test
	public void testWithoutMissingGermanTranslation() {

		I18n0 key = new I18n0("English and German").de("Englisch und Deutsch");
		I18nKeyContainerTestField field = new I18nKeyContainerTestField("ENGLISH_AND_GERMAN", key);

		validate(field, Arrays.asList(LanguageEnum.GERMAN))//
			.assertNoErrors();
	}

	private ConstantContainerValidatorResultAsserter validate(I18nKeyContainerTestField field, Collection<LanguageEnum> mandatoryLanguages) {

		var result = new ConstantContainerValidatorResult<II18nKey>();
		new I18nKeyContainerFieldValidator(field, null, mandatoryLanguages).validate(result);
		return new ConstantContainerValidatorResultAsserter(result);
	}
}

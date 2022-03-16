package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldMustBeFinalError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldMustBePublicError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldMustBeStaticError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldOrderError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldUnexpectedNameError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldValueUndefinedError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerIllegalFieldNameError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerMustBeInterfaceError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerMustNotDeclareMethodError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerMustNotExtendOtherClassError;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResultAsserter;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldIllegalEnglishStringError;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldWrongTypeError;
import org.junit.Assert;
import org.junit.Test;

public class I18nKeyContainerValidatorTest extends Assert {

	@Test
	public void testWithValidI18nKeyContainerClass() {

		validate(I18nKeyTestContainer.class)//
			.assertNoErrors();
	}

	@Test
	public void testWithExtendsAndImplements() {

		validate(I18nKeyTestContainerWithExtendsAndImplements.class)//
			.assertError(ConstantContainerMustBeInterfaceError.class)
			.assertError(ConstantContainerMustNotExtendOtherClassError.class)
			.assertError(ConstantContainerMustNotDeclareMethodError.class)
			.assertNoErrors();
	}

	@Test
	public void testWithIllegalFieldModifiers() {

		validate(I18nKeyTestContainerWithIllegalFieldModifiers.class)//
			.assertError(ConstantContainerFieldMustBeFinalError.class, "NON_FINAL")
			.assertError(ConstantContainerFieldMustBeStaticError.class, "NON_STATIC")
			.assertError(ConstantContainerFieldMustBePublicError.class, "VISIBILITY_PRIVATE")
			.assertError(ConstantContainerFieldMustBePublicError.class, "VISIBILITY_PROTECTED")
			.assertError(ConstantContainerFieldMustBePublicError.class, "VISIBILITY_DEFAULT")
			// unavoidable errors:
			.assertError(ConstantContainerMustBeInterfaceError.class)
			.assertError(ConstantContainerMustNotExtendOtherClassError.class)
			.assertError(ConstantContainerFieldValueUndefinedError.class, "NON_STATIC")
			.assertNoErrors();
	}

	@Test
	public void testWithIllegalFieldNames() {

		validate(I18nKeyTestContainerWithIllegalFieldNames.class)//
			.assertError(ConstantContainerIllegalFieldNameError.class, "_LEADING_UNSERSCORE")
			.assertError(ConstantContainerIllegalFieldNameError.class, "DOUBLE__UNSERSCORE")
			.assertError(ConstantContainerIllegalFieldNameError.class, "TRAILING_UNSERSCORE_")
			.assertError(ConstantContainerIllegalFieldNameError.class, "lower_case")
			// unavoidable errors:
			.assertError(ConstantContainerFieldUnexpectedNameError.class, "_LEADING_UNSERSCORE")
			.assertError(ConstantContainerFieldUnexpectedNameError.class, "DOUBLE__UNSERSCORE")
			.assertError(ConstantContainerFieldUnexpectedNameError.class, "TRAILING_UNSERSCORE_")
			.assertError(ConstantContainerFieldUnexpectedNameError.class, "lower_case")
			.assertNoErrors();
	}

	@Test
	public void testWithUnexpectedFieldTypes() {

		validate(I18nKeyTestContainerWithIllegalFieldTypes.class)//
			.assertError(I18nKeyContainerFieldWrongTypeError.class, "THIS_IS_ARG1")
			.assertError(I18nKeyContainerFieldWrongTypeError.class, "THIS_IS_WITHOUT_ARGUMENT")
			.assertNoErrors();
	}

	@Test
	public void testWithUnexpectedFieldNames() {

		validate(I18nKeyTestContainerWithUnexpectedFieldNames.class)//
			.assertError(ConstantContainerFieldUnexpectedNameError.class, "FOO")
			.assertError(ConstantContainerFieldUnexpectedNameError.class, "BAR")
			.assertNoErrors();
	}

	@Test
	public void testWithIllegalFieldOrder() {

		validate(I18nKeyTestContainerWithIllegalFieldOrder.class)//
			.assertError(ConstantContainerFieldOrderError.class, "A")
			.assertError(ConstantContainerFieldOrderError.class, "X_Y")
			.assertNoErrors();
	}

	@Test
	public void testWithIllegalKeyStrings() {

		validate(I18nKeyTestContainerWithIllegalKeyStrings.class)//
			.assertError(I18nKeyContainerFieldIllegalEnglishStringError.class, "EMPTY")
			.assertError(I18nKeyContainerFieldIllegalEnglishStringError.class, "LEADING_WHITESPACE")
			.assertError(I18nKeyContainerFieldIllegalEnglishStringError.class, "TRAILING_WHITESPACE")
			.assertError(I18nKeyContainerFieldIllegalEnglishStringError.class, "FIRST_SENTENCE_SECOND_SENTENCE")
			// unavoidable error:
			.assertError(ConstantContainerFieldUnexpectedNameError.class, "EMPTY")
			.assertNoErrors();
	}

	private ConstantContainerValidatorResultAsserter validate(Class<?> i18nClass) {

		var result = new ConstantContainerValidatorResult<II18nKey>();
		new I18nKeyContainerValidator(i18nClass).validate(result);
		return new ConstantContainerValidatorResultAsserter(result);
	}
}

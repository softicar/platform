package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldUnexpectedNameError;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResultAsserter;
import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldIllegalEnglishStringError;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldWrongFormatSpecifierCountError;
import com.softicar.platform.common.core.i18n.key.container.validator.error.I18nKeyContainerFieldWrongTypeError;
import java.util.Collections;
import org.junit.Test;

public class I18nKeyContainerFieldValueValidatorTest {

	@Test
	public void test() {

		validate("FOO", new I18n0("foo")).assertNoErrors();
		validate("THIS_IS_ARG1", new I18n1("This is %s.")).assertNoErrors();
		validate("ARG1_OF_ARG2", new I18n2("%s of %s")).assertNoErrors();
	}

	@Test
	public void testWithEmptyEnglishString() {

		validate("FOO", new I18n0(""))//
			.assertError("FOO", I18nKeyContainerFieldIllegalEnglishStringError.class)
			.assertError("FOO", ConstantContainerFieldUnexpectedNameError.class)
			.assertNoErrors();
	}

	@Test
	public void testWithExtraWhitespace() {

		validate("FOO", new I18n0(" foo"))//
			.assertError("FOO", I18nKeyContainerFieldIllegalEnglishStringError.class)
			.assertNoErrors();

		validate("FOO", new I18n0("foo "))//
			.assertError("FOO", I18nKeyContainerFieldIllegalEnglishStringError.class)
			.assertNoErrors();
	}

	@Test
	public void testWithMultipleSentences() {

		validate("HELLO_THIS_IS_IT", new I18n0("Hello. This is it."))//
			.assertError("HELLO_THIS_IS_IT", I18nKeyContainerFieldIllegalEnglishStringError.class)
			.assertNoErrors();
	}

	@Test
	public void testWithWrongType() {

		validate("NO_ARG", new I18n1("no arg"))//
			.assertError("NO_ARG", I18nKeyContainerFieldWrongTypeError.class)
			.assertNoErrors();

		validate("ONE_ARG1", new I18n0("one %s"))//
			.assertError("ONE_ARG1", I18nKeyContainerFieldWrongTypeError.class)
			.assertNoErrors();
	}

	@Test
	public void testWithWrongFormatSpecifierCountInTranslation() {

		validate("NO_ARG", new I18n0("no arg").de("kein Arg %s"))//
			.assertError("NO_ARG", I18nKeyContainerFieldWrongFormatSpecifierCountError.class)
			.assertNoErrors();

		validate("ONE_ARG1", new I18n1("one %s").de("eins"))//
			.assertError("ONE_ARG1", I18nKeyContainerFieldWrongFormatSpecifierCountError.class)
			.assertNoErrors();
	}

	@Test
	public void testWithUnexpectedFieldName() {

		validate("FOO", new I18n0("bar"))//
			.assertError("FOO", ConstantContainerFieldUnexpectedNameError.class)
			.assertNoErrors();
	}

	private ConstantContainerValidatorResultAsserter validate(String fieldName, II18nKey key) {

		I18nKeyContainerTestField field = new I18nKeyContainerTestField(fieldName, key);

		var result = new ConstantContainerValidatorResult<II18nKey>();
		new I18nKeyContainerFieldValueValidator(field, Collections.emptyList()).validate(result);
		return new ConstantContainerValidatorResultAsserter(result);
	}
}

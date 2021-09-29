package com.softicar.platform.common.core.i18n.key.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.key.II18nKey;

public class I18nKeyContainerFieldWrongFormatSpecifierCountError extends AbstractConstantContainerValidationError<II18nKey> {

	public I18nKeyContainerFieldWrongFormatSpecifierCountError(IConstantContainerField<II18nKey> field, LanguageEnum language) {

		super(field, "Translation to %s has wrong format specifier count.", language.toDisplay());
	}
}

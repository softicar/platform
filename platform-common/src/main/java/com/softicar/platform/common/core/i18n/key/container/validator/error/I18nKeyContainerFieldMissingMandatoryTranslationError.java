package com.softicar.platform.common.core.i18n.key.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.key.II18nKey;

public class I18nKeyContainerFieldMissingMandatoryTranslationError extends AbstractConstantContainerValidationError<II18nKey> {

	public I18nKeyContainerFieldMissingMandatoryTranslationError(IConstantContainerField<II18nKey> field, LanguageEnum languageEnum) {

		super(field, "Lacks translation into %s.", languageEnum.toString());
	}
}

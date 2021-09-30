package com.softicar.platform.common.core.i18n.key.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.core.i18n.key.II18nKey;

public class I18nKeyContainerFieldIllegalEnglishStringError extends AbstractConstantContainerValidationError<II18nKey> {

	public I18nKeyContainerFieldIllegalEnglishStringError(IConstantContainerField<II18nKey> field, String error, Object...arguments) {

		super(field, error, arguments);
	}
}

package com.softicar.platform.common.core.i18n.key.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.core.i18n.key.II18nKey;

public class I18nKeyContainerFieldWrongTypeError extends AbstractConstantContainerValidationError<II18nKey> {

	public I18nKeyContainerFieldWrongTypeError(IConstantContainerField<II18nKey> field, Class<?> expectedClass) {

		super(field, "Expected type: %s", expectedClass.getSimpleName());
	}
}

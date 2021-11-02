package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.AbstractConstantContainerFieldValidator;
import com.softicar.platform.common.core.constant.container.validator.IConstantContainerValidator;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import java.util.Collection;

class I18nKeyContainerFieldValidator extends AbstractConstantContainerFieldValidator<II18nKey> {

	private final Collection<LanguageEnum> mandatoryLanguages;

	public I18nKeyContainerFieldValidator(IConstantContainerField<II18nKey> currentField, IConstantContainerField<II18nKey> previousField,
			Collection<LanguageEnum> mandatoryLanguages) {

		super(currentField, previousField);

		this.mandatoryLanguages = mandatoryLanguages;
	}

	@Override
	protected IConstantContainerValidator<II18nKey> createFieldValueValidator(IConstantContainerField<II18nKey> field) {

		return new I18nKeyContainerFieldValueValidator(field, mandatoryLanguages);
	}
}

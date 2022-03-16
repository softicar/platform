package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.AbstractConstantContainerValidator;
import com.softicar.platform.common.core.constant.container.validator.IConstantContainerValidator;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.i18n.key.computer.I18nKeyComputer;
import java.util.Set;
import java.util.TreeSet;

/**
 * Validates a given class with respect to the I18n class requirements.
 * <p>
 * <ul>
 * <li>The class must be an interface.</li>
 * <li>The class may not have a super class.</li>
 * <li>The class may not declare any methods.</li>
 * </ul>
 * <ul>
 * <li>All fields must be <b>public</b> and <b>static</b> and <b>final</b>.</li>
 * <li>All fields must be in alphabetic order.</li>
 * <li>All field names must obey the Java conventions for constants.</li>
 * <li>All field names must match the key strings according to
 * {@link I18nKeyComputer}.</li>
 * <li>All field types must match the format specifier count.</li>
 * </ul>
 * <ul>
 * <li>No key string may be empty.</li>
 * <li>No key string may have leading or trailing whitespace.</li>
 * <li>No key string may contain more than one sentence.</li>
 * </ul>
 *
 * @author Oliver Richers
 */
class I18nKeyContainerValidator extends AbstractConstantContainerValidator<II18nKey> {

	private final Set<LanguageEnum> mandatoryLanguages;

	public I18nKeyContainerValidator(Class<?> containerClass) {

		super(containerClass, II18nKey.class);

		this.mandatoryLanguages = new TreeSet<>();
	}

	public I18nKeyContainerValidator addMandatoryLanguages(Set<String> languages) {

		languages.forEach(this::addMandatoryLanguage);
		return this;
	}

	public I18nKeyContainerValidator addMandatoryLanguage(String language) {

		this.mandatoryLanguages.add(LanguageEnum.getByIso6391(language));
		return this;
	}

	@Override
	protected IConstantContainerValidator<II18nKey> createFieldValidator(IConstantContainerField<II18nKey> currentField,
			IConstantContainerField<II18nKey> previousField) {

		return new I18nKeyContainerFieldValidator(currentField, previousField, mandatoryLanguages);
	}
}

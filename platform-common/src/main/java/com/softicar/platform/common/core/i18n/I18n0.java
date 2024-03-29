package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.i18n.key.AbstractI18nKey;
import com.softicar.platform.common.core.i18n.key.II18nKey;

/**
 * Represents an unparameterized {@link II18nKey}.
 *
 * @author Oliver Richers
 */
public final class I18n0 extends AbstractI18nKey<I18n0> implements IDisplayString {

	public I18n0(String keyString) {

		super(keyString);
	}

	/**
	 * Translates this {@link I18n0}.
	 *
	 * @return this {@link I18n0} as translated {@link String} (never null)
	 */
	@Override
	public String toString() {

		return new I18nKeyDisplayString(this).toString();
	}

	@Override
	protected I18n0 getThis() {

		return this;
	}
}

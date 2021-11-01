package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.i18n.key.AbstractI18nKey;
import com.softicar.platform.common.core.i18n.key.II18nKey;

/**
 * Represents a parameterized {@link II18nKey}.
 *
 * @author Oliver Richers
 */
public final class I18n1 extends AbstractI18nKey<I18n1> {

	public I18n1(String keyString) {

		super(keyString);
	}

	/**
	 * Converts this {@link II18nKey}.
	 *
	 * @param argument
	 *            formatting parameter
	 * @return the {@link IDisplayString} (never null)
	 */
	public IDisplayString toDisplay(Object argument) {

		return new I18nKeyDisplayString(this, argument);
	}

	@Override
	protected I18n1 getThis() {

		return this;
	}
}

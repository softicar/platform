package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.i18n.key.AbstractI18nKey;
import com.softicar.platform.common.core.i18n.key.II18nKey;

/**
 * Represents a parameterized {@link II18nKey}.
 *
 * @author Oliver Richers
 */
public final class I18n2 extends AbstractI18nKey<I18n2> {

	public I18n2(String keyString) {

		super(keyString);
	}

	/**
	 * Converts this {@link II18nKey}.
	 *
	 * @param argument1
	 *            formatting parameter
	 * @param argument2
	 *            formatting parameter
	 * @return the {@link IDisplayString} (never null)
	 */
	public IDisplayString toDisplay(Object argument1, Object argument2) {

		return new I18nKeyDisplayString(this, argument1, argument2);
	}

	@Override
	protected I18n2 getThis() {

		return this;
	}
}

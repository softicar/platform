package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.i18n.key.AbstractI18nKey;
import com.softicar.platform.common.core.i18n.key.II18nKey;

/**
 * Represents a parameterized {@link II18nKey}.
 *
 * @author Oliver Richers
 */
public final class I18n3 extends AbstractI18nKey<I18n3> {

	public I18n3(String keyString) {

		super(keyString);
	}

	/**
	 * Converts this {@link II18nKey}.
	 *
	 * @param argument1
	 *            formatting parameter
	 * @param argument2
	 *            formatting parameter
	 * @param argument3
	 *            formatting parameter
	 * @return the {@link IDisplayString} (never null)
	 */
	public IDisplayString toDisplay(Object argument1, Object argument2, Object argument3) {

		return new I18nKeyDisplayString(this, argument1, argument2, argument3);
	}

	@Override
	protected I18n3 getThis() {

		return this;
	}
}

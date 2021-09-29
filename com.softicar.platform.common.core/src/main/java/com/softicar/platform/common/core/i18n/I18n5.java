package com.softicar.platform.common.core.i18n;

import com.softicar.platform.common.core.i18n.key.AbstractI18nKey;
import com.softicar.platform.common.core.i18n.key.II18nKey;

/**
 * Represents a parameterized {@link II18nKey}.
 *
 * @author Oliver Richers
 */
public final class I18n5 extends AbstractI18nKey<I18n5> {

	public I18n5(String keyString) {

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
	 * @param argument4
	 *            formatting parameter
	 * @param argument5
	 *            formatting parameter
	 * @return the {@link IDisplayString} (never null)
	 */
	public IDisplayString toDisplay(Object argument1, Object argument2, Object argument3, Object argument4, Object argument5) {

		return new I18nKeyDisplayString(this, argument1, argument2, argument3, argument4, argument5);
	}

	@Override
	protected I18n5 getThis() {

		return this;
	}
}

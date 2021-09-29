package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;

public class PasswordDigitCharacterCriterion extends AbstractPasswordCharacterCriterion {

	public PasswordDigitCharacterCriterion(int minimumCount) {

		super(minimumCount);
	}

	@Override
	public IDisplayString getDescription() {

		return CoreI18n.THE_PASSWORD_CONTAINS_AT_LEAST_ARG1_DIGITS.toDisplay(minimumCount);
	}

	@Override
	protected boolean isMatchingCharacter(char character) {

		return Character.isDigit(character);
	}
}

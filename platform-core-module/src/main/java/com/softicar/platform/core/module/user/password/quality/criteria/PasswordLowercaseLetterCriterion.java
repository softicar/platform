package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;

public class PasswordLowercaseLetterCriterion extends AbstractPasswordCharacterCriterion {

	public PasswordLowercaseLetterCriterion(int minimumCount) {

		super(minimumCount);
	}

	@Override
	public IDisplayString getDescription() {

		return CoreI18n.THE_PASSWORD_CONTAINS_AT_LEAST_ARG1_LOWERCASE_LETTERS.toDisplay(minimumCount);
	}

	@Override
	protected boolean isMatchingCharacter(char character) {

		return Character.isLowerCase(character);
	}
}

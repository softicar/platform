package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;

public class PasswordUppercaseLetterCriterion extends AbstractPasswordCharacterCriterion {

	public PasswordUppercaseLetterCriterion(int minimumCount) {

		super(minimumCount);
	}

	@Override
	public IDisplayString getDescription() {

		return CoreI18n.THE_PASSWORD_CONTAINS_AT_LEAST_ARG1_UPPERCASE_LETTERS.toDisplay(minimumCount);
	}

	@Override
	protected boolean isMatchingCharacter(char character) {

		return Character.isUpperCase(character);
	}
}

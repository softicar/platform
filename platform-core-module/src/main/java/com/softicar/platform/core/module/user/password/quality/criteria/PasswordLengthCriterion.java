package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.password.quality.UserPasswordQuality;

public class PasswordLengthCriterion implements IPasswordQualityCriterion {

	private final int minimumLength;

	public PasswordLengthCriterion(int minimumLength) {

		this.minimumLength = minimumLength;
	}

	@Override
	public IDisplayString getDescription() {

		return CoreI18n.THE_PASSWORD_HAS_A_MINIMUM_LENGTH_OF_ARG1_CHARACTERS.toDisplay(minimumLength);
	}

	@Override
	public boolean isFulfilled(String password) {

		return password.length() >= minimumLength;
	}

	@Override
	public UserPasswordQuality getQuality(String password) {

		return new UserPasswordQuality(password.length() / (double) minimumLength);
	}
}

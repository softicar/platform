package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.password.quality.UserPasswordQuality;

public class PasswordOnlyAsciiCharactersCriterion implements IPasswordQualityCriterion {

	@Override
	public IDisplayString getDescription() {

		return CoreI18n.THE_PASSWORD_CONTAINS_ONLY_ASCII_CHARACTERS;
	}

	@Override
	public boolean isFulfilled(String password) {

		for (int i = 0; i < password.length(); ++i) {
			if (password.charAt(i) >= 128) {
				return false;
			}
		}
		return true;
	}

	@Override
	public UserPasswordQuality getQuality(String password) {

		return new UserPasswordQuality(isFulfilled(password)? 1 : 0);
	}
}

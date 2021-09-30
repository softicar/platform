package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.password.quality.UserPasswordQuality;

public class PasswordIdenticalConsecutiveCharactersCriterion implements IPasswordQualityCriterion {

	private final int maximumConsecutiveCharacters;

	public PasswordIdenticalConsecutiveCharactersCriterion(int maximumConsecutiveCharacters) {

		this.maximumConsecutiveCharacters = maximumConsecutiveCharacters;

		if (maximumConsecutiveCharacters < 1) {
			throw new SofticarDeveloperException("Parameter must be equal or greater than 1.");
		}
	}

	@Override
	public IDisplayString getDescription() {

		return CoreI18n.THE_PASSWORD_CONTAINS_NO_GROUP_OF_MORE_THAN_ARG1_CONSECUTIVE_IDENTICAL_CHARACTERS.toDisplay(maximumConsecutiveCharacters);
	}

	@Override
	public boolean isFulfilled(String password) {

		return getMaximumConsecutiveCharacters(password) <= maximumConsecutiveCharacters;
	}

	@Override
	public UserPasswordQuality getQuality(String password) {

		return new UserPasswordQuality(maximumConsecutiveCharacters / (double) getMaximumConsecutiveCharacters(password));
	}

	private int getMaximumConsecutiveCharacters(String password) {

		int start = 0;
		int maximum = 0;
		for (int index = 1; index < password.length(); ++index) {
			char previousCharacter = password.charAt(index - 1);
			char currentCharacter = password.charAt(index);
			if (currentCharacter == previousCharacter) {
				int length = index + 1 - start;
				if (length > maximum) {
					maximum = length;
				} else {
					// still okay -> go on
				}
			} else {
				start = index;
			}
		}
		return maximum;
	}
}

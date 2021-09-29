package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.password.quality.UserPasswordQuality;
import java.util.Set;
import java.util.TreeSet;

public class PasswordDistinctCharactersCriterion implements IPasswordQualityCriterion {

	private final int minimumCount;

	public PasswordDistinctCharactersCriterion(int minimumCount) {

		this.minimumCount = minimumCount;

		if (minimumCount < 1) {
			throw new SofticarDeveloperException("Parameter must be equal or greater than 1.");
		}
	}

	@Override
	public IDisplayString getDescription() {

		return CoreI18n.THE_PASSWORD_CONTAINS_AT_LEAST_ARG1_DISTINCT_CHARACTERS.toDisplay(minimumCount);
	}

	@Override
	public boolean isFulfilled(String password) {

		return getDistinctCharacterCount(password) >= minimumCount;
	}

	@Override
	public UserPasswordQuality getQuality(String password) {

		return new UserPasswordQuality(getDistinctCharacterCount(password) / (double) minimumCount);
	}

	private int getDistinctCharacterCount(String password) {

		Set<Character> distinctCharacters = new TreeSet<>();
		for (int index = 0; index < password.length(); ++index) {
			distinctCharacters.add(password.charAt(index));
		}
		return distinctCharacters.size();
	}
}

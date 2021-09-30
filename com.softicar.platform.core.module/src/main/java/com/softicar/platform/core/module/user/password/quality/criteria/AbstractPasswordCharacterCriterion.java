package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.core.module.user.password.quality.UserPasswordQuality;

public abstract class AbstractPasswordCharacterCriterion implements IPasswordQualityCriterion {

	protected final int minimumCount;

	public AbstractPasswordCharacterCriterion(int minimumCount) {

		this.minimumCount = minimumCount;
	}

	@Override
	public boolean isFulfilled(String password) {

		return getMatchingCharacterCount(password) >= minimumCount;
	}

	@Override
	public UserPasswordQuality getQuality(String password) {

		return new UserPasswordQuality(getMatchingCharacterCount(password) / (double) minimumCount);
	}

	protected abstract boolean isMatchingCharacter(char character);

	private int getMatchingCharacterCount(String password) {

		int count = 0;
		for (int i = 0; i < password.length(); ++i) {
			if (isMatchingCharacter(password.charAt(i))) {
				++count;
			}
		}
		return count;
	}
}

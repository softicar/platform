package com.softicar.platform.core.module.user.password.policy;

import com.softicar.platform.core.module.user.password.quality.criteria.ComposedPasswordQualityCriterion;
import com.softicar.platform.core.module.user.password.quality.criteria.PasswordDigitCharacterCriterion;
import com.softicar.platform.core.module.user.password.quality.criteria.PasswordDistinctCharactersCriterion;
import com.softicar.platform.core.module.user.password.quality.criteria.PasswordLengthCriterion;
import com.softicar.platform.core.module.user.password.quality.criteria.PasswordLowercaseLetterCriterion;
import com.softicar.platform.core.module.user.password.quality.criteria.PasswordOnlyAsciiCharactersCriterion;
import com.softicar.platform.core.module.user.password.quality.criteria.PasswordSpecialCharacterCriterion;
import com.softicar.platform.core.module.user.password.quality.criteria.PasswordUppercaseLetterCriterion;

public class SofticarPasswordPolicy extends PasswordPolicy {

	public static final int MINIMUM_PASSWORD_LENGTH = 10;
	public static final int MINIMUM_DIGIT_CHARACTERS = 1;
	public static final int MINIMUM_DISTINCT_CHARACTERS = 7;
	public static final int MINIMUM_LOWERCASE_LETTERS = 1;
	public static final int MINIMUM_SPECIAL_CHARACTERS = 1;
	public static final int MINIMUM_SUB_CRITERIA_COUNT = 3;
	public static final int MINIMUM_UPPERCASE_LETTERS = 1;

	private static final SofticarPasswordPolicy SINGLETON = new SofticarPasswordPolicy();

	public SofticarPasswordPolicy() {

		addQualityCriterion(new PasswordLengthCriterion(MINIMUM_PASSWORD_LENGTH));
		addQualityCriterion(new PasswordDistinctCharactersCriterion(MINIMUM_DISTINCT_CHARACTERS));
		addQualityCriterion(new PasswordOnlyAsciiCharactersCriterion());

		ComposedPasswordQualityCriterion criterion = new ComposedPasswordQualityCriterion();
		criterion.addSubCriterion(new PasswordLowercaseLetterCriterion(MINIMUM_LOWERCASE_LETTERS));
		criterion.addSubCriterion(new PasswordUppercaseLetterCriterion(MINIMUM_UPPERCASE_LETTERS));
		criterion.addSubCriterion(new PasswordDigitCharacterCriterion(MINIMUM_DIGIT_CHARACTERS));
		criterion.addSubCriterion(new PasswordSpecialCharacterCriterion(MINIMUM_SPECIAL_CHARACTERS));
		criterion.setMinimumSubCriteriaCount(MINIMUM_SUB_CRITERIA_COUNT);
		addQualityCriterion(criterion);
	}

	public static SofticarPasswordPolicy get() {

		return SINGLETON;
	}
}

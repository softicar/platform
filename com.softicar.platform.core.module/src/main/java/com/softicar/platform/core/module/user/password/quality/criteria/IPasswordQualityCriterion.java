package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.user.password.quality.UserPasswordQuality;

public interface IPasswordQualityCriterion {

	/**
	 * Returns a description of this criterion.
	 *
	 * @return criterion description
	 */
	IDisplayString getDescription();

	/**
	 * Returns true if any only if the given passwords fulfills this criterion.
	 *
	 * @param password
	 *            the password to check
	 * @return whether password fulfills this criterion
	 */
	boolean isFulfilled(String password);

	/**
	 * Returns an informal quality score for the given password.
	 *
	 * @param password
	 *            the password to check
	 * @return the password quality
	 */
	UserPasswordQuality getQuality(String password);
}

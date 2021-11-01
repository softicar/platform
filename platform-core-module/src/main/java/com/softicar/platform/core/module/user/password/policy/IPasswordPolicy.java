package com.softicar.platform.core.module.user.password.policy;

import com.softicar.platform.core.module.user.password.quality.UserPasswordQuality;
import com.softicar.platform.core.module.user.password.quality.criteria.IPasswordQualityCriterion;
import java.util.Collection;

public interface IPasswordPolicy {

	Collection<IPasswordQualityCriterion> getQualityCriteria();

	/**
	 * Returns true if any only if the given passwords fulfills this policy.
	 *
	 * @param password
	 *            the password to check
	 * @return whether password fulfills this policy
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

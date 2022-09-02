package com.softicar.platform.core.module.user.password;

import com.softicar.platform.core.module.user.AGUser;

/**
 * Loader class for user passwords.
 * <p>
 * This class is used by {@link AGUser#getActivePassword()} and should not be
 * used directly.
 *
 * @author Oliver Richers
 */
public class UserPasswordLoader {

	private final AGUser user;

	public UserPasswordLoader(AGUser user) {

		this.user = user;
	}

	/**
	 * Loads the active password of the user.
	 * <p>
	 * If the user has no active password, this method returns null. If the user
	 * has more than one password, this method returns the active password,
	 * which was created last.
	 *
	 * @return the active password or null if none exists
	 */
	public AGUserPassword loadActivePassword() {

		return AGUserPassword
			.createSelect()
			.where(AGUserPassword.USER.isEqual(user))
			.where(AGUserPassword.ACTIVE.isEqual(true))
			.orderDescendingBy(AGUserPassword.CREATED_AT)
			.getFirst();
	}
}

package com.softicar.platform.common.core.user;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * References the currently active {@link IBasicUser}.
 *
 * @author Oliver Richers
 */
public class CurrentBasicUser {

	private static final Singleton<IBasicUser> VALUE = new Singleton<IBasicUser>().setInheritByIdentity();

	/**
	 * Returns the currently active {@link IBasicUser} or null.
	 *
	 * @return the current user (may be null)
	 */
	public static IBasicUser get() {

		return VALUE.get();
	}

	public static void set(IBasicUser user) {

		VALUE.set(user);
	}
}

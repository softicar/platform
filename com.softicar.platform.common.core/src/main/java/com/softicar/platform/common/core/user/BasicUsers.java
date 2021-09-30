package com.softicar.platform.common.core.user;

import java.util.Objects;

/**
 * Utility methods for {@link IBasicUser}.
 *
 * @author Oliver Richers
 */
interface BasicUsers {

	/**
	 * Compares the two given users for identity.
	 *
	 * @param userA
	 *            the first user (may be null)
	 * @param userB
	 *            the second user (may be null)
	 * @return <i>true</i> if both users are identical; <i>false</i> otherwise
	 */
	static boolean isSame(IBasicUser userA, IBasicUser userB) {

		if (userA == null || userB == null) {
			return false;
		} else {
			Integer idA = userA.getId();
			Integer idB = userB.getId();
			return Objects.equals(idA, idB) && idA != null;
		}
	}
}

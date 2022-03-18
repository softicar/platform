package com.softicar.platform.common.core.user;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.locale.ILocale;

/**
 * Represents a basic user of the system.
 * <p>
 * This interface defines the minimum contract for a user object.
 *
 * @author Oliver Richers
 */
public interface IBasicUser extends IEntity {

	/**
	 * Returns the unique ID of this user.
	 *
	 * @return the user ID (never null)
	 */
	@Override
	Integer getId();

	/**
	 * Returns the formal login name of this user account.
	 * <p>
	 * Every user must have a unique login name.
	 *
	 * @return the user login name (never null)
	 */
	String getLoginName();

	/**
	 * Returns whether this is a super-user or not.
	 * <p>
	 * This flag can be used for simple authorization cases. A super user can be
	 * regarded as the most trusted user and may usually do everything. It is
	 * comparable to the <i>root</i> user on a <i>Unix</i> system.
	 *
	 * @return <i>true</i> if this is a super user, <i>false</i> otherwise
	 *         (never null)
	 */
	Boolean isSuperUser();

	/**
	 * Returns the {@link ILocale} of this user.
	 *
	 * @return the user {@link ILocale} (never <i>null</i>)
	 */
	ILocale getLocale();

	/**
	 * Compares this user to the given user.
	 *
	 * @param other
	 *            the other user (may be null)
	 * @return <i>true</i> if this user and the given user are identical;
	 *         <i>false</i> otherwise
	 */
	default boolean is(IBasicUser other) {

		return BasicUsers.isSame(this, other);
	}
}

package com.softicar.platform.core.module.user;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.runtime.table.row.DbTableRowProxy;

/**
 * References the currently active {@link AGUser}.
 * <p>
 * This class is an extension to {@link CurrentBasicUser} and is implemented in
 * terms of it, that is, it has no internal state on its own.
 *
 * @author Oliver Richers
 */
public class CurrentUser {

	/**
	 * Returns the currently active user.
	 * <p>
	 * This method calls {@link CurrentBasicUser#get} internally.
	 *
	 * @return the current user, never null
	 */
	public static AGUser get() {

		IBasicUser basicUser = CurrentBasicUser.get();
		if (basicUser != null) {
			return AGUser.get(basicUser.getId());
		} else {
			return AGUser.getSystemUser();
		}
	}

	/**
	 * Sets the current user by calling {@link CurrentBasicUser#set}.
	 *
	 * @param user
	 *            the user to set as current user (never <i>null</i>)
	 */
	public static void set(AGUser user) {

		if (user.impermanent()) {
			throw new IllegalArgumentException("Current user object must be permanent, but was not.");
		}

		CurrentBasicUser.set(new UserProxy(user));
	}

	/**
	 * Effectively sets the {@link CurrentBasicUser} to <i>null</i>.
	 */
	public static void reset() {

		CurrentBasicUser.set(null);
	}

	private static class UserProxy extends DbTableRowProxy<AGUser, Integer> implements IBasicUser {

		public UserProxy(AGUser user) {

			super(user);
		}

		@Override
		public IDisplayString toDisplayWithoutId() {

			return get().toDisplayWithoutId();
		}

		@Override
		public Integer getId() {

			return get().getId();
		}

		@Override
		public String getLoginName() {

			return get().getLoginName();
		}

		@Override
		public Boolean isSuperUser() {

			return get().isSuperUser();
		}

		@Override
		public ILocale getLocale() {

			return get().getLocale();
		}

		@Override
		public int compareTo(IBasicItem other) {

			return get().compareTo(other);
		}
	}
}

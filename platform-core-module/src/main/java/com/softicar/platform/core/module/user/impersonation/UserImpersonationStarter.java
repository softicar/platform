package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;

/**
 * Initiates a user impersonation.
 *
 * @author Oliver Richers
 */
class UserImpersonationStarter {

	private final AGUser impersonatedUser;
	private final String rationale;

	public UserImpersonationStarter(AGUser impersonatedUser, String rationale) {

		this.impersonatedUser = impersonatedUser;
		this.rationale = rationale;
	}

	/**
	 * Validates and starts the impersonation.
	 * <p>
	 * An {@link AGUserImpersonationState} entry is created and a notification
	 * is sent to the impersonated user if necessary.
	 *
	 * @return the inserted {@link AGUserImpersonationState} (never null)
	 */
	public AGUserImpersonationState start() {

		try (DbTransaction transaction = new DbTransaction()) {
			if (!CurrentUser.get().isSuperUser()) {
				throw new SofticarUserException(CoreI18n.YOU_ARE_NOT_ALLOWED_TO_IMPERSONATE_ANOTHER_USER);
			}

			if (impersonatedUser.isSuperUser()) {
				throw new SofticarUserException(CoreI18n.SUPER_USERS_CANNOT_BE_IMPERSONATED);
			}

			informImpersonatedUser();

			AGUserImpersonationState state = insertImpersonationState();
			transaction.commit();
			return state;
		}
	}

	private void informImpersonatedUser() {

		new UserImpersonationNotifier(impersonatedUser, rationale).notifyUser();
	}

	private AGUserImpersonationState insertImpersonationState() {

		return new AGUserImpersonationState()//
			.setSourceUser(CurrentUser.get())
			.setTargetUser(impersonatedUser)
			.setReason(rationale)
			.setStartedAt(DayTime.now())
			.save();
	}
}

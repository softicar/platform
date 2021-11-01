package com.softicar.platform.core.module.user.pseudonymization;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.AGUserLog;
import com.softicar.platform.db.core.transaction.DbTransaction;

public class UserPseudonymizer {

	private final AGUser user;
	private final String pseudonym;

	public UserPseudonymizer(AGUser user, String pseudonym) {

		this.user = user;
		this.pseudonym = pseudonym;
	}

	public void pseudonymize() {

		try (DbTransaction transaction = new DbTransaction()) {
			user.reloadForUpdate();
			assertUserIsInactive();

			user//
				.setLoginName(getPseudoLoginName())
				.setFirstName(getPseudoFirstName())
				.setLastName(pseudonym)
				.setEmailAddress("")
				.save();

			AGUserLog.TABLE//
				.createSelect()
				.where(AGUserLog.USER.isEqual(user))
				.forEach(this::pseudonymizeLog);

			transaction.commit();
		}
	}

	private void assertUserIsInactive() {

		if (user.isActive()) {
			throw new SofticarUserException(CoreI18n.THE_USER_MUST_NOT_BE_ACTIVE);
		}
	}

	private void pseudonymizeLog(AGUserLog userLog) {

		userLog//
			.setLoginName(getPseudoLoginName())
			.setFirstName(getPseudoFirstName())
			.setLastName(pseudonym)
			.setEmailAddress("")
			.save();
	}

	private String getPseudoLoginName() {

		return "pseudo" + user.getId();
	}

	private String getPseudoFirstName() {

		return CoreI18n.PSEUDONYMIZED_USER.toEnglish();
	}
}

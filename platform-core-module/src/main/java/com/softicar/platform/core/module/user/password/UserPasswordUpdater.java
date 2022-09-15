package com.softicar.platform.core.module.user.password;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.security.crypt.Bcrypt;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.password.algorithm.AGUserPasswordAlgorithmEnum;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.db.core.transaction.DbTransaction;

public class UserPasswordUpdater {

	private final AGUser user;
	private final String password;

	public UserPasswordUpdater(AGUser user, String password) {

		this.user = user;
		this.password = password;
	}

	public void update() {

		if (!SofticarPasswordPolicy.get().isFulfilled(password)) {
			throw new SofticarUserException(CoreI18n.THE_PASSWORD_DOES_NOT_FULFILL_THE_REQUIRED_SECURITY_CRITERIA);
		}

		updatePasswordInDatabase();
		user.setActive(true);
		user.save();
	}

	public AGUserPassword updatePasswordInDatabase() {

		AGUserPassword password = null;
		try (DbTransaction transaction = new DbTransaction()) {

			// disable all currently active passwords of the user
			disableAllPasswords();

			// insert and activate the new password
			password = insertPassword();

			transaction.commit();
		}
		return password;
	}

	private void disableAllPasswords() {

		for (AGUserPassword userPassword: AGUserPassword
			.createSelect()
			.where(AGUserPassword.USER.isEqual(user))
			.where(AGUserPassword.ACTIVE.isEqual(true))
			.list()) {
			userPassword.setActive(false);
			userPassword.save();
		}
	}

	private AGUserPassword insertPassword() {

		return new AGUserPassword()//
			.setActive(true)
			.setUser(user)
			.setAlgorithm(AGUserPasswordAlgorithmEnum.BCRYPT.getRecord())
			.setEncryptedPassword(Bcrypt.encryptPassword(password))
			.setCreatedAt(DayTime.now())
			.save();
	}
}

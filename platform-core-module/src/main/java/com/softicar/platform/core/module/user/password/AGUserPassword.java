package com.softicar.platform.core.module.user.password;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.security.crypt.Apr1Crypt;
import com.softicar.platform.common.security.crypt.Bcrypt;
import com.softicar.platform.common.security.crypt.UnixCrypt;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.password.algorithm.AGUserPasswordAlgorithmEnum;
import com.softicar.platform.emf.object.IEmfObject;

public class AGUserPassword extends AGUserPasswordGenerated implements IEmfObject<AGUserPassword> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return CoreI18n.PASSWORD_OF_ARG1.toDisplay(getUser().toDisplayWithoutId());
	}

	public boolean verifyPassword(String password) {

		AGUserPasswordAlgorithmEnum algorithm = getThis().getAlgorithm().getEnum();
		switch (algorithm) {
		case BCRYPT:
			return Bcrypt.verifyPassword(password, getThis().getEncryptedPassword());
		case UNIX_CRYPT:
			return UnixCrypt.verifyPassword(password, getThis().getEncryptedPassword());
		case APR_1:
			return Apr1Crypt.verifyPassword(password, getThis().getEncryptedPassword());
		}
		throw new SofticarUnknownEnumConstantException(algorithm);
	}

	public static AGUserPassword getActive(AGUser user) {

		return AGUserPassword.TABLE//
			.createSelect()
			.where(AGUserPassword.USER.equal(user))
			.where(AGUserPassword.ACTIVE.equal(true))
			.getFirst();
	}
}

package com.softicar.platform.core.module.user.password;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.security.crypt.Apr1Crypt;
import com.softicar.platform.common.security.crypt.Bcrypt;
import com.softicar.platform.common.security.crypt.UnixCrypt;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.password.algorithm.AGUserPasswordAlgorithmEnum;
import com.softicar.platform.core.module.user.password.policy.AGPasswordPolicy;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Optional;

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

	public boolean isValid() {

		Optional<Integer> maxAllowedAge = Optional.ofNullable(getUser().getPasswordPolicy()).map(AGPasswordPolicy::getMaximumPasswordAge);
		if (maxAllowedAge.isPresent()) {
			return DayTime.now().isBeforeOrEqual(getCreatedAt().plusDays(maxAllowedAge.get()));
		} else {
			return true;
		}
	}

	public static AGUserPassword getActive(AGUser user) {

		return AGUserPassword.TABLE//
			.createSelect()
			.where(AGUserPassword.USER.isEqual(user))
			.where(AGUserPassword.ACTIVE.isEqual(true))
			.getFirst();
	}
}

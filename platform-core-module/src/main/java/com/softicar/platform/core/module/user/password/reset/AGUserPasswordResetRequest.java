package com.softicar.platform.core.module.user.password.reset;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Optional;

public class AGUserPasswordResetRequest extends AGUserPasswordResetRequestGenerated {

	public static Optional<AGUserPasswordResetRequest> loadByUuidAsOptional(String uuid) {

		return AGUserPasswordResetRequest.TABLE//
			.createSelect()
			.where(AGUserPasswordResetRequest.UUID.isEqual(uuid))
			.where(AGUserPasswordResetRequest.CREATED_AT.isGreater(DayTime.now().getYesterday()))
			.where(AGUserPasswordResetRequest.ACTIVE)
			.getOneAsOptional();
	}

	public static boolean isResetLimitReachedForUser(AGUser user) {

		return AGUserPasswordResetRequest.TABLE//
			.createSelect()
			.where(AGUserPasswordResetRequest.CREATED_AT.isGreater(DayTime.now().getYesterday()))
			.where(AGUserPasswordResetRequest.USER.isEqual(user))
			.count() >= 3;
	}
}

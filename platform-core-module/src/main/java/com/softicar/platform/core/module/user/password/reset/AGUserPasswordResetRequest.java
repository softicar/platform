package com.softicar.platform.core.module.user.password.reset;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Optional;

public class AGUserPasswordResetRequest extends AGUserPasswordResetRequestGenerated {

	private static final int VALID_REQUEST_TIME_LIMIT_IN_SECONDS = 28800; //8 hours
	private static final int REQUEST_LIMIT_PER_8_HOURS = 3;

	public static Optional<AGUserPasswordResetRequest> loadByUuidAsOptional(String uuid) {

		return AGUserPasswordResetRequest.TABLE//
			.createSelect()
			.where(AGUserPasswordResetRequest.UUID.isEqual(uuid))
			.where(AGUserPasswordResetRequest.CREATED_AT.isGreater(DayTime.now().minusSeconds(VALID_REQUEST_TIME_LIMIT_IN_SECONDS)))
			.where(AGUserPasswordResetRequest.ACTIVE)
			.getOneAsOptional();
	}

	public static boolean isResetLimitReachedForUser(AGUser user) {

		return AGUserPasswordResetRequest.TABLE//
			.createSelect()
			.where(AGUserPasswordResetRequest.CREATED_AT.isGreater(DayTime.now().minusSeconds(VALID_REQUEST_TIME_LIMIT_IN_SECONDS)))
			.where(AGUserPasswordResetRequest.USER.isEqual(user))
			.count() >= REQUEST_LIMIT_PER_8_HOURS;
	}
}

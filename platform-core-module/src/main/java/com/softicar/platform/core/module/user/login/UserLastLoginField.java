package com.softicar.platform.core.module.user.login;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.transients.AbstractTransientIntegerField;
import com.softicar.platform.db.runtime.transients.IValueAccumulator;
import com.softicar.platform.db.sql.Sql;
import java.util.Set;

public class UserLastLoginField extends AbstractTransientIntegerField<AGUser> {

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.DAYS_SINCE_LAST_LOGIN;
	}

	@Override
	protected void loadValues(Set<AGUser> users, IValueAccumulator<AGUser, Integer> accumulator) {

		Sql//
			.from(AGUserLoginLog.TABLE)
			.select(AGUserLoginLog.USER)
			.select(AGUserLoginLog.LOGIN_AT.max())
			.where(AGUserLoginLog.USER.isIn(users))
			.groupBy(AGUserLoginLog.USER)
			.forEach(record -> accumulator.add(record.get0(), Day.today().minus(record.get1().getDay())));
	}
}

package com.softicar.platform.core.module.user.session;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Duration;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Date;
import javax.servlet.http.HttpSession;

public class UserSessionsTable extends AbstractInMemoryDataTable<HttpSession> {

	private final IDataTableColumn<HttpSession, AGUser> userColumn;

	public UserSessionsTable() {

		this.userColumn = newColumn(AGUser.class)//
			.setGetter(this::getUserFromSession)
			.setTitle(CoreI18n.USER)
			.addColumn();
		newColumn(String.class)//
			.setGetter(HttpSession::getId)
			.setTitle(CoreI18n.SESSION_ID)
			.addColumn();
		newColumn(DayTime.class)//
			.setGetter(session -> convertToDayTime(session.getCreationTime()))
			.setTitle(CoreI18n.CREATED_AT)
			.addColumn();
		newColumn(Duration.class)//
			.setGetter(session -> getElapsedTime(session.getCreationTime()))
			.setTitle(CoreI18n.AGE)
			.addColumn();
		newColumn(DayTime.class)//
			.setGetter(session -> convertToDayTime(session.getLastAccessedTime()))
			.setTitle(CoreI18n.LAST_ACCESS)
			.addColumn();
		newColumn(Duration.class)//
			.setGetter(session -> getElapsedTime(session.getLastAccessedTime()))
			.setTitle(CoreI18n.INACTIVE_FOR)
			.addColumn();
	}

	public IDataTableColumn<HttpSession, AGUser> getUserColumn() {

		return userColumn;
	}

	@Override
	protected Iterable<HttpSession> getTableRows() {

		return new UserSessionManager().getAllSessions();
	}

	private AGUser getUserFromSession(HttpSession session) {

		return SofticarAjaxSession//
			.getInstance(session)
			.map(SofticarAjaxSession::getUser)
			.orElse(null);
	}

	private DayTime convertToDayTime(long time) {

		return DayTime.fromDate(new Date(time));
	}

	private Duration getElapsedTime(long millis) {

		return convertToDayTime(millis).getDuration(DayTime.now());
	}
}

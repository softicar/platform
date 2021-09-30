package com.softicar.platform.ajax.document;

import com.softicar.platform.common.date.DayTime;

public class AjaxDocumentLogs implements IAjaxDocumentLogs {

	private DayTime lastAccess;

	public AjaxDocumentLogs() {

		this.lastAccess = DayTime.now();
	}

	@Override
	public DayTime getLastAccess() {

		return lastAccess;
	}

	@Override
	public void setLastAccess(DayTime lastAccess) {

		this.lastAccess = lastAccess;
	}

	@Override
	public void updateLastAccess() {

		this.lastAccess = DayTime.now();
	}

	@Override
	public final double getIdleSeconds() {

		long access = getLastAccess().toMillis();
		long now = DayTime.now().toMillis();
		long idle = now - access;

		return idle > 0? idle / 1000.0 : 0.0;
	}
}

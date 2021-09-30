package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Duration;

class OneTimePassword {

	protected static final int MAXIMUM_AGE_IN_SECONDS = 5 * 60;
	private final int index;
	private final String text;
	private final DayTime creationDate;

	public OneTimePassword(int index, String text, DayTime creationDate) {

		this.index = index;
		this.text = text;
		this.creationDate = creationDate;
	}

	public int getIndex() {

		return index;
	}

	public String getText() {

		return text;
	}

	public boolean isExpired() {

		return new Duration(creationDate, DayTime.now()).getTotalSeconds() > MAXIMUM_AGE_IN_SECONDS;
	}
}

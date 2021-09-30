package com.softicar.platform.core.module.cron.element;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;

public class CronEmptyElement implements ICronElement {

	@Override
	public boolean test(int value) {

		throw new MissingCronElementException();
	}

	@Override
	public boolean isInRange(int min, int max) {

		throw new MissingCronElementException();
	}

	@Override
	public String toString() {

		return "";
	}

	private class MissingCronElementException extends SofticarUserException {

		public MissingCronElementException() {

			super(CoreI18n.EMPTY_CRON_ELEMENT);
		}
	}
}

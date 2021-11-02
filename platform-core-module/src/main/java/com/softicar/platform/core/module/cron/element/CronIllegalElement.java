package com.softicar.platform.core.module.cron.element;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;

public class CronIllegalElement implements ICronElement {

	private final String elementString;

	public CronIllegalElement(String elementString) {

		this.elementString = elementString;
	}

	@Override
	public boolean test(int value) {

		throw new IllegalCronElementFormatException(elementString);
	}

	@Override
	public boolean isInRange(int min, int max) {

		throw new IllegalCronElementFormatException(elementString);
	}

	@Override
	public String toString() {

		return elementString;
	}

	private class IllegalCronElementFormatException extends SofticarUserException {

		public IllegalCronElementFormatException(String elementString) {

			super(CoreI18n.ILLEGAL_CRON_ELEMENT_FORMAT_ARG1.toDisplay(elementString));
		}
	}
}

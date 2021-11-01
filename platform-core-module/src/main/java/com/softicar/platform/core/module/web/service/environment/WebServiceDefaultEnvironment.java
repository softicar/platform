package com.softicar.platform.core.module.web.service.environment;

import com.softicar.platform.common.date.ISOCalendar;

public class WebServiceDefaultEnvironment implements IWebServiceEnvironment {

	@Override
	public void setupEnvironment() {

		ISOCalendar.setDefaultTimeZone();
	}

	@Override
	public void cleanupEnvironment() {

		// nothing to do
	}
}

package com.softicar.platform.core.module.environment;

import com.softicar.platform.common.date.DayTime;

/**
 * Checks a given {@link DayTime} to be in the scheduled down-time of the DBMS
 * of the live system.
 *
 * @author Oliver Richers
 */
public class LiveSystemDbmsDownTimeChecker {

	private final AGDbmsConfiguration configuration;
	private final DayTime dayTime;

	public LiveSystemDbmsDownTimeChecker(AGDbmsConfiguration configuration, DayTime dayTime) {

		this.configuration = configuration;
		this.dayTime = dayTime;
	}

	public boolean isDownTime() {

		return isMatchingWeekday() && isMatchingTimeRange();
	}

	private boolean isMatchingWeekday() {

		return configuration.getDbmsDownTimeWeekday().is(dayTime.getDay().getWeekday());
	}

	private boolean isMatchingTimeRange() {

		return configuration.getDbmsDownTimeRange().contains(dayTime.getTime());
	}
}

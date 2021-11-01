package com.softicar.platform.core.module.cron;

import com.softicar.platform.core.module.cron.element.ICronElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CronExpressionBuilder {

	private Collection<ICronElement> minutes;
	private Collection<ICronElement> hours;
	private Collection<ICronElement> days;
	private Collection<ICronElement> months;
	private Collection<ICronElement> weekdays;
	private String surplusTokens;

	public CronExpressionBuilder() {

		this.minutes = new ArrayList<>();
		this.hours = new ArrayList<>();
		this.days = new ArrayList<>();
		this.months = new ArrayList<>();
		this.weekdays = new ArrayList<>();
		this.surplusTokens = null;
	}

	public Collection<ICronElement> getMinutes() {

		return minutes;
	}

	public CronExpressionBuilder setMinutes(List<ICronElement> minutes) {

		this.minutes = minutes;
		return this;
	}

	public Collection<ICronElement> getHours() {

		return hours;
	}

	public CronExpressionBuilder setHours(List<ICronElement> hours) {

		this.hours = hours;
		return this;
	}

	public Collection<ICronElement> getDays() {

		return days;
	}

	public CronExpressionBuilder setDays(List<ICronElement> days) {

		this.days = days;
		return this;
	}

	public Collection<ICronElement> getMonths() {

		return months;
	}

	public CronExpressionBuilder setMonths(List<ICronElement> months) {

		this.months = months;
		return this;
	}

	public Collection<ICronElement> getWeekdays() {

		return weekdays;
	}

	public CronExpressionBuilder setWeekdays(List<ICronElement> weekdays) {

		this.weekdays = weekdays;
		return this;
	}

	public Optional<String> getSurplusTokens() {

		return Optional.ofNullable(surplusTokens);
	}

	public CronExpressionBuilder setSurplusTokens(String surplusTokens) {

		this.surplusTokens = surplusTokens;
		return this;
	}

	public CronExpression build() {

		return new CronExpression(this);
	}
}

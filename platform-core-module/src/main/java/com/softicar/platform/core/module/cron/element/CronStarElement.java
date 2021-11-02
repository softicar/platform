package com.softicar.platform.core.module.cron.element;

public class CronStarElement implements ICronElement {

	private final int step;

	public CronStarElement() {

		this(1);
	}

	public CronStarElement(int step) {

		this.step = step;
	}

	@Override
	public boolean test(int value) {

		return value % step == 0;
	}

	@Override
	public boolean isInRange(int min, int max) {

		return true;
	}

	@Override
	public String toString() {

		if (step != 1) {
			return String.format("*/%s", step);
		} else {
			return "*";
		}
	}
}

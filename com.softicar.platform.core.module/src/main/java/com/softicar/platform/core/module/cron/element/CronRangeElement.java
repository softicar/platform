package com.softicar.platform.core.module.cron.element;

public class CronRangeElement implements ICronElement {

	private final int start;
	private final int end;
	private final int step;

	public CronRangeElement(int start, int end) {

		this(start, end, 1);
	}

	public CronRangeElement(int start, int end, int step) {

		if (step <= 0) {
			throw new IllegalArgumentException(String.format("Illegal value for step. Must be greater than 0: %s", step));
		}
		this.start = start;
		this.end = end;
		this.step = step;
	}

	@Override
	public boolean test(int value) {

		if (start <= end) {
			return value >= start && value <= end//
					&& (value + start) % step == 0;
		} else {
			return (value >= start || value <= end)//
					&& (value + start) % step == 0;
		}
	}

	@Override
	public boolean isInRange(int min, int max) {

		return start >= min && start <= max//
				&& end >= min && end <= max;
	}

	@Override
	public String toString() {

		if (step != 1) {
			return String.format("%s-%s/%s", start, end, step);
		} else {
			return String.format("%s-%s", start, end);
		}
	}
}

package com.softicar.platform.core.module.cron.element;

public class CronIndexElement implements ICronElement {

	private final int index;

	public CronIndexElement(Integer index) {

		this.index = index;
	}

	@Override
	public boolean test(int value) {

		return index == value;
	}

	@Override
	public boolean isInRange(int min, int max) {

		return index >= min && index <= max;
	}

	@Override
	public String toString() {

		return Integer.toString(index);
	}
}

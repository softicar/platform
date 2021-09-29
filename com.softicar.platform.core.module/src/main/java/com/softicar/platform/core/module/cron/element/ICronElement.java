package com.softicar.platform.core.module.cron.element;

public interface ICronElement {

	boolean test(int value);

	boolean isInRange(int min, int max);
}

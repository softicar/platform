package com.softicar.platform.core.module.date.weekday;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.CommonWeekdayI18n;
import com.softicar.platform.common.date.Weekday;
import java.util.Objects;

public class AGWeekday extends AGWeekdayGenerated implements IEntity {

	public static AGWeekday getByWeekday(Weekday weekday) {

		return TABLE.get(weekday.getIndex());
	}

	public Weekday getWeekday() {

		return Weekday.get(getId());
	}

	public boolean is(Weekday weekday) {

		return Objects.equals(getId(), weekday.getIndex());
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		switch (getEnum()) {
		case MONDAY:
			return CommonWeekdayI18n.MONDAY;
		case TUESDAY:
			return CommonWeekdayI18n.TUESDAY;
		case WEDNESDAY:
			return CommonWeekdayI18n.WEDNESDAY;
		case THURSDAY:
			return CommonWeekdayI18n.THURSDAY;
		case FRIDAY:
			return CommonWeekdayI18n.FRIDAY;
		case SATURDAY:
			return CommonWeekdayI18n.SATURDAY;
		case SUNDAY:
			return CommonWeekdayI18n.SUNDAY;
		}
		throw new SofticarUnknownEnumConstantException(getEnum());
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}
}

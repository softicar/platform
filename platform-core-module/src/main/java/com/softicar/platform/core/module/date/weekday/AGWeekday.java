package com.softicar.platform.core.module.date.weekday;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
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

		return getEnum().toDisplay();
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}
}

package com.softicar.platform.demo.module.core.module.moment;

import com.softicar.platform.emf.attribute.field.daytime.EmfDayTimeInput;

public class DemoMomentPointInTimeInput extends EmfDayTimeInput {

	private final AGDemoMoment moment;

	public DemoMomentPointInTimeInput(AGDemoMoment moment) {

		this.moment = moment;
	}

	@Override
	public void refreshInputConstraints() {

		if (moment.getDay() != null) {
			setValue(moment.getDay().toDayTime());
		}
	}
}

package com.softicar.platform.core.module.environment;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.math.Range;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.emf.trait.IEmfTrait;
import java.util.Optional;

public class AGDbmsConfiguration extends AGDbmsConfigurationGenerated implements IEmfTrait<AGDbmsConfiguration, AGCoreModuleInstance> {

	public static Optional<AGDbmsConfiguration> getInstance() {

		return Optional.ofNullable(AGDbmsConfiguration.TABLE.get(AGCoreModuleInstance.getInstance()));
	}

	public Range<Time> getDbmsDownTimeRange() {

		return new Range<>(getDbmsDownTimeBegin(), getDbmsDownTimeEnd());
	}

	public boolean isDbmsDownTime(DayTime dayTime) {

		return new LiveSystemDbmsDownTimeChecker(this, dayTime).isDownTime();
	}
}

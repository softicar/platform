package com.softicar.platform.core.module.environment;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.math.Range;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.emf.trait.IEmfTrait;
import java.util.Optional;

public class AGLiveSystemConfiguration extends AGLiveSystemConfigurationGenerated implements IEmfTrait<AGLiveSystemConfiguration, AGCoreModuleInstance> {

	public static Optional<AGLiveSystemConfiguration> getInstance() {

		return Optional.ofNullable(AGLiveSystemConfiguration.TABLE.get(AGCoreModuleInstance.getInstance()));
	}

	public Range<Time> getDbmsDownTimeRange() {

		return new Range<>(getDbmsDownTimeBegin(), getDbmsDownTimeEnd());
	}

	public boolean isDbmsDownTime(DayTime dayTime) {

		return new LiveSystemDbmsDownTimeChecker(this, dayTime).isDownTime();
	}

	public static String getSystemIdentifier() {

		return AGLiveSystemConfiguration.getInstance().map(AGLiveSystemConfiguration::getSystemName).orElse("");
	}
}

package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.cron.CronParser;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.UUID;

public class AGScheduledProgramExecution extends AGScheduledProgramExecutionGenerated implements IEmfObject<AGScheduledProgramExecution> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString
			.format(//
				"%s @ '%s'",
				Programs.getProgramName(getProgramUuid().getUuid()),
				getCronExpression());
	}

	public AGScheduledProgramExecution setProgramUuid(UUID programUuid) {

		return setProgramUuid(AGUuid.getOrCreate(programUuid));
	}

	public boolean isScheduleMatching(DayTime dayTime) {

		return CronParser.parse(getCronExpression()).matches(dayTime);
	}

	public void enqueueExecutionIfScheduleMatches(DayTime dayTime) {

		new ScheduledProgramEnqueuer(this, dayTime).enqueueExecution();
	}
}

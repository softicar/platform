package com.softicar.platform.core.module.standard.configuration;

import com.softicar.platform.core.module.configuration.AbstractStandardConfiguration;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.program.execution.scheduled.AGScheduledProgramExecution;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoint;
import com.softicar.platform.db.core.transaction.DbTransactions;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;

public class ProgramStandardConfiguration extends AbstractStandardConfiguration {

	@Override
	public void createAndSaveAll() {

		Programs//
			.getAllProgramsAsIndirectEntities()
			.stream()
			.map(AGUuidBasedSourceCodeReferencePoint::getRepresentedEntity)
			.forEach(it -> DbTransactions.wrap(this::registerProgramAndDefaultSchedule).accept(it));
	}

	private void registerProgramAndDefaultSchedule(AGUuid uuid) {

		AGProgram program = new AGProgram()//
			.setProgramUuid(uuid.getUuid())
			.save();
		program.getState().save();
		registerDefaultSchedule(uuid);
	}

	private void registerDefaultSchedule(AGUuid uuid) {

		EmfSourceCodeReferencePoints
			.getReferencePointOrThrow(uuid.getUuid(), IProgram.class)//
			.getDefaultCronExpression()
			.ifPresent(cronExpression -> insertScheduledProgramExecution(cronExpression, uuid));
	}

	private void insertScheduledProgramExecution(String cronExpression, AGUuid uuid) {

		new AGScheduledProgramExecution()//
			.setActive(true)
			.setCronExpression(cronExpression)
			.setProgramUuid(uuid.getUuid())
			.save();
	}
}

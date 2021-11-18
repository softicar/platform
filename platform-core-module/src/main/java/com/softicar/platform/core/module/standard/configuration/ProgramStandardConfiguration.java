package com.softicar.platform.core.module.standard.configuration;

import com.softicar.platform.core.module.configuration.AbstractStandardConfiguration;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoint;
import com.softicar.platform.db.core.transaction.DbTransactions;

public class ProgramStandardConfiguration extends AbstractStandardConfiguration {

	@Override
	public void createAndSaveAll() {

		Programs//
			.getAllProgramsAsIndirectEntities()
			.stream()
			.map(AGUuidBasedSourceCodeReferencePoint::getRepresentedEntity)
			.forEach(it -> DbTransactions.wrap(this::registerProgram).accept(it));
	}

	private void registerProgram(AGUuid uuid) {

		new AGProgram()//
			.setAbortRequested(false)
			.setCurrentExecution(null)
			.setProgramUuid(uuid.getUuid())
			.setQueuedAt(null)
			.save();
	}
}

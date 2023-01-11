package com.softicar.platform.core.module.standard.configuration;

import com.softicar.platform.core.module.configuration.AbstractStandardConfiguration;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.program.execution.scheduled.AGScheduledProgramExecution;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.core.module.uuid.AGUuidBasedSourceCodeReferencePoint;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProgramStandardConfiguration extends AbstractStandardConfiguration {

	@Override
	public void createAndSaveAll() {

		var programs = createPrograms();

		saveAll(programs);
		saveAll(createStates(programs));
		saveAll(createScheduledExecutions(programs));
	}

	private Collection<AGProgram> createPrograms() {

		return Programs//
			.getAllProgramsAsIndirectEntities()
			.stream()
			.map(AGUuidBasedSourceCodeReferencePoint::getRepresentedEntity)
			.map(this::createProgram)
			.collect(Collectors.toList());
	}

	private AGProgram createProgram(AGUuid uuid) {

		return new AGProgram().setProgramUuid(uuid.getUuid());
	}

	private Collection<AGProgramState> createStates(Collection<AGProgram> programs) {

		return AGProgramState.TABLE//
			.getOrCreateAsMap(programs)
			.values();
	}

	private Collection<AGScheduledProgramExecution> createScheduledExecutions(Collection<AGProgram> programs) {

		return programs//
			.stream()
			.map(this::createScheduledProgramExecution)
			.flatMap(Optional::stream)
			.collect(Collectors.toList());
	}

	private Optional<AGScheduledProgramExecution> createScheduledProgramExecution(AGProgram program) {

		return program//
			.getProgram()
			.getDefaultCronExpression()
			.map(cronExpression -> createScheduledProgramExecution(program, cronExpression));
	}

	private AGScheduledProgramExecution createScheduledProgramExecution(AGProgram program, String cronExpression) {

		return new AGScheduledProgramExecution()//
			.setActive(true)
			.setCronExpression(cronExpression)
			.setProgramUuid(program.getProgramUuid().getUuid());
	}
}

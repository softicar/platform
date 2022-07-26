package com.softicar.platform.core.module.program.execution.cleanup;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.core.module.event.AGSystemEvent;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.TestProgram;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.state.AGProgramState;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.CurrentUser;
import org.junit.Test;

public class ProgramExecutionsCleanerTest extends AbstractCoreTest {

	private final AGProgram program;

	public ProgramExecutionsCleanerTest() {

		program = insertProgram();
	}

	@Test
	public void testOrphanedExecutionCleanup() {

		AGProgramExecution activeExecution = insertProgramExecution();
		insertProgramState(program, activeExecution);

		AGProgramExecution orphanedExecution = insertProgramExecution();
		assertFalse(orphanedExecution.isFailed());
		assertNull(orphanedExecution.getTerminatedAt());

		new ProgramExecutionsCleaner().cleanupOrphanedRecords();

		assertTrue(orphanedExecution.isFailed());
		assertNotNull(orphanedExecution.getTerminatedAt());
		assertOne(AGSystemEvent.TABLE.loadAll());

		assertFalse(activeExecution.isFailed());
		assertNull(activeExecution.getTerminatedAt());
	}

	private AGProgram insertProgram() {

		return new AGProgram()//
			.setProgramUuid(SourceCodeReferencePoints.getUuidOrThrow(TestProgram.class))
			.save();
	}

	private AGProgramExecution insertProgramExecution() {

		return new AGProgramExecution()//
			.setProgramUuid(SourceCodeReferencePoints.getUuidOrThrow(TestProgram.class))
			.setQueuedBy(CurrentUser.get())
			.save();
	}

	private AGProgramState insertProgramState(AGProgram program, AGProgramExecution execution) {

		return AGProgramState.TABLE//
			.getOrCreate(program)
			.setCurrentExecution(execution)
			.save();
	}
}

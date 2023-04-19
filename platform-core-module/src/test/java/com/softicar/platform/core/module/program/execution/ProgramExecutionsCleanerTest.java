package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.core.module.event.AGSystemEvent;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.TestProgram;
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
	public void testCleanupOrphanedExecutionsWithoutStateRecord() {

		AGProgramExecution executionWithoutState = insertProgramExecution();
		assertFalse(executionWithoutState.isFailed());
		assertNull(executionWithoutState.getTerminatedAt());

		new ProgramExecutionsCleaner().cleanupOrphanedExecutions();

		assertTrue(executionWithoutState.isFailed());
		assertNotNull(executionWithoutState.getTerminatedAt());

		assertCount(1, AGSystemEvent.TABLE.loadAll());
	}

	@Test
	public void testCleanupOrphanedExecutionsWithStateRecord() {

		AGProgramExecution executionWithState = insertProgramExecution();
		insertProgramState(program, executionWithState);
		assertFalse(executionWithState.isFailed());
		assertNull(executionWithState.getTerminatedAt());

		new ProgramExecutionsCleaner().cleanupOrphanedExecutions();

		assertTrue(executionWithState.isFailed());
		assertNotNull(executionWithState.getTerminatedAt());

		assertCount(1, AGSystemEvent.TABLE.loadAll());
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

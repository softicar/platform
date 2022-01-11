package com.softicar.platform.core.module.program.abort;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.AbstractProgramTest;
import com.softicar.platform.core.module.program.execution.ProgramExecutionInserter;
import java.util.UUID;
import org.junit.Test;

public class ProgramAbortRequesterTest extends AbstractProgramTest {

	private static final UUID SOME_UUID = UUID.fromString("d6c08b5e-9e35-489d-a984-16ad021f6b1e");
	private final AGProgram program;

	public ProgramAbortRequesterTest() {

		this.program = new AGProgram()//
			.setProgramUuid(SOME_UUID)
			.setQueuedAt(null)
			.setQueuedBy(user)
			.setAbortRequested(false)
			.setCurrentExecution(null)
			.save();
	}

	@Test
	public void testRequestAbortWithCurrentExecutionAndWithQueuedAt() {

		updateQueuedAtToNow();
		insertCurrentExecution();

		boolean success = requestAbort();

		assertTrue(program.isAbortRequested());
		assertTrue(success);
	}

	@Test
	public void testRequestAbortWithCurrentExecutionAndWithoutQueuedAt() {

		insertCurrentExecution();

		boolean success = requestAbort();

		assertTrue(program.isAbortRequested());
		assertTrue(success);
	}

	@Test
	public void testRequestAbortWithoutCurrentExecutionAndWithQueuedAt() {

		updateQueuedAtToNow();

		boolean success = requestAbort();

		assertTrue(program.isAbortRequested());
		assertTrue(success);
	}

	@Test
	public void testRequestAbortWithoutCurrentExecutionAndWithoutQueuedAt() {

		boolean success = requestAbort();

		assertFalse(program.isAbortRequested());
		assertFalse(success);
	}

	@Test
	public void testIsAbortRequestableWithCurrentExecutionAndWithQueuedAt() {

		updateQueuedAtToNow();
		insertCurrentExecution();

		assertTrue(isAbortRequestable());
	}

	@Test
	public void testIsAbortRequestableWithCurrentExecutionAndWithoutQueuedAt() {

		insertCurrentExecution();

		assertTrue(isAbortRequestable());
	}

	@Test
	public void testIsAbortRequestableWithoutCurrentExecutionAndWithQueuedAt() {

		updateQueuedAtToNow();

		assertTrue(isAbortRequestable());
	}

	@Test
	public void testIsAbortRequestableWithoutCurrentExecutionAndWithoutQueuedAt() {

		assertFalse(isAbortRequestable());
	}

	private void updateQueuedAtToNow() {

		program.setQueuedAt(DayTime.now()).save();
	}

	private void insertCurrentExecution() {

		new ProgramExecutionInserter(program).insert();
	}

	private boolean requestAbort() {

		return new ProgramAbortRequester(program).requestAbort();
	}

	private boolean isAbortRequestable() {

		return program.isQueuedOrRunning();
	}
}

package com.softicar.platform.core.module.program.execution.queued.unqueue;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.unqueue.ProgramUnqueuer;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.UUID;
import org.junit.Test;

public class ProgramUnqueuerTest extends AbstractDbTest {

	private static final UUID SOME_UUID = UUID.fromString("28818f64-369a-412c-a27e-c6697800a600");
	private final AGProgram program;

	public ProgramUnqueuerTest() {

		this.program = new AGProgram()//
			.setProgramUuid(SOME_UUID)
			.setQueuedAt(null)
			.setAbortRequested(false)
			.setCurrentExecution(null)
			.save();
	}

	@Test
	public void testWithoutQueuedAt() {

		boolean removed = new ProgramUnqueuer(program).removeFromQueue();

		assertFalse(removed);
		assertNull(program.getQueuedAt());
	}

	@Test
	public void testWithQueuedAt() {

		program.setQueuedAt(DayTime.now()).save();

		boolean removed = new ProgramUnqueuer(program).removeFromQueue();

		assertTrue(removed);
		assertNull(program.getQueuedAt());
	}
}

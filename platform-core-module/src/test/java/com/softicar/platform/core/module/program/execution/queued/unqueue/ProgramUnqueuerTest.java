package com.softicar.platform.core.module.program.execution.queued.unqueue;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.AbstractProgramTest;
import com.softicar.platform.core.module.program.unqueue.ProgramUnqueuer;
import java.util.UUID;
import org.junit.Test;

public class ProgramUnqueuerTest extends AbstractProgramTest {

	private static final UUID SOME_UUID = UUID.fromString("28818f64-369a-412c-a27e-c6697800a600");
	private final AGProgram program;

	public ProgramUnqueuerTest() {

		this.program = new AGProgram()//
			.setProgramUuid(SOME_UUID)
			.save();
		this.program.getOrCreateProgramState().save();
	}

	@Test
	public void testWithoutQueuedAt() {

		boolean removed = new ProgramUnqueuer(program).removeFromQueue();

		assertFalse(removed);
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithQueuedAt() {

		program//
			.getOrCreateProgramState()
			.setQueuedAt(DayTime.now())
			.setQueuedBy(user)
			.save();

		boolean removed = new ProgramUnqueuer(program).removeFromQueue();

		assertTrue(removed);
		assertNull(program.getQueuedAt());
	}
}

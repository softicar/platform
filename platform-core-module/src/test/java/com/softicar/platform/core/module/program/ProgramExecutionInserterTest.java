package com.softicar.platform.core.module.program;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.ProgramExecutionInserter;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.UUID;
import org.junit.Test;

public class ProgramExecutionInserterTest extends AbstractDbTest {

	private static final UUID SOME_UUID = UUID.fromString("a46e629b-8756-43a5-8bff-6294dba7a2c4");
	private final AGProgram program;
	private final AGUser user;

	public ProgramExecutionInserterTest() {

		this.user = insertUser();
		this.program = new AGProgram()//
			.setProgramUuid(SOME_UUID)
			.setQueuedAt(DayTime.now())
			.setQueuedBy(user)
			.setAbortRequested(false)
			.setCurrentExecution(null)
			.save();
	}

	@Test
	public void test() {

		new ProgramExecutionInserter(program).insert();

		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
		assertNotNull(program.getCurrentExecution());

		AGProgramExecution currentExecution = program.getCurrentExecution();
		assertEquals(program.getProgramUuid(), currentExecution.getProgramUuid());
		assertNull(currentExecution.getStartedAt());
		assertNull(currentExecution.getTerminatedAt());
		assertEquals("", currentExecution.getOutput());
		assertEquals(user, currentExecution.getQueuedBy());
	}

	private AGUser insertUser() {

		return new AGUser()//
			.setActive(false)
			.setLoginName("john.doe")
			.setFirstName("John")
			.setLastName("Doe")
			.setEmailAddress("john.doe@example.com")
			.save();
	}
}

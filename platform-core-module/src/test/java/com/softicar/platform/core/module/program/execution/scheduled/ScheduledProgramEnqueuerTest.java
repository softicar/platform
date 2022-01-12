package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.core.module.program.AbstractProgramTest;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import java.util.UUID;
import org.junit.Test;

public class ScheduledProgramEnqueuerTest extends AbstractProgramTest {

	private static final Time NOON = new Time(12, 0, 0);
	private static final Day SOME_DAY = Day.fromYMD(2020, 1, 1);
	private static final UUID SOME_UUID = UUID.fromString("460703d1-236f-4c8b-af21-6de1f29b18e7");
	private final DayTime noon;
	private final DayTime beforeNoon;
	private final AGScheduledProgramExecution scheduledExecution;
	private final AGProgram program;

	public ScheduledProgramEnqueuerTest() {

		this.noon = new DayTime(SOME_DAY, NOON);
		this.beforeNoon = noon.minusSeconds(60);

		this.scheduledExecution = new AGScheduledProgramExecution()//
			.setActive(true)
			.setCronExpression("0 12 * * *")
			.setProgramUuid(SOME_UUID)
			.save();

		this.program = new AGProgram()//
			.setProgramUuid(SOME_UUID)
			.setQueuedAt(null)
			.setCurrentExecution(null)
			.save();
	}

	// -------------------- matching schedule ------------------------------ //

	@Test
	public void testWithMatchingScheduleWithoutExistingQueuedAt() {

		program.setQueuedAt(null).save();

		runEnqueuer(noon);

		assertQueued(noon);
	}

	@Test
	public void testWithMatchingScheduleWithoutExistingQueuedAtAndWithRunningOtherProgram() {

		program.setQueuedAt(null).save();
		insertProgramExecution(noon, UUID.fromString("34fcfe77-e7b3-403f-ae3a-556d848d315a"));

		runEnqueuer(noon);

		assertQueued(noon);
	}

	@Test
	public void testWithMatchingScheduleWithExistingQueuedAt() {

		program.setQueuedAt(beforeNoon).setQueuedBy(AGCoreModuleInstance.getInstance().getSystemUser()).save();

		runEnqueuer(noon);

		assertQueued(beforeNoon);
	}

	@Test
	public void testWithMatchingScheduleWithActiveExecution() {

		program.setCurrentExecution(insertProgramExecution(noon)).save();

		runEnqueuer(noon);

		assertQueued(null);
	}

	@Test
	public void testWithMatchingScheduleWithActiveButOldExecution() {

		program.setCurrentExecution(insertProgramExecution(beforeNoon)).save();

		runEnqueuer(noon);

		assertQueued(noon);
	}

	@Test
	public void testWithMatchingScheduleWithFinishedButRecentExecution() {

		insertProgramExecution(noon).setTerminatedAt(noon).save();

		runEnqueuer(noon);

		assertQueued(null);
	}

	@Test
	public void testWithMatchingScheduleWithoutProgramRow() {

		program.delete();

		runEnqueuer(noon);

		AGProgram program = assertOne(AGProgram.TABLE.loadAll());
		assertEquals(SOME_UUID, program.getProgramUuid().getUuid());
		assertEquals(noon, program.getQueuedAt());
		assertNull(program.getCurrentExecution());
	}

	// -------------------- non-matching schedule ------------------------------ //

	@Test
	public void testWithNonMatchingSchedule() {

		runEnqueuer(noon.plusSeconds(60));

		assertNull(program.getQueuedAt());
	}

	// -------------------- auxiliary methods ------------------------------ //

	private void runEnqueuer(DayTime currentMinute) {

		new ScheduledProgramEnqueuer(scheduledExecution, currentMinute).enqueueExecution();
	}

	private void assertQueued(DayTime expectedQueuedAt) {

		assertEquals(expectedQueuedAt, program.getQueuedAt());
		assertEquals(expectedQueuedAt != null? AGCoreModuleInstance.getInstance().getSystemUser() : null, program.getQueuedBy());
	}

	private AGProgramExecution insertProgramExecution(DayTime startedAt) {

		return insertProgramExecution(startedAt, SOME_UUID);
	}

	private AGProgramExecution insertProgramExecution(DayTime startedAt, UUID programUuid) {

		return new AGProgramExecution()//
			.setProgramUuid(programUuid)
			.setStartedAt(startedAt)
			.setQueuedBy(user)
			.save();
	}
}

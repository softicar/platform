package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.thread.runner.LimitedThreadRunner;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.core.thread.sleeper.DefaultSleeper;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.core.module.event.AGSystemEvent;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.ProgramExecutionRunnable;
import com.softicar.platform.core.module.program.execution.scheduled.AGScheduledProgramExecution;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.time.Duration;
import java.util.UUID;
import org.junit.Test;

/**
 * Unit tests for {@link QueuedProgramExecutionDaemon}.
 * <p>
 * Test methods cover all possible permutations of following properties:
 * <ol>
 * <li><b>current execution</b> exists (yes/no)</li>
 * <li><b>queued</b>-at date specified (yes/no)</li>
 * <li><b>abort</b> requested (yes/no)</li>
 * <li><b>slots</b> are available (yes/no)</li>
 * </ol>
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class QueuedProgramExecutionDaemonTest extends AbstractProgramTest {

	private static final int THREAD_LIMIT = 2;
	private static final UUID TEST_PROGRAM_UUID = EmfSourceCodeReferencePoints.getUuidOrThrow(TestProgram.class);
	private final TestThreadRunner threadRunner;
	private final QueuedProgramExecutionDaemon daemon;
	private final DayTime now;

	public QueuedProgramExecutionDaemonTest() {

		setupTestSleeper();

		this.threadRunner = new TestThreadRunner();
		this.daemon = new QueuedProgramExecutionDaemon(threadRunner);
		this.now = DayTime.now();
	}

	@Test
	public void testWithoutQueuedExecutionRecords() {

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();
	}

	// -------------------------------- Queued-Execution Property Permutations -------------------------------- //

	@Test
	public void testWithCurrentExecutionAndQueuedAndAbortAndSlots() {

		AGProgram program = insertProgram(insertCurrentExecution(user), now, user, true);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithCurrentExecutionAndQueuedAndAbortAndNoSlots() {

		AGProgram program = insertProgram(insertCurrentExecution(user), now, user, true);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithCurrentExecutionAndQueuedAndNoAbortAndSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user);
		AGProgram program = insertProgram(currentExecution, now, user, false);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertSame(currentExecution, program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertEquals(now, program.getQueuedAt());
		assertEquals(user, program.getQueuedBy());
	}

	@Test
	public void testWithCurrentExecutionAndQueuedAndNoAbortAndNoSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user);
		AGProgram program = insertProgram(currentExecution, now, user, false);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertSame(currentExecution, program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertEquals(now, program.getQueuedAt());
		assertEquals(user, program.getQueuedBy());
	}

	@Test
	public void testWithCurrentExecutionAndNotQueuedAndAbortAndSlots() {

		AGProgram program = insertProgram(insertCurrentExecution(user), null, null, true);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithCurrentExecutionAndNotQueuedAndAbortAndNoSlots() {

		AGProgram program = insertProgram(insertCurrentExecution(user), null, null, true);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithCurrentExecutionAndNotQueuedAndNoAbortAndSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user);
		AGProgram program = insertProgram(currentExecution, null, null, false);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertSame(currentExecution, program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithCurrentExecutionAndNotQueuedAndNoAbortAndNoSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user);
		AGProgram program = insertProgram(currentExecution, null, null, false);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertSame(currentExecution, program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithNoCurrentExecutionAndQueuedAndAbortAndSlots() {

		AGProgram program = insertProgram(null, now, user, true);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithNoCurrentExecutionAndQueuedAndAbortAndNoSlots() {

		AGProgram program = insertProgram(null, now, user, true);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(2);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithNoCurrentExecutionAndQueuedAndNoAbortAndSlots() {

		AGProgram program = insertProgram(null, now, user, false);

		daemon.runIteration();

		threadRunner.assertStartedThreads(1);

		AGProgramExecution execution = assertOneExecution();
		assertEquals(TEST_PROGRAM_UUID, execution.getProgramUuid().getUuid());
		assertNull(execution.getStartedAt());
		assertNull(execution.getTerminatedAt());
		assertEquals("", execution.getOutput());

		assertSame(execution, program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithNoCurrentExecutionAndQueuedAndNoAbortAndNoSlots() {

		AGProgram program = insertProgram(null, now, user, false);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertEquals(now, program.getQueuedAt());
		assertEquals(user, program.getQueuedBy());
	}

	@Test
	public void testWithNoCurrentExecutionAndNotQueuedAndAbortAndSlots() {

		AGProgram program = insertProgram(null, null, null, true);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithNoCurrentExecutionAndNotQueuedAndAbortAndNoSlots() {

		AGProgram program = insertProgram(null, null, null, true);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithNoCurrentExecutionAndNotQueuedAndNoAbortAndSlots() {

		AGProgram program = insertProgram(null, null, null, false);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithNoCurrentExecutionAndNotQueuedAndNoAbortAndNoSlots() {

		AGProgram program = insertProgram(null, null, null, false);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	// -------------------------------- Cleanup of terminated executions -------------------------------- //

	@Test
	public void testWithTerminatedCurrentExecutionAndQueuedAndAbortAndSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user).setTerminatedAt(now.minusDays(1)).save();
		AGProgram program = insertProgram(currentExecution, now, user, true);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithTerminatedCurrentExecutionAndQueuedAndAbortAndNoSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user).setTerminatedAt(now.minusDays(1)).save();
		AGProgram program = insertProgram(currentExecution, now, user, true);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithTerminatedCurrentExecutionAndQueuedAndNoAbortAndSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user).setTerminatedAt(now.minusDays(1)).save();
		AGProgram program = insertProgram(currentExecution, now, user, false);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertEquals(now, program.getQueuedAt());
		assertEquals(user, program.getQueuedBy());
	}

	@Test
	public void testWithTerminatedCurrentExecutionAndQueuedAndNoAbortAndNoSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user).setTerminatedAt(now.minusDays(1)).save();
		AGProgram program = insertProgram(currentExecution, now, user, false);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertEquals(now, program.getQueuedAt());
		assertEquals(user, program.getQueuedBy());
	}

	@Test
	public void testWithTerminatedCurrentExecutionAndNotQueuedAndAbortAndSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user).setTerminatedAt(now.minusDays(1)).save();
		AGProgram program = insertProgram(currentExecution, null, null, true);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithTerminatedCurrentExecutionAndNotQueuedAndAbortAndNoSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user).setTerminatedAt(now.minusDays(1)).save();
		AGProgram program = insertProgram(currentExecution, null, null, true);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithTerminatedCurrentExecutionAndNotQueuedAndNoAbortAndSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user).setTerminatedAt(now.minusDays(1)).save();
		AGProgram program = insertProgram(currentExecution, null, null, false);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithTerminatedCurrentExecutionAndNotQueuedAndNoAbortAndNoSlots() {

		AGProgramExecution currentExecution = insertCurrentExecution(user).setTerminatedAt(now.minusDays(1)).save();
		AGProgram program = insertProgram(currentExecution, null, null, false);
		threadRunner.simulateNoAvailableSlots();

		daemon.runIteration();

		threadRunner.assertStartedThreads(THREAD_LIMIT);
		threadRunner.assertNoQueuedRunnables();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	// ------------------------------ Exceeded Runtime ------------------------------ //

	// do not use test timeouts; it will break this test
	@Test//(timeout = 3000)
	public void testExceededRuntime() {

		// create program and schedule
		AGProgram program = insertProgram(null, now, user, false);
		new AGScheduledProgramExecution()//
			.setActive(true)
			.setAutomaticAbort(true)
			.setCronExpression("* * * * *")
			.setMaximumRuntime(5)
			.setProgramUuid(program.getProgramUuid())
			.save();

		// initial iteration; should start thread
		daemon.runIteration();
		threadRunner.assertStartedThreads(1);
		threadRunner.assertNoQueuedRunnables();
		assertNotNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
		assertFalse(assertOneExecution().isMaximumRuntimeExceeded());
		assertNull(assertOneExecution().getTerminatedAt());
		assertNone(AGSystemEvent.TABLE.loadAll());

		// iteration before maximum runtime is exceeded
		Sleep.sleep(Duration.ofMinutes(3));
		daemon.runIteration();
		threadRunner.assertStartedThreads(1);
		threadRunner.assertNoQueuedRunnables();
		assertNotNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertFalse(assertOneExecution().isMaximumRuntimeExceeded());
		assertNull(assertOneExecution().getTerminatedAt());
		assertNone(AGSystemEvent.TABLE.loadAll());

		// iteration after maximum runtime is exceeded
		Sleep.sleep(Duration.ofMinutes(3));
		daemon.runIteration();
		threadRunner.assertStartedThreads(1);
		threadRunner.assertNoQueuedRunnables();
		assertNotNull(program.getCurrentExecution());
		assertTrue(program.isAbortRequested());
		assertTrue(assertOneExecution().isMaximumRuntimeExceeded());

		// validate system error event
		var systemEvent = assertOne(AGSystemEvent.TABLE.loadAll());
		assertContains(TestProgram.class.getSimpleName(), systemEvent.getProperties());
		assertEquals(AGSystemEventSeverityEnum.ERROR, systemEvent.getSeverity().getEnum());

		try (DbTransaction transaction = new DbTransaction()) {
			transaction.assertIsRootTransaction();
		}

		// wait for program to be aborted
		daemon.runIteration();
		threadRunner.waitForTerminationOfStartedThreads();

		// iteration after program was aborted
		daemon.runIteration();
		threadRunner.assertNoStartedThread();
		threadRunner.assertNoQueuedRunnables();
		assertOneExecution();
		assertNull(program.getCurrentExecution());
		assertTrue(assertOne(AGProgramExecution.TABLE.loadAll()).isMaximumRuntimeExceeded());
		assertFalse(program.isAbortRequested());
		assertCount(2, AGSystemEvent.TABLE.loadAll());
	}

	// ------------------------------ auxiliary methods ------------------------------ //

	private static AGProgramExecution insertCurrentExecution(AGUser user) {

		return new AGProgramExecution()//
			.setProgramUuid(TEST_PROGRAM_UUID)
			.setQueuedBy(user)
			.save();
	}

	private static AGProgram insertProgram(AGProgramExecution currentExecution, DayTime queuedAt, AGUser user, boolean abortRequested) {

		AGProgram program = new AGProgram()//
			.setProgramUuid(TEST_PROGRAM_UUID)
			.save();
		program//
			.getState()
			.setQueuedAt(queuedAt)
			.setQueuedBy(user)
			.setAbortRequested(abortRequested)
			.setCurrentExecution(currentExecution)
			.save();
		return program;
	}

	public AGProgramExecution assertOneExecution() {

		return Asserts.assertOne(AGProgramExecution.TABLE.loadAll());
	}

	private static class TestThreadRunner extends LimitedThreadRunner<ProgramExecutionRunnable> {

		public TestThreadRunner() {

			super(THREAD_LIMIT);
		}

		public void waitForTerminationOfStartedThreads() {

			while (getStartedThreadsCount() > 0) {
				Thread.yield();
			}
		}

		public void simulateNoAvailableSlots() {

			while (hasAvailableSlots()) {
				if (getQueueRunnablesCount() > 0) {
					startThreads();
				} else {
					addRunnable(new DummyRunnable());
				}
			}
		}

		public void assertStartedThreads(int expectedCount) {

			assertEquals("started threads", expectedCount, getStartedThreadsCount());
		}

		public void assertNoStartedThread() {

			assertStartedThreads(0);
		}

		public void assertNoQueuedRunnables() {

			assertEquals("queued runnables", 0, getQueueRunnablesCount());
		}
	}

	private static class DummyRunnable extends ProgramExecutionRunnable {

		public DummyRunnable() {

			super(new AGProgramExecution());
		}

		@Override
		public void run() {

			// intentionally not using CurrentSleeper
			new DefaultSleeper().sleep(10000);
		}
	}
}

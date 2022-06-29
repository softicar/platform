package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.thread.IRunnableThread;
import com.softicar.platform.common.core.thread.runner.ILimitedThreadRunner;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.event.AGSystemEvent;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.program.execution.AGProgramExecution;
import com.softicar.platform.core.module.program.execution.ProgramExecutionRunnable;
import com.softicar.platform.core.module.program.execution.scheduled.AGScheduledProgramExecution;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
 */
public class QueuedProgramExecutionDaemonTest extends AbstractProgramTest {

	private static final UUID TEST_PROGRAM_UUID = EmfSourceCodeReferencePoints.getUuidOrThrow(TestProgram.class);
	private final FakeLimitedThreadRunner threadRunner;
	private final QueuedProgramExecutionDaemon daemon;
	private final DayTime now;

	public QueuedProgramExecutionDaemonTest() {

		this.threadRunner = new FakeLimitedThreadRunner();
		this.daemon = new QueuedProgramExecutionDaemon(threadRunner);
		this.now = DayTime.now();
		setupTestSleeper();
	}

	@Test
	public void testWithoutQueuedExecutionRecords() {

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();
	}

	// -------------------------------- Queued-Execution Property Permutations -------------------------------- //

	@Test
	public void testWithCurrentExecutionAndQueuedAndAbortAndSlots() {

		AGProgram program = insertProgram(insertCurrentExecution(user), now, user, true);

		daemon.runIteration();

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	@Test
	public void testWithNoCurrentExecutionAndQueuedAndNoAbortAndSlots() {

		AGProgram program = insertProgram(null, now, user, false);

		daemon.runIteration();

		threadRunner.assertStartedThread();

		AGProgramExecution execution = threadRunner.assertOneExecution();
		assertEquals(TEST_PROGRAM_UUID, execution.getProgramUuid().getUuid());
		assertEquals(now, execution.getStartedAt());
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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

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
		threadRunner.assertNoExecutions();

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

		threadRunner.assertNoStartedThread();
		threadRunner.assertNoExecutions();

		assertNull(program.getCurrentExecution());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
	}

	// ------------------------------ Exceeded Runtime ------------------------------ //

	@Test
	public void testExceededRuntime() {

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
		threadRunner.assertStartedThread();
		threadRunner.assertOneExecution();
		assertNotNull(program.getCurrentExecution());
		assertFalse(assertOne(AGProgramExecution.TABLE.loadAll()).isMaximumRuntimeExceeded());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
		assertNone(AGSystemEvent.TABLE.loadAll());

		// iteration before maximum runtime is exceeded
		Sleep.sleep(Duration.ofMinutes(3));
		daemon.runIteration();
		threadRunner.assertStartedThread();
		threadRunner.assertOneExecution();
		assertNotNull(program.getCurrentExecution());
		assertFalse(assertOne(AGProgramExecution.TABLE.loadAll()).isMaximumRuntimeExceeded());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
		assertNone(AGSystemEvent.TABLE.loadAll());

		// iteration after maximum runtime is exceeded
		Sleep.sleep(Duration.ofMinutes(2));
		daemon.runIteration();
		threadRunner.assertStartedThread();
		threadRunner.assertOneExecution();
		assertNotNull(program.getCurrentExecution());
		assertTrue(assertOne(AGProgramExecution.TABLE.loadAll()).isMaximumRuntimeExceeded());
		assertTrue(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());

		// validate system error event
		var systemEvent = assertOne(AGSystemEvent.TABLE.loadAll());
		assertContains(TestProgram.class.getSimpleName(), systemEvent.getProperties());
		assertEquals(AGSystemEventSeverityEnum.ERROR, systemEvent.getSeverity().getEnum());

		// iteration after program was aborted
		daemon.runIteration();
		threadRunner.assertNoStartedThread();
		threadRunner.assertOneExecution();
		assertNull(program.getCurrentExecution());
		assertTrue(assertOne(AGProgramExecution.TABLE.loadAll()).isMaximumRuntimeExceeded());
		assertFalse(program.isAbortRequested());
		assertNull(program.getQueuedAt());
		assertNull(program.getQueuedBy());
		assertOne(AGSystemEvent.TABLE.loadAll());
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

	// TODO generalize this class into a LimitedThreadTestRunner
	private static class FakeLimitedThreadRunner implements ILimitedThreadRunner<ProgramExecutionRunnable> {

		private final Collection<AGProgramExecution> executions;
		private ProgramExecutionRunnable queuedRunnable;
		private FakeRunnableThread startedThread;
		private boolean noAvailableSlots;

		public FakeLimitedThreadRunner() {

			this.executions = new ArrayList<>();
			this.queuedRunnable = null;
			this.startedThread = null;
			this.noAvailableSlots = false;
		}

		@Override
		public Collection<IRunnableThread<ProgramExecutionRunnable>> startThreads() {

			if (hasAvailableSlots() && queuedRunnable != null) {
				var runnableThread = new FakeRunnableThread(queuedRunnable);
				runnableThread.start();
				this.startedThread = runnableThread;
				this.queuedRunnable = null;
				return Collections.singleton(runnableThread);
			} else {
				return Collections.emptyList();
			}
		}

		@Override
		public void addRunnable(ProgramExecutionRunnable runnable) {

			if (queuedRunnable == null) {
				this.queuedRunnable = runnable;
				this.executions.add(runnable.getExecution());
			} else {
				throw new UnsupportedOperationException();
			}
		}

		@Override
		public boolean hasAvailableSlots() {

			removeThreadIfTerminated();
			return !noAvailableSlots && startedThread == null;
		}

		public void simulateNoAvailableSlots() {

			this.noAvailableSlots = true;
		}

		public AGProgramExecution assertOneExecution() {

			return assertOne(executions);
		}

		public void assertNoExecutions() {

			assertTrue(executions.isEmpty());
		}

		public void assertStartedThread() {

			assertNotNull(startedThread);
			assertTrue(startedThread.isStarted());
		}

		public void assertNoStartedThread() {

			assertNull(startedThread);
		}

		private void removeThreadIfTerminated() {

			if (startedThread != null && startedThread.isTerminated()) {
				this.startedThread = null;
			}
		}

		@Override
		public boolean isFinished() {

			removeThreadIfTerminated();
			return this.startedThread == null && queuedRunnable == null;
		}
	}

	private static class FakeRunnableThread implements IRunnableThread<ProgramExecutionRunnable> {

		private final ProgramExecutionRunnable runnable;
		private boolean terminated;
		private boolean started;

		public FakeRunnableThread(ProgramExecutionRunnable runnable) {

			this.runnable = runnable;
			this.terminated = false;
			this.started = false;
		}

		@Override
		public ProgramExecutionRunnable getRunnable() {

			return runnable;
		}

		@Override
		public void start() {

			this.started = true;
		}

		@Override
		public void interrupt() {

			throw new UnsupportedOperationException();
		}

		@Override
		public boolean kill() {

			setTerminated(true);
			return true;
		}

		@Override
		public boolean isTerminated() {

			return terminated;
		}

		public boolean isStarted() {

			return started;
		}

		public void setTerminated(boolean terminated) {

			this.terminated = terminated;
		}
	}
}

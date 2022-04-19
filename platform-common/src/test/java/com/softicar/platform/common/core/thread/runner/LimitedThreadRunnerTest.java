package com.softicar.platform.common.core.thread.runner;

import com.softicar.platform.common.core.thread.IRunnableThread;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.testing.AbstractTest;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Test;

public class LimitedThreadRunnerTest extends AbstractTest {

	private static final String A = "A";
	private static final String B = "B";
	private static final String C = "C";
	private static final int TEST_TIMEOUT_MILLIS = 1000;
	private static final int THREAD_LIMIT = 2;
	private static final int LOOP_SLEEP_TIME = 1;
	private final LimitedThreadRunner<TestRunnable> runner;
	private final TestRunnable runnableA;
	private final TestRunnable runnableB;
	private final TestRunnable runnableC;
	private final Collection<TestRunnable> runnablesWithThread;

	public LimitedThreadRunnerTest() {

		this.runner = new LimitedThreadRunner<TestRunnable>(THREAD_LIMIT).setThreadFactory(this::createThread);
		this.runnableA = new TestRunnable(A);
		this.runnableB = new TestRunnable(B);
		this.runnableC = new TestRunnable(C);
		this.runnablesWithThread = new ArrayList<>();
	}

	@After
	public void cleanup() {

		runnableA.terminate();
		runnableB.terminate();
		runnableC.terminate();
	}

	@Test(timeout = TEST_TIMEOUT_MILLIS)
	public void testBeforeStarting() {

		runner.addRunnables(runnableA, runnableB);

		assertNoThreadCreated(runnableA, runnableB);
		assertTrue(runner.hasAvailableSlots());
		assertFalse(runner.isFinished());
	}

	@Test(timeout = TEST_TIMEOUT_MILLIS)
	public void testAfterStarting() {

		runner.addRunnables(runnableA, runnableB);
		var startedThreads = runner.startThreads();
		waitUntilRunMethodStarted(runnableA, runnableB);

		assertStartedThreads("A,B", startedThreads);
		assertCreatedThread("A,B");
		assertFalse(runner.hasAvailableSlots());
		assertFalse(runner.isFinished());
	}

	/**
	 * Attempt to start a number of threads that is greater than
	 * {@link #THREAD_LIMIT}.
	 */
	@Test(timeout = TEST_TIMEOUT_MILLIS)
	public void testAfterStartingWithRunnablesOverLimit() {

		runner.addRunnables(runnableA, runnableB, runnableC);
		var startedThreads = runner.startThreads();
		waitUntilRunMethodStarted(runnableA, runnableB);

		assertStartedThreads("A,B", startedThreads);
		assertCreatedThread("A,B");
		assertNoThreadCreated(runnableC);
		assertFalse(runner.hasAvailableSlots());
		assertFalse(runner.isFinished());
	}

	@Test(timeout = TEST_TIMEOUT_MILLIS)
	public void testAfterTerminationWithSingleThread() {

		runner.addRunnables(runnableA);
		var startedThreads = runner.startThreads();
		terminate(runnableA);

		assertStartedThreads("A", startedThreads);
		assertCreatedThread("A");
		assertThreadTerminated(runnableA);
		assertTrue(runner.hasAvailableSlots());
		assertTrue(runner.isFinished());
	}

	@Test(timeout = TEST_TIMEOUT_MILLIS)
	public void testAfterTerminationWithSeveralThreads() {

		runner.addRunnables(runnableA, runnableB);
		var startedThreads = runner.startThreads();
		terminate(runnableB);
		waitUntilRunMethodStarted(runnableA);

		assertStartedThreads("A,B", startedThreads);
		assertCreatedThread("A,B");
		assertThreadTerminated(runnableB);
		assertTrue(runner.hasAvailableSlots());
		assertFalse(runner.isFinished());
	}

	@Test(timeout = TEST_TIMEOUT_MILLIS)
	public void testAfterTerminationWithAllThreads() {

		runner.addRunnables(runnableA, runnableB);
		var startedThreads = runner.startThreads();
		terminate(runnableA, runnableB);

		assertStartedThreads("A,B", startedThreads);
		assertCreatedThread("A,B");
		assertThreadTerminated(runnableA, runnableB);
		assertTrue(runner.hasAvailableSlots());
		assertTrue(runner.isFinished());
	}

	@Test(timeout = TEST_TIMEOUT_MILLIS)
	public void testAfterTerminationWithRunnablesOverLimit() {

		runner.addRunnables(runnableA, runnableB, runnableC);
		var startedThreads = runner.startThreads();
		terminate(runnableA, runnableB);

		assertStartedThreads("A,B", startedThreads);
		assertCreatedThread("A,B");
		assertNoThreadCreated(runnableC);
		assertThreadTerminated(runnableA, runnableB);
		assertTrue(runner.hasAvailableSlots());
		assertFalse(runner.isFinished());
	}

	@Test(timeout = TEST_TIMEOUT_MILLIS)
	public void testTerminationAndSubsequentStarting() {

		runner.addRunnables(runnableA, runnableB, runnableC);
		var startedThreads1 = runner.startThreads();
		terminate(runnableA);
		var startedThreads2 = runner.startThreads();
		waitUntilRunMethodStarted(runnableB, runnableC);

		assertStartedThreads("A,B", startedThreads1);
		assertStartedThreads("C", startedThreads2);
		assertCreatedThread("A,B,C");
		assertThreadTerminated(runnableA);
		assertFalse(runner.hasAvailableSlots());
		assertFalse(runner.isFinished());
	}

	private Thread createThread(TestRunnable runnable) {

		this.runnablesWithThread.add(runnable);
		var thread = new Thread(runnable);
		runnable.setThread(thread);
		return thread;
	}

	private void waitUntilRunMethodStarted(TestRunnable...runnables) {

		for (TestRunnable runnable: runnables) {
			while (!runnable.isRunMethodStarted()) {
				Sleep.sleep(10);
			}
		}
	}

	private void terminate(TestRunnable...runnables) {

		for (TestRunnable runnable: runnables) {
			runnable.terminate();
		}
	}

	private void assertStartedThreads(String expectedRunnables, Collection<IRunnableThread<TestRunnable>> startedThreads) {

		assertEquals(
			"started threads",
			expectedRunnables,
			startedThreads//
				.stream()
				.map(IRunnableThread::getRunnable)
				.map(TestRunnable::getName)
				.collect(Collectors.joining(",")));
	}

	private void assertCreatedThread(String expectedRunnables) {

		assertEquals(
			"created threads",
			expectedRunnables,
			runnablesWithThread//
				.stream()
				.map(TestRunnable::getName)
				.collect(Collectors.joining(",")));
	}

	private void assertNoThreadCreated(TestRunnable...runnables) {

		for (int i = 0; i < runnables.length; i++) {
			TestRunnable runnable = runnables[i];
			assertFalse(//
				String.format("The given runnable number %s was started unexpectedly.", i + 1),
				runnablesWithThread.contains(runnable));
		}
	}

	private void assertThreadTerminated(TestRunnable...runnables) {

		for (int i = 0; i < runnables.length; i++) {
			TestRunnable runnable = runnables[i];
			assertTrue(//
				String.format("The given runnable number %s was not terminated as expected.", i + 1),
				runnable.isThreadTerminated());
		}
	}

	private static class TestRunnable implements Runnable {

		private final String name;
		private volatile boolean terminate;
		private volatile Status status;
		private long startupTime;
		private Thread thread;

		public TestRunnable(String name) {

			this.name = name;
			this.terminate = false;
			this.status = Status.NEW;
			this.thread = null;
		}

		@Override
		public void run() {

			this.status = Status.RUN_METHOD_STARTED;
			this.startupTime = System.currentTimeMillis();
			while (!this.terminate) {
				if (isTimeout()) {
					break;
				}
				Sleep.sleep(LOOP_SLEEP_TIME);
			}
			this.status = Status.RUN_METHOD_FINISHED;
		}

		public String getName() {

			return name;
		}

		public void setThread(Thread thread) {

			this.thread = thread;
		}

		public void terminate() {

			if (thread != null) {
				this.terminate = true;
				while (!(isRunMethodFinished() && isThreadTerminated())) {
					Sleep.sleep(LOOP_SLEEP_TIME);
				}
			}
		}

		public boolean isRunMethodStarted() {

			return status == Status.RUN_METHOD_STARTED;
		}

		public boolean isRunMethodFinished() {

			return status == Status.RUN_METHOD_FINISHED;
		}

		private boolean isThreadTerminated() {

			return thread.getState() == State.TERMINATED;
		}

		private boolean isTimeout() {

			return System.currentTimeMillis() - startupTime > TEST_TIMEOUT_MILLIS;
		}

		private static enum Status {

			NEW,
			RUN_METHOD_STARTED,
			RUN_METHOD_FINISHED
		}
	}
}

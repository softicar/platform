package com.softicar.platform.common.core.thread.collection;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Test cases for {@link ThreadKiller}.
 *
 * @author Oliver Richers
 */
public class ThreadKillerTest extends AbstractTest {

	public static class BadRunnable implements Runnable {

		private int interruptCountDown;

		public BadRunnable(int interruptCountDown) {

			this.interruptCountDown = interruptCountDown;
		}

		@Override
		public void run() {

			while (interruptCountDown > 0) {
				try {
					Thread.sleep(3600000);// try to sleep for 1 hour
				} catch (InterruptedException exception) {
					DevNull.swallow(exception);
					--interruptCountDown;
					Log.finfo("Got interruped exception.");
				}
			}
		}
	}

	@Test
	public void killThreadWorksWithNonStartedThread() {

		Thread thread = new Thread(new BadRunnable(5));
		boolean success = new ThreadKiller<>(thread).killAll();

		assertTrue(success);
	}

	@Test
	public void killThreadSendsEnoughInterrupts() {

		Thread thread = new Thread(new BadRunnable(5));
		thread.start();

		long timeout = 1000;
		long start = System.currentTimeMillis();
		boolean success = new ThreadKiller<>(thread)//
			.setTimeout(timeout)
			.setTryCount(10)
			.killAll();
		long duration = System.currentTimeMillis() - start;

		assertTrue(success);
		assertTrue(duration < 2 * timeout);
	}

	@Test
	public void killThreadReturnsFalseIfNotKillable() {

		Thread thread = new Thread(new BadRunnable(5));
		thread.start();

		long timeout = 50;
		long start = System.currentTimeMillis();
		boolean success = new ThreadKiller<>(thread)//
			.setTimeout(timeout)
			.setTryCount(2)
			.killAll();
		long duration = System.currentTimeMillis() - start;
		Log.finfo("duration: %s ms", duration);

		assertFalse(success);
		assertTrue(duration < 2 * timeout);
	}
}

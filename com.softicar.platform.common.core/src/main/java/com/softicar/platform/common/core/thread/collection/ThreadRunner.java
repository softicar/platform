package com.softicar.platform.common.core.thread.collection;

/**
 * Runs the given {@link Thread} objects and kills those that take too long.
 *
 * @author Oliver Richers
 */
public class ThreadRunner<T extends Thread> {

	private final ThreadCollection<Thread> threads;

	public ThreadRunner(Thread thread) {

		this.threads = new ThreadCollection<>(thread);
	}

	public ThreadRunner(ThreadCollection<? extends T> threads) {

		this.threads = new ThreadCollection<>(threads);
	}

	/**
	 * Starts all threads and joins them.
	 * <p>
	 * If one thread runs for more than the specified amount of milliseconds,
	 * the thread is killed. And this method returns <i>false</i>.
	 *
	 * @param timeoutMillis
	 *            maximum time to wait for the threads to finish
	 * @return <i>true</i> if all threads finished before timeout, <i>false</i>
	 *         if at least one thread was killed because of timeout
	 */
	public boolean runAll(long timeoutMillis) {

		threads.startAll();
		threads.joinAll(timeoutMillis);
		threads.removeFinishedThreads();
		if (threads.isEmpty()) {
			return true;
		} else {
			threads.killAll();
			return false;
		}
	}
}

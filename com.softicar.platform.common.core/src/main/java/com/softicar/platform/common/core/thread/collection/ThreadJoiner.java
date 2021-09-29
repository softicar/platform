package com.softicar.platform.common.core.thread.collection;

import com.softicar.platform.common.core.threading.Threads;

/**
 * This class joins a set of thread until a given timeout.
 *
 * @author Oliver Richers
 */
public class ThreadJoiner<T extends Thread> {

	private final ThreadCollection<T> threads;
	private long deadline;

	public ThreadJoiner(ThreadCollection<? extends T> threads) {

		this.threads = new ThreadCollection<>(threads);
	}

	public boolean joinAll(long timeoutMillis) {

		this.threads.removeFinishedThreads();
		this.deadline = System.currentTimeMillis() + timeoutMillis;

		do {
			joinFirstThread();
			threads.removeFinishedThreads();
		} while (!threads.isEmpty() && System.currentTimeMillis() <= deadline);

		return threads.isEmpty();
	}

	private void joinFirstThread() {

		threads//
			.stream()
			.findFirst()
			.ifPresent(this::joinThread);
	}

	private void joinThread(Thread thread) {

		Threads.join(thread, getRestTimeout());
	}

	private long getRestTimeout() {

		return Math.max(1, deadline - System.currentTimeMillis());
	}
}

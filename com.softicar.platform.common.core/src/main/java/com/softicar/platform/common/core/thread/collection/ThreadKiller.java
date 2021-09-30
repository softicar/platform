package com.softicar.platform.common.core.thread.collection;

/**
 * This class tries to kill one or more threads by calling
 * {@link Thread#interrupt()}.
 *
 * @author Oliver Richers
 */
public class ThreadKiller<T extends Thread> {

	private static final long DEFAULT_TIMEOUT = 100;
	private static final int DEFAULT_TRY_COUNT = 10;
	private final ThreadCollection<T> threads;
	private long timeout;
	private int tryCount;

	public ThreadKiller(T thread) {

		this(new ThreadCollection<>(thread));
	}

	public ThreadKiller(ThreadCollection<? extends T> threads) {

		this.threads = new ThreadCollection<>(threads);
		this.timeout = DEFAULT_TIMEOUT;
		this.tryCount = DEFAULT_TRY_COUNT;
	}

	public ThreadKiller<T> setTimeout(long timeout) {

		if (timeout < 0) {
			throw new IllegalArgumentException("Kill timeout must be at least 0.");
		}
		this.timeout = timeout;
		return this;
	}

	public ThreadKiller<T> setTryCount(int tryCount) {

		if (tryCount < 1) {
			throw new IllegalArgumentException("Kill try count must be at least 1.");
		}
		this.tryCount = tryCount;
		return this;
	}

	public boolean killAll() {

		for (int i = 0; i < tryCount && !threads.isEmpty(); i++) {
			killThreads();
		}
		return threads.isEmpty();
	}

	private void killThreads() {

		threads.interruptAll();
		threads.joinAll(Math.max(1, timeout / tryCount));
		threads.removeFinishedThreads();
	}
}

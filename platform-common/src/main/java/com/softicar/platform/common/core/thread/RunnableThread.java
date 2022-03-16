package com.softicar.platform.common.core.thread;

import com.softicar.platform.common.core.thread.collection.ThreadKiller;
import com.softicar.platform.common.core.threading.Threads;
import java.util.Objects;
import java.util.function.Function;

/**
 * Combines a {@link Runnable} and a {@link Thread} object.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class RunnableThread<R extends Runnable> implements IRunnableThread<R> {

	private final R runnable;
	private final Thread thread;

	/**
	 * Constructs a new instance for the given {@link Runnable}.
	 *
	 * @param runnable
	 *            the {@link Runnable} (never <i>null</i>)
	 */
	public RunnableThread(R runnable) {

		this(runnable, Thread::new);
	}

	/**
	 * Constructs a new instance for the given {@link Runnable}, using the given
	 * {@link Thread} factory.
	 *
	 * @param runnable
	 *            the {@link Runnable} (never <i>null</i>)
	 * @param threadFactory
	 *            a factory to create a {@link Thread} for the {@link Runnable}
	 *            (never <i>null</i>)
	 */
	public RunnableThread(R runnable, Function<R, Thread> threadFactory) {

		this.runnable = Objects.requireNonNull(runnable);
		this.thread = Objects.requireNonNull(threadFactory.apply(runnable));
	}

	@Override
	public R getRunnable() {

		return runnable;
	}

	@Override
	public void start() {

		thread.start();
	}

	@Override
	public void interrupt() {

		thread.interrupt();
	}

	@Override
	public boolean kill() {

		return new ThreadKiller<>(thread).killAll();
	}

	@Override
	public boolean isTerminated() {

		return Threads.isTerminated(thread);
	}
}

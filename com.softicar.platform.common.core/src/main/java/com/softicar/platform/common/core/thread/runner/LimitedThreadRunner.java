package com.softicar.platform.common.core.thread.runner;

import com.softicar.platform.common.core.thread.IRunnableThread;
import com.softicar.platform.common.core.thread.RunnableThread;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Runs queued {@link Runnable}s, employing a limited number of concurrent
 * {@link Thread}s.
 *
 * @author Alexander Schmidt
 */
public class LimitedThreadRunner<R extends Runnable> implements ILimitedThreadRunner<R> {

	private final int limit;
	private final Deque<R> runnables;
	private final List<RunnableThread<R>> startedThreads;
	private Function<R, Thread> threadFactory;

	/**
	 * Creates a {@link LimitedThreadRunner}.
	 *
	 * @param limit
	 *            the maximum number of {@link Thread}s that will be run
	 *            concurrently, at any given point in time; clamped to [1,
	 *            {@link Integer#MAX_VALUE}]
	 */
	public LimitedThreadRunner(int limit) {

		this.limit = Math.min(Math.max(1, limit), Integer.MAX_VALUE);
		this.runnables = new ArrayDeque<>();
		this.startedThreads = new ArrayList<>();
		this.threadFactory = Thread::new;
	}

	/**
	 * Calls {@link #addRunnable(Runnable)} for all given {@link Runnable}
	 * objects.
	 *
	 * @param runnables
	 *            the {@link Runnable}s to be added (never <i>null</i>)
	 * @return this
	 */
	@SafeVarargs
	public final LimitedThreadRunner<R> addRunnables(R...runnables) {

		Arrays.asList(runnables).forEach(this::addRunnable);
		return this;
	}

	@Override
	public void addRunnable(R runnable) {

		runnables.add(Objects.requireNonNull(runnable));
	}

	@Override
	public Collection<IRunnableThread<R>> startThreads() {

		Collection<IRunnableThread<R>> threads = new ArrayList<>();
		while (hasAvailableSlots() && !runnables.isEmpty()) {
			threads.add(startNextRunnable());
		}
		return threads;
	}

	@Override
	public boolean hasAvailableSlots() {

		return getAvailableSlots() > 0;
	}

	@Override
	public boolean isFinished() {

		return getStartedThreadCount() == 0 && runnables.isEmpty();
	}

	/**
	 * Allows for customization of the factory that creates {@link Thread}s from
	 * {@link Runnable}s.
	 * <p>
	 * The factory must never return <i>null</i>.
	 *
	 * @param threadFactory
	 *            creates a {@link Thread} from a {@link Runnable} (never null)
	 * @return this {@link LimitedThreadRunner}
	 */
	public LimitedThreadRunner<R> setThreadFactory(Function<R, Thread> threadFactory) {

		this.threadFactory = Objects.requireNonNull(threadFactory);
		return this;
	}

	private int getAvailableSlots() {

		return Math.max(0, limit - getStartedThreadCount());
	}

	private int getStartedThreadCount() {

		removeTerminatedThreads();
		return startedThreads.size();
	}

	private RunnableThread<R> startNextRunnable() {

		R nextRunnable = runnables.pollFirst();
		if (nextRunnable != null) {
			return startRunnableThread(nextRunnable);
		} else {
			return null;
		}
	}

	private RunnableThread<R> startRunnableThread(R runnable) {

		var runnableThread = new RunnableThread<>(runnable, threadFactory);
		startedThreads.add(runnableThread);
		runnableThread.start();
		return runnableThread;
	}

	private void removeTerminatedThreads() {

		var iterator = startedThreads.iterator();
		while (iterator.hasNext()) {
			RunnableThread<R> runnableThread = iterator.next();
			if (runnableThread.isTerminated()) {
				iterator.remove();
			}
		}
	}
}

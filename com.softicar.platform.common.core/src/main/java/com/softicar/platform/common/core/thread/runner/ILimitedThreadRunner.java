package com.softicar.platform.common.core.thread.runner;

import com.softicar.platform.common.core.thread.IRunnableThread;
import java.util.Collection;

/**
 * Interface to run queued {@link Runnable} objects by employing a limited
 * number of concurrent {@link Thread} instances.
 *
 * @param <R>
 *            the type of {@link Runnable} objects
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface ILimitedThreadRunner<R extends Runnable> {

	/**
	 * Starts {@link Thread} instances for as many previously-added
	 * {@link Runnable} objects as possible, respecting the maximum number of
	 * concurrent {@link Thread} instances.
	 *
	 * @return all {@link IRunnableThread} instances started by this method call
	 */
	Collection<IRunnableThread<R>> startThreads();

	/**
	 * Enqueues the given {@link Runnable} to be started as {@link Thread}
	 * later-on.
	 * <p>
	 * The given {@link Runnable} will not be started before
	 * {@link #startThreads} was called.
	 *
	 * @param runnable
	 *            the {@link Runnable} to be added (never <i>null</i>)
	 */
	void addRunnable(R runnable);

	/**
	 * Tests whether there are slots for new {@link Thread} instances to be
	 * started.
	 *
	 * @return <i>true</i> if there are free slots for new {@link Thread}
	 *         instances to be started; <i>false</i> otherwise
	 */
	boolean hasAvailableSlots();

	/**
	 * Determines whether there are either running {@link Thread} instances
	 * and/or queued {@link Runnable} objects.
	 *
	 * @return <i>true</i> if there are running threads or queue entries;
	 *         <i>false</i> otherwise
	 */
	boolean isFinished();
}

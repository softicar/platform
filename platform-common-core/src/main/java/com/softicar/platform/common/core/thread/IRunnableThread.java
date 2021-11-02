package com.softicar.platform.common.core.thread;

/**
 * Represents a {@link Thread} that is created from a given {@link Runnable}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IRunnableThread<R extends Runnable> {

	/**
	 * Returns the {@link Runnable} from which the {@link Thread} is created.
	 *
	 * @return the {@link Runnable} (never <i>null</i>)
	 */
	R getRunnable();

	/**
	 * Starts the {@link Thread} created from the {@link Runnable}.
	 * <p>
	 * May only be called once.
	 */
	void start();

	/**
	 * Interrupts the {@link Thread} created from the {@link Runnable}.
	 */
	void interrupt();

	/**
	 * Kills the {@link Thread} created from the {@link Runnable}.
	 * <p>
	 * Repeatedly sends interrupts as long as either the {@link Thread} is
	 * terminated, or a timeout is encountered.
	 *
	 * @return <i>true</i> if the {@link Thread} was successfully killed;
	 *         <i>false</i> otherwise
	 */
	boolean kill();

	/**
	 * Determines whether the {@link Thread} created from the {@link Runnable}
	 * has been terminated.
	 *
	 * @return <i>true</i> if the {@link Thread} has been terminated;
	 *         <i>false</i> otherwise
	 */
	boolean isTerminated();
}

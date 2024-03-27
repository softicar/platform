package com.softicar.platform.common.core.threading;

import java.lang.Thread.State;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * Some utility methods for {@link Thread}.
 *
 * @author Oliver Richers
 */
public class Threads {

	/**
	 * Joins the given thread.
	 *
	 * @param thread
	 *            the thread to join
	 * @throws InterruptedRuntimeException
	 *             if an {@link InterruptedException} is thrown by
	 *             {@link Thread#join()}.
	 */
	public static void join(Thread thread) {

		try {
			thread.join();
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}

	/**
	 * Joins the given thread with the specified timeout.
	 *
	 * @param thread
	 *            the thread to join
	 * @param millis
	 *            timeout in milliseconds
	 * @throws InterruptedRuntimeException
	 *             if an {@link InterruptedException} is thrown by
	 *             {@link Thread#join()}.
	 */
	public static void join(Thread thread, long millis) {

		try {
			thread.join(millis);
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}

	/**
	 * Runs the given supplier in a child {@link Thread}.
	 *
	 * @param supplier
	 *            the supplier to execute
	 * @return the supplier return value
	 */
	public static <T> T runInChildThread(Supplier<T> supplier) {

		try (var singleThreadExecutor = Executors.newSingleThreadExecutor()) {
			return singleThreadExecutor.submit(() -> supplier.get()).get();
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		} catch (ExecutionException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Determines whether the given {@link Thread} is terminated, as per its
	 * {@link State}.
	 *
	 * @param thread
	 *            the {@link Thread} to check (never <i>null</i>)
	 * @return <i>true</i> if the {@link Thread} is terminated; <i>false</i>
	 *         otherwise
	 */
	public static boolean isTerminated(Thread thread) {

		Objects.requireNonNull(thread);
		return thread.getState() == State.TERMINATED;
	}
}

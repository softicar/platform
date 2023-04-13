package com.softicar.platform.core.module.daemon;

/**
 * Interface of a daemon.
 * <p>
 * A daemon is a concurrently and continuously executed background
 * {@link Thread}. This interface defines the {@link #runIteration()} method
 * which is periodically called to do the work.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 */
@FunctionalInterface
public interface IDaemon {

	/**
	 * Executed once before the daemon's main loop is entered, i.e. before the
	 * very first call to {@link #runIteration()}.
	 * <p>
	 * Does nothing by default.
	 */
	default void setup() {

		// nothing
	}

	/**
	 * Executes a single iteration of the daemon's main loop.
	 * <p>
	 * Implementations must call {@link Thread#sleep(long)}, to throttle
	 * execution.
	 * <p>
	 * Implementations must be interruptible (as in {@link Thread#interrupt()})
	 * at any time, without causing inconsistencies or data corruption.
	 */
	void runIteration();

	/**
	 * Executed once after the daemon's main loop has terminated, i.e. after the
	 * very last call to {@link #runIteration()}.
	 * <p>
	 * Does nothing by default.
	 */
	default void cleanup() {

		// nothing
	}
}

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
	 * Executes setup operations to perform before entering the daemon's main
	 * loop.
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
	 * Executes cleanup operations after the daemon's main loop has terminated.
	 * <p>
	 * Does nothing by default.
	 */
	default void cleanup() {

		// nothing
	}
}

package com.softicar.platform.core.module.daemon.watchdog;

/**
 * Holds a static singleton {@link DaemonWatchdogController} reference.
 *
 * @author Alexander Schmidt
 */
public class DaemonWatchdogControllerSingleton {

	private static final DaemonWatchdogController INSTANCE = new DaemonWatchdogController();

	/**
	 * Returns the singleton {@link DaemonWatchdogController} instance.
	 *
	 * @return the {@link DaemonWatchdogController} instance (never <i>null</i>)
	 */
	public static DaemonWatchdogController get() {

		return INSTANCE;
	}
}

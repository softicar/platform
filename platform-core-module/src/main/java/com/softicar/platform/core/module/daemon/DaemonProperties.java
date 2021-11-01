package com.softicar.platform.core.module.daemon;

import com.softicar.platform.common.core.properties.IProperty;
import com.softicar.platform.common.core.properties.SystemPropertyFactory;
import com.softicar.platform.core.module.daemon.watchdog.DaemonWatchdog;
import com.softicar.platform.core.module.daemon.watched.runnable.WatchedDaemonRunnableManager;

/**
 * Defines system properties related to {@link DaemonWatchdog} and
 * {@link WatchedDaemonRunnableManager} based threading.
 * <p>
 * All time specifications are in <b>seconds</b>, unless designated otherwise.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 */
public class DaemonProperties {

	private static final SystemPropertyFactory FACTORY = new SystemPropertyFactory("com.softicar.platform.core.module.daemon");

	public static final IProperty<Integer> MANAGER_THREAD_START_RETRY_TIMEOUT = FACTORY.createIntegerProperty("manager.thread.start.retry.timeout", 10);

	public static final IProperty<Boolean> WATCHDOG_ENABLED = FACTORY.createBooleanProperty("watchdog.enabled", true);
	public static final IProperty<Integer> WATCHDOG_LOG_ENTRY_LIMIT = FACTORY.createIntegerProperty("watchdog.log.entry.limit", 10000);
	public static final IProperty<Integer> WATCHDOG_SLEEP_MILLIS = FACTORY.createIntegerProperty("watchdog.sleep.millis", 100);

	public static final IProperty<Integer> QUEUED_PROGRAM_EXECUTION_DAEMON_HEARTBEAT_TIMEOUT =//
			FACTORY.createIntegerProperty("queued.program.execution.daemon.heartbeat.timeout", 60);
	public static final IProperty<Integer> QUEUED_PROGRAM_EXECUTION_DAEMON_SLEEP_MILLIS =//
			FACTORY.createIntegerProperty("queued.program.execution.daemon.sleep.millis", 1000);
	public static final IProperty<Integer> QUEUED_PROGRAM_EXECUTION_THREAD_COUNT =//
			FACTORY.createIntegerProperty("queued.program.execution.thread.limit", 4);

	public static final IProperty<Integer> SCHEDULED_PROGRAM_ENQUEUER_DAEMON_HEARTBEAT_TIMEOUT =//
			FACTORY.createIntegerProperty("scheduled.program.enqueuer.daemon.heartbeat.timeout", 90);
}

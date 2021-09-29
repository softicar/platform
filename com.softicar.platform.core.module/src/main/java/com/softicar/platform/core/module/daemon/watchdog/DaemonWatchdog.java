package com.softicar.platform.core.module.daemon.watchdog;

import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.core.throwable.Throwables;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.daemon.DaemonProperties;
import com.softicar.platform.core.module.daemon.IDaemon;
import com.softicar.platform.core.module.daemon.IDaemonDefinition;
import com.softicar.platform.core.module.daemon.watched.runnable.WatchedDaemonRunnableManager;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link Runnable} to serve as a watchdog for daemon threads based on
 * {@link IDaemon}.
 * <p>
 * Starts, terminates and restarts watched threads, as necessary.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DaemonWatchdog implements Runnable {

	private static final String DISPLAY_NAME = "Daemon Watchdog";

	private final Collection<WatchedDaemonRunnableManager> runnableManagers;
	private final int sleepDuration;

	public DaemonWatchdog(Collection<IDaemonDefinition> definitions) {

		this.runnableManagers = createManagers(definitions);
		this.sleepDuration = DaemonProperties.WATCHDOG_SLEEP_MILLIS.getValue();
	}

	@Override
	public void run() {

		try {
			log("Entering main loop.");
			while (true) {
				runnableManagers.forEach(WatchedDaemonRunnableManager::manage);
				Sleep.sleep(sleepDuration);
			}
		} catch (Throwable throwable) {
			if (Throwables.isCausedBy(InterruptedException.class, throwable)) {
				log("Watchdog thread was interrupted.");
			} else {
				log(StackTraceFormatting.getStackTraceAsString(throwable));
			}
		} finally {
			log("Left main loop.");
		}
	}

	public Collection<WatchedDaemonRunnableManager> getRunnableManagers() {

		return runnableManagers;
	}

	public void terminateWatchedDaemonThreads() {

		log("Terminating watched daemon threads.");
		runnableManagers.forEach(WatchedDaemonRunnableManager::terminateThread);
		log("Terminated watched daemon threads.");
	}

	private List<WatchedDaemonRunnableManager> createManagers(Collection<IDaemonDefinition> definitions) {

		return definitions//
			.stream()
			.map(WatchedDaemonRunnableManager::new)
			.collect(Collectors.toList());
	}

	private void log(String format, Object...args) {

		String prefix = DISPLAY_NAME + ": ";
		DaemonWatchdogControllerSingleton.get().log(prefix + format, args);
	}
}

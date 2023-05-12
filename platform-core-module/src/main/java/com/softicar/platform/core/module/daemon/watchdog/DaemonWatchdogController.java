package com.softicar.platform.core.module.daemon.watchdog;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.thread.collection.ThreadKiller;
import com.softicar.platform.common.core.threading.Threads;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.daemon.DaemonProperties;
import com.softicar.platform.core.module.daemon.IDaemonDefinition;
import com.softicar.platform.core.module.daemon.watchdog.log.DaemonWatchdogLogEntry;
import com.softicar.platform.core.module.daemon.watchdog.log.DaemonWatchdogLogEntryList;
import com.softicar.platform.core.module.daemon.watchdog.state.DaemonWatchdogState;
import com.softicar.platform.core.module.daemon.watched.runnable.WatchedDaemonState;
import com.softicar.platform.core.module.program.execution.daemon.ProgramExecutionDaemonDefinition;
import com.softicar.platform.core.module.program.execution.scheduled.ScheduledProgramEnqueuerDaemonDefinition;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Provides functionality to start and stop the daemon watchdog thread, to
 * retrieve state information, and to write related logs.
 *
 * @author Alexander Schmidt
 */
public class DaemonWatchdogController {

	private final DaemonWatchdogLogEntryList logs = new DaemonWatchdogLogEntryList();
	private volatile DaemonWatchdog runnable = null;
	private volatile Thread thread = null;
	private volatile Instant startedAt = null;
	private volatile Instant stoppedAt = null;

	DaemonWatchdogController() {

		// avoid instantiation from other packages
	}

	public synchronized void start() {

		log("**** Watchdog start requested. ****");

		if (isEnabled()) {
			log("Watchdog is enabled.");

			if (isStartable()) {
				log("Starting watchdog thread.");

				runnable = new DaemonWatchdog(getDaemonDefinitions());
				thread = new Thread(runnable);
				thread.start();
				log("Watchdog thread started.");

				startedAt = CurrentClock.get().instant();
				stoppedAt = null;
			} else {
				log("Watchdog thread could not be started. Please wait.");
			}
		} else {
			log("Watchdog is not enabled. Nothing to do.");
		}
	}

	public synchronized void stop() {

		log("**** Watchdog stop requested. ****");

		if (isThreadPresent()) {
			log("Terminating watchdog thread.");
			terminateWatchdogThread();

			log("Terminating watched daemon threads.");
			runnable.terminateWatchedDaemonThreads();

			log("All threads terminated.");

			runnable = null;
			thread = null;

			startedAt = null;
			stoppedAt = CurrentClock.get().instant();
		} else {
			log("Watchdog thread is not present. Nothing to do.");
		}
	}

	public DaemonWatchdogState getState() {

		return new DaemonWatchdogState(//
			isThreadPresent(),
			isThreadAlive(),
			startedAt,
			stoppedAt,
			logs.getEntries(),
			getWatchedDaemonStates());
	}

	public void log(String format, Object...args) {

		logs.add(new DaemonWatchdogLogEntry(CurrentClock.get().instant(), String.format(format, args)));
	}

	private Boolean isEnabled() {

		return DaemonProperties.WATCHDOG_ENABLED.getValue();
	}

	private boolean isStartable() {

		return thread == null || isThreadTerminated();
	}

	private boolean isThreadPresent() {

		return thread != null;
	}

	private boolean isThreadAlive() {

		return thread != null && thread.isAlive();
	}

	private boolean isThreadTerminated() {

		return Threads.isTerminated(thread);
	}

	private Collection<IDaemonDefinition> getDaemonDefinitions() {

		return List
			.of(//
				new ScheduledProgramEnqueuerDaemonDefinition(),
				new ProgramExecutionDaemonDefinition());
	}

	private List<WatchedDaemonState> getWatchedDaemonStates() {

		return Optional//
			.ofNullable(runnable)
			.map(DaemonWatchdog::getRunnableManagers)
			.orElse(Collections.emptyList())//
			.stream()
			.map(WatchedDaemonState::new)
			.collect(Collectors.toList());
	}

	private void terminateWatchdogThread() {

		try {
			log("Terminating watchdog thread.");
			if (new ThreadKiller<>(thread).killAll()) {
				log("Watchdog thread was terminated.");
			} else {
				log("Failed to terminate watchdog thread. Giving up.");
			}
		} catch (Exception exception) {
			log(//
				"Encountered an exception while terminating watchdog thread. Giving up.\n%s",
				StackTraceFormatting.getStackTraceAsString(exception));
		}
	}
}

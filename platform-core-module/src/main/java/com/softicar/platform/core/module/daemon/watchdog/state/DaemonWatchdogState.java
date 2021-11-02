package com.softicar.platform.core.module.daemon.watchdog.state;

import com.softicar.platform.core.module.daemon.watchdog.DaemonWatchdog;
import com.softicar.platform.core.module.daemon.watchdog.log.DaemonWatchdogLogEntry;
import com.softicar.platform.core.module.daemon.watched.runnable.WatchedDaemonState;
import java.time.Instant;
import java.util.List;

/**
 * Provides state information about a {@link DaemonWatchdog}.
 * <p>
 * All contained information is effectively immutable.
 *
 * @author Alexander Schmidt
 */
public class DaemonWatchdogState {

	private final boolean threadExists;
	private final boolean threadAlive;
	private final Instant startedAt;
	private final Instant stoppedAt;
	private final List<DaemonWatchdogLogEntry> logEntries;
	private final List<WatchedDaemonState> watchedDaemonStates;

	public DaemonWatchdogState(boolean threadExists, boolean threadAlive, Instant startedAt, Instant stoppedAt, List<DaemonWatchdogLogEntry> logEntries,
			List<WatchedDaemonState> watchedDaemonStates) {

		this.threadExists = threadExists;
		this.threadAlive = threadAlive;
		this.startedAt = startedAt;
		this.stoppedAt = stoppedAt;
		this.logEntries = logEntries;
		this.watchedDaemonStates = watchedDaemonStates;
	}

	public boolean isThreadExists() {

		return threadExists;
	}

	public boolean isThreadAlive() {

		return threadAlive;
	}

	public Instant getStartedAt() {

		return startedAt;
	}

	public Instant getStoppedAt() {

		return stoppedAt;
	}

	public List<DaemonWatchdogLogEntry> getLogEntries() {

		return logEntries;
	}

	public List<WatchedDaemonState> getWatchedDaemonStates() {

		return watchedDaemonStates;
	}
}

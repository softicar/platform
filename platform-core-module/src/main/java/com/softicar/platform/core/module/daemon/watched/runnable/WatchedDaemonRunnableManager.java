package com.softicar.platform.core.module.daemon.watched.runnable;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.thread.collection.ThreadKiller;
import com.softicar.platform.common.core.threading.Threads;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.daemon.DaemonProperties;
import com.softicar.platform.core.module.daemon.IDaemonDefinition;
import com.softicar.platform.core.module.daemon.watchdog.DaemonWatchdogControllerSingleton;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class WatchedDaemonRunnableManager {

	private final IDaemonDefinition daemonDefinition;
	private final Duration threadStartRetryTimeout;
	private volatile Thread daemonThread;
	private volatile Instant lastHeartbeat;
	private volatile boolean iterationEnabled;
	private Instant lastThreadStartFailure;

	public WatchedDaemonRunnableManager(IDaemonDefinition daemonDefinition) {

		this.daemonDefinition = Objects.requireNonNull(daemonDefinition);
		this.threadStartRetryTimeout = Duration.ofSeconds(DaemonProperties.MANAGER_THREAD_START_RETRY_TIMEOUT.getValue());
		this.daemonThread = null;
		this.lastHeartbeat = null;
		this.iterationEnabled = true;
		this.lastThreadStartFailure = null;
	}

	public IDaemonDefinition getDefinition() {

		return daemonDefinition;
	}

	public boolean isDaemonThreadPresent() {

		return daemonThread != null;
	}

	public boolean isDaemonThreadAlive() {

		return daemonThread != null && daemonThread.isAlive();
	}

	public Instant getLastHeartbeat() {

		return lastHeartbeat;
	}

	public void updateHeartbeat() {

		this.lastHeartbeat = CurrentClock.get().instant();
	}

	public boolean isIterationEnabled() {

		return iterationEnabled;
	}

	public void manage() {

		if (isDaemonThreadPresent()) {
			if (Threads.isTerminated(daemonThread)) {
				reset();
				log("Internal state was reset because a daemon thread was unexpectedly terminated.");
			}
			if (needsForcedTermination()) {
				terminateThread();
			}
		}

		if (!isDaemonThreadPresent() && isIterationEnabled()) {
			startNewThread();
		}
	}

	public void terminateThread() {

		log("Daemon thread termination requested.");

		if (isDaemonThreadPresent()) {
			try {
				log("Interrupting daemon thread.");
				if (new ThreadKiller<>(daemonThread).killAll()) {
					log("Daemon thread was terminated.");
				} else {
					log("Failed to terminate daemon thread. Giving up.");
				}
			} catch (Exception exception) {
				log(//
					"Encountered an exception while terminating daemon thread. Giving up.\n%s",
					StackTraceFormatting.getStackTraceAsString(exception));
			}
			reset();
		} else {
			log("No daemon thread present. Nothing to do.");
		}
	}

	public void log(String format, Object...args) {

		String prefix = daemonDefinition.toDisplay().concatColon().concatSpace().toString();
		DaemonWatchdogControllerSingleton.get().log(prefix + format, args);
	}

	private void startNewThread() {

		if (isStartable()) {
			try {
				reset();
				this.daemonThread = new Thread(new WatchedDaemonRunnable(this, daemonDefinition.create()));
				this.daemonThread.start();
				log("Started a daemon thread.");
			} catch (Exception exception) {
				this.lastThreadStartFailure = CurrentClock.get().instant();
				reset();
				log(//
					"Failed to start a daemon thread:\n%s",
					StackTraceFormatting.getStackTraceAsString(exception));
			}
		} else {
			log("Could not start a daemon thread.");
		}
	}

	private boolean isStartable() {

		return lastThreadStartFailure == null || isStartRetryTimeoutElapsed();
	}

	private boolean isStartRetryTimeoutElapsed() {

		return CurrentClock.get().instant().isAfter(lastThreadStartFailure.plus(threadStartRetryTimeout));
	}

	private void reset() {

		this.daemonThread = null;
		this.lastHeartbeat = null;
	}

	private boolean needsForcedTermination() {

		return lastHeartbeat != null && CurrentClock.get().instant().isAfter(getHeartbeatTimeout());
	}

	// FIXME the name of this method is confusing
	private Instant getHeartbeatTimeout() {

		return lastHeartbeat.plus(daemonDefinition.getKillTimeout());
	}
}

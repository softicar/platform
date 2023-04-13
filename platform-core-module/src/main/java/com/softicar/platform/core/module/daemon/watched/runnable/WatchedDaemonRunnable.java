package com.softicar.platform.core.module.daemon.watched.runnable;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.logging.LogOutputScope;
import com.softicar.platform.common.core.throwable.Throwables;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.daemon.IDaemon;
import com.softicar.platform.db.core.connection.DbConnections;
import java.util.Objects;

class WatchedDaemonRunnable implements Runnable {

	private final WatchedDaemonRunnableManager manager;
	private final IDaemon daemon;

	public WatchedDaemonRunnable(WatchedDaemonRunnableManager manager, IDaemon daemon) {

		this.manager = Objects.requireNonNull(manager);
		this.daemon = Objects.requireNonNull(daemon);
	}

	@Override
	public final void run() {

		try (LogOutputScope scope = new LogOutputScope(manager::log)) {
			try {
				daemon.setup();
				while (manager.isIterationEnabled()) {
					manager.updateHeartbeat();
					daemon.runIteration();
					Thread.yield();
				}
			} catch (Throwable throwable) {
				logException(throwable);
			} finally {
				performDaemonCleanup();
				DbConnections.closeAll();
			}
		}
	}

	private void logException(Throwable throwable) {

		if (Throwables.isCausedBy(InterruptedException.class, throwable)) {
			Log.ferror("Daemon thread was interrupted.");
		} else {
			Log.ferror("Daemon thread encountered an exception.\n%s", StackTraceFormatting.getStackTraceAsString(throwable));
		}
	}

	private void performDaemonCleanup() {

		try {
			daemon.cleanup();
		} catch (Exception exception) {
			Log.ferror("Failed to execute cleanup operations.\n%s", StackTraceFormatting.getStackTraceAsString(exception));
		}
	}
}

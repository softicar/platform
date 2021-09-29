package com.softicar.platform.core.module.daemon.watched.runnable;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.daemon.IDaemon;
import java.time.Duration;
import java.time.Instant;

/**
 * Provides state information about a running {@link IDaemon}.
 * <p>
 * All contained information is effectively immutable.
 *
 * @author Alexander Schmidt
 */
public class WatchedDaemonState {

	private final IDisplayString name;
	private final Duration killTimeout;
	private final Instant lastHeartbeat;
	private final boolean workerThreadPresent;
	private final boolean workerThreadAlive;

	public WatchedDaemonState(WatchedDaemonRunnableManager manager) {

		this.name = manager.getDefinition().toDisplay();
		this.killTimeout = manager.getDefinition().getKillTimeout();
		this.lastHeartbeat = manager.getLastHeartbeat();
		this.workerThreadPresent = manager.isDaemonThreadPresent();
		this.workerThreadAlive = manager.isDaemonThreadAlive();
	}

	public IDisplayString getName() {

		return name;
	}

	public Duration getKillTimeout() {

		return killTimeout;
	}

	public Instant getLastHeartbeat() {

		return lastHeartbeat;
	}

	public boolean isWorkerThreadPresent() {

		return workerThreadPresent;
	}

	public boolean isWorkerThreadAlive() {

		return workerThreadAlive;
	}
}

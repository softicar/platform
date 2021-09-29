package com.softicar.platform.db.core.connection.tracker;

import com.softicar.platform.common.core.clock.CurrentClock;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * This class keeps track of all opened database connections.
 * <p>
 * This class is a singleton.
 *
 * @author Oliver Richers
 */
public class DbConnectionTracker {

	public static final long MAX_CONNECTION_IDLE_MILLIS = 5 * 60 * 1000;
	public static final long FULL_LOG_INTERVAL = 1 * 60 * 1000;
	private static final DbConnectionTracker SINGLETON = new DbConnectionTracker();
	private final Map<Integer, DbConnectionTrack> connectionTracks = new ConcurrentHashMap<>();
	private final AtomicInteger connectionCounter = new AtomicInteger(0);
	private final AtomicLong lastFullLog = new AtomicLong(0);
	private final Consumer<Collection<DbConnectionTrack>> output;

	public static DbConnectionTracker getSingleton() {

		return SINGLETON;
	}

	public DbConnectionTracker() {

		this(new DbConnectionTrackerLogger());
	}

	public DbConnectionTracker(Consumer<Collection<DbConnectionTrack>> output) {

		this.output = output;
	}

	public int notifyNewConnection() {

		logIdleConnections();

		// allocate index and create connection data
		int index = connectionCounter.getAndIncrement();
		DbConnectionTrack track = new DbConnectionTrack(index, CurrentClock.get().millis());
		connectionTracks.put(index, track);
		return index;
	}

	public void notifyConnectionClosed(int connectionIndex) {

		// remove connection data
		connectionTracks.remove(connectionIndex);
	}

	public void notifyConnectionUsed(int connectionIndex) {

		DbConnectionTrack track = connectionTracks.get(connectionIndex);
		if (track != null) {
			track.updateLastUsedAt(CurrentClock.get().millis());
		}
	}

	private void logIdleConnections() {

		long now = CurrentClock.get().millis();
		long last = lastFullLog.get();
		if (now > last + FULL_LOG_INTERVAL && lastFullLog.compareAndSet(last, now)) {
			Collection<DbConnectionTrack> idleConnectionTracks = getIdleConnectionTracks();
			if (!idleConnectionTracks.isEmpty()) {
				output.accept(idleConnectionTracks);
			}
		}
	}

	private Collection<DbConnectionTrack> getIdleConnectionTracks() {

		long minimum = CurrentClock.get().millis() - MAX_CONNECTION_IDLE_MILLIS;

		return connectionTracks//
			.values()
			.stream()
			.filter(track -> track.getLastUsedAt() < minimum)
			.collect(Collectors.toList());
	}
}

package com.softicar.platform.db.core.connection.tracker;

class DbConnectionTrack {

	private final int connectionIndex;
	private final StackTraceElement[] stackTrace;
	private final long openedAt;
	private long lastUsedAt;

	public DbConnectionTrack(int connectionIndex, long now) {

		this.connectionIndex = connectionIndex;
		this.stackTrace = Thread.currentThread().getStackTrace();
		this.openedAt = now;
		this.lastUsedAt = now;
	}

	public int getConnectionIndex() {

		return connectionIndex;
	}

	public StackTraceElement[] getStackTrace() {

		return stackTrace;
	}

	public long getOpenedAt() {

		return openedAt;
	}

	public long getLastUsedAt() {

		return lastUsedAt;
	}

	public void updateLastUsedAt(long now) {

		this.lastUsedAt = now;
	}
}

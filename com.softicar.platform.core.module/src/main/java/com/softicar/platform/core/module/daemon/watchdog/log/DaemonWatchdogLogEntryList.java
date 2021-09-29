package com.softicar.platform.core.module.daemon.watchdog.log;

import com.softicar.platform.core.module.daemon.DaemonProperties;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DaemonWatchdogLogEntryList {

	private final int maxEntries;
	private final Deque<DaemonWatchdogLogEntry> entries;

	public DaemonWatchdogLogEntryList() {

		this.maxEntries = DaemonProperties.WATCHDOG_LOG_ENTRY_LIMIT.getValue();
		this.entries = new ArrayDeque<>();
	}

	public synchronized void add(DaemonWatchdogLogEntry entry) {

		entries.add(entry);
		if (entries.size() > maxEntries) {
			entries.removeFirst();
		}
	}

	public synchronized List<DaemonWatchdogLogEntry> getEntries() {

		return new ArrayList<>(entries);
	}
}

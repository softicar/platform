package com.softicar.platform.core.module.daemon.watchdog.log;

import java.time.Instant;
import java.util.Objects;

public class DaemonWatchdogLogEntry {

	private final Instant time;
	private final String text;

	public DaemonWatchdogLogEntry(Instant time, String text) {

		this.time = Objects.requireNonNull(time);
		this.text = Objects.requireNonNull(text);
	}

	public Instant getTime() {

		return time;
	}

	public String getText() {

		return text;
	}
}

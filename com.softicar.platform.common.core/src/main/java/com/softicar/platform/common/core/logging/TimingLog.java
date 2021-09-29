package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.singleton.Singleton;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Utility class for measuring and logging of elapsed time.
 *
 * @author Oliver Richers
 */
public class TimingLog {

	private static final Singleton<Map<String, TimingEntry>> ENTRY_MAP = new Singleton<>(TreeMap::new);

	public static void begin(String name) {

		TimingEntry unit = getEntryMap().get(name);
		if (unit == null) {
			getEntryMap().put(name, unit = new TimingEntry(name));
		}
		unit.begin = new Date();
	}

	public static void end(String name) {

		TimingEntry unit = getEntryMap().get(name);
		++unit.count;
		unit.elapsed += System.currentTimeMillis() - unit.begin.getTime();
	}

	public static long getElapsed(String name) {

		TimingEntry unit = getEntryMap().get(name);

		if (unit != null) {
			return unit.elapsed;
		} else {
			return 0;
		}
	}

	public static void log() {

		Log.info(">>>--- TIMING --->>>");
		for (TimingEntry unit: getEntryMap().values()) {
			Log.info("" + unit);
		}
		Log.info("<<<--- TIMING ---<<<");
	}

	public static void logAsWarning() {

		Log.warning(">>>--- TIMING --->>>");
		for (TimingEntry unit: getEntryMap().values()) {
			Log.warning("" + unit);
		}
		Log.warning("<<<--- TIMING ---<<<");
	}

	private static Map<String, TimingEntry> getEntryMap() {

		return ENTRY_MAP.get();
	}

	private static class TimingEntry {

		public final String name;
		public int count = 0;
		public long elapsed = 0;
		public Date begin;

		public TimingEntry(String name) {

			this.name = name;
		}

		@Override
		public String toString() {

			return name + ": count(" + count + ") elapsed(" + elapsed + "ms) avg(" + elapsed / count + "ms)";
		}
	}
}

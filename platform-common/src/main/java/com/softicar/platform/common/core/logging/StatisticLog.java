package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.singleton.Singleton;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Oliver Richers
 */
public class StatisticLog {

	private final static Singleton<Map<String, StatisticLog>> STATISTICS_MAP = new Singleton<>(TreeMap::new);
	private final String name;
	private final long start_time = System.currentTimeMillis();
	private final long maxCount;
	private final long printInterval;
	private final int logLevel;
	private long last_time = start_time;
	private long count = 0;

	private StatisticLog(String name, long maxCount, long printInterval, int logLevel) {

		this.name = name;
		this.maxCount = maxCount;
		this.printInterval = printInterval;
		this.logLevel = logLevel;
	}

	private long elapsed() {

		long elapsed = System.currentTimeMillis() - start_time;
		return elapsed == 0? 1 : elapsed;
	}

	private long perSecond() {

		return 1000 * count / elapsed();
	}

	private void printIncLog() {

		if (maxCount >= 0) {
			Log.log(logLevel, name + ": " + count + " of " + maxCount + " done, " + perSecond() + " per second.");
		} else {
			Log.log(logLevel, name + " count: " + count + ", " + perSecond() + " per second.");
		}
	}

	private void printStopLog() {

		Log.log(logLevel, name + ": " + count + " done in " + elapsed() / 1000.0 + " seconds, " + perSecond() + " per second.");
	}

	private void inc(int increment) {

		count += increment;
		if (System.currentTimeMillis() - last_time > printInterval) {
			last_time = System.currentTimeMillis();
			printIncLog();
		}
	}

	public static void start(String name, long max_count, long interval, int log_level) {

		getStatisticsMap().put(name, new StatisticLog(name, max_count, interval, log_level));
	}

	public static void start(String name, long max_count) {

		start(name, max_count, 5000, Log.INFO_LEVEL);
	}

	public static void start(String name) {

		start(name, -1);
	}

	public static void stop(String name) {

		StatisticLog s = getStatisticsMap().get(name);
		s.printStopLog();
		getStatisticsMap().remove(name);
	}

	public static void inc(String name, int increment) {

		StatisticLog s = getStatisticsMap().get(name);
		s.inc(increment);
	}

	public static void inc(String name) {

		inc(name, 1);
	}

	private static Map<String, StatisticLog> getStatisticsMap() {

		return STATISTICS_MAP.get();
	}
}

package com.softicar.platform.common.core.logging;

import com.softicar.platform.common.core.singleton.Singleton;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class contains a legacy logging mechanism.
 *
 * @author Oliver Richers
 */
public class IntervalLog {

	private static final Singleton<Map<String, Long>> TIMINGS_MAP = new Singleton<>(TreeMap::new);
	private static final long LOG_INTERVAL = 5000;

	/**
	 * Outputs log messages every 5 seconds. Not every call to this function
	 * will output a line. The first time and then every 5 seconds a new message
	 * within the same domain will come through.
	 *
	 * @param logLevel
	 *            the log level @see log()
	 * @param domain
	 *            the domain of the message
	 * @param msg
	 *            the message text
	 */
	public static void intervalLog(int logLevel, String domain, String msg) {

		Map<String, Long> timingsMap = getTimingsMap();

		long now = System.currentTimeMillis();
		Long lastTime = timingsMap.get(domain);
		if (lastTime == null || now - lastTime.longValue() >= LOG_INTERVAL) {
			Log.log(logLevel, domain + ": " + msg);
			timingsMap.put(domain, now);
		}
	}

	private static Map<String, Long> getTimingsMap() {

		return TIMINGS_MAP.get();
	}
}

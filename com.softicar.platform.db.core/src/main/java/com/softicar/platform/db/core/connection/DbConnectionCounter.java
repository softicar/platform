package com.softicar.platform.db.core.connection;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.singleton.Singleton;

/**
 * This class keeps track of the number of open database connections.
 * <p>
 * A panic message is sent if a thread opens too many database connection.
 *
 * @author Oliver Richers
 */
public class DbConnectionCounter {

	private static final int MAX_CONNECTIONS = 16;
	private static final Singleton<Integer> COUNTER = new Singleton<>(() -> 0);

	public static void increment() {

		int value = get() + 1;

		if (value > MAX_CONNECTIONS) {
			throw new SofticarDeveloperException("Number of concurrent database connection exceeded %d.", MAX_CONNECTIONS);
		}

		COUNTER.set(value);
	}

	public static void decrement() {

		int value = get() - 1;
		if (value >= 0) {
			COUNTER.set(value);
		}
	}

	public static int get() {

		return COUNTER.get();
	}
}

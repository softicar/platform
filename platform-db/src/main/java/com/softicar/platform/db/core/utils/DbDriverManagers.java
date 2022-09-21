package com.softicar.platform.db.core.utils;

import com.softicar.platform.common.container.iterable.IteratorIterable;
import com.softicar.platform.common.core.logging.Log;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Maintenance methods for {@link DriverManager}.
 *
 * @author Oliver Richers
 */
public abstract class DbDriverManagers {

	/**
	 * Deregisters all loaded JDBC drivers from the {@link DriverManager}.
	 */
	public static void deregisterAllDrivers() {

		Enumeration<Driver> drivers = DriverManager.getDrivers();
		for (Driver driver: IteratorIterable.create(drivers)) {
			try {
				DriverManager.deregisterDriver(driver);
				Log.finfo("Deregistered JDBC driver '%s'.", driver);
			} catch (SQLException exception) {
				Log.ferror("Failed to deregister JDBC driver '%s'.", driver);
				exception.printStackTrace();
			}
		}
	}
}

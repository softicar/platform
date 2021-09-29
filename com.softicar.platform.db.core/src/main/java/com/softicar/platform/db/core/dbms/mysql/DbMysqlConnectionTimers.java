package com.softicar.platform.db.core.dbms.mysql;

import com.softicar.platform.common.core.logging.Log;
import java.lang.reflect.Field;
import java.sql.Driver;
import java.util.Timer;

/**
 * Maintenance methods for MySQL JDBC {@link Driver}.
 *
 * @author Oliver Richers
 */
public abstract class DbMysqlConnectionTimers {

	/**
	 * Cancels the MySQL connection timer to prevent memory leaks.
	 * <p>
	 * This method is a hack to work around a bug in the MySQL connector.
	 * Starting with MySQL connector 5.1.17, this hack is not necessary anymore.
	 */
	public static void cancelMySqlConnectionTimer() {

		try {
			Class<?> connectionClass = Class.forName("com.mysql.jdbc.ConnectionImpl");
			Field timerField = connectionClass.getDeclaredField("cancelTimer");
			timerField.setAccessible(true);
			Timer timer = (Timer) timerField.get(null);
			timer.cancel();
			Log.finfo("Successfull shutdown of the MySQL connection timer.");
		} catch (Exception exception) {
			Log.fwarning("Failed shutdown of the MySQL connection timer.");
			exception.printStackTrace();
		}
	}
}

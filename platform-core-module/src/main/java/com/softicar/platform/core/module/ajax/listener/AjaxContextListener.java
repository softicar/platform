package com.softicar.platform.core.module.ajax.listener;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.core.module.daemon.watchdog.DaemonWatchdogControllerSingleton;
import com.softicar.platform.db.core.connection.pool.DbConnectionPoolMap;
import com.softicar.platform.db.core.dbms.mysql.DbMysqlConnectionTimers;
import com.softicar.platform.db.core.utils.DbDriverManagers;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Keeps track of the shared {@link ServletContext}.
 * <p>
 * WARNING: DO NOT RENAME OR MOVE THIS FILE.
 *
 * @author Oliver Richers
 */
@WebListener
public class AjaxContextListener implements ServletContextListener {

	private static ServletContext servletContext;

	public static ServletContext getServletContext() {

		return servletContext;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Log.finfo("CONTEXT STARTUP: %s", sce.getServletContext().getContextPath());
		servletContext = sce.getServletContext();
		new IioRegistryManager().registerProviders();
		DaemonWatchdogControllerSingleton.get().start();
		Log.finfo("CONTEXT INITIALIZED: %s", sce.getServletContext().getContextPath());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		Log.finfo("CONTEXT SHUTDOWN: %s", sce.getServletContext().getContextPath());
		DaemonWatchdogControllerSingleton.get().stop();
		DbDriverManagers.deregisterAllDrivers();
		DbMysqlConnectionTimers.cancelMySqlConnectionTimer();
		DbConnectionPoolMap.getSingleton().closeAllAndClear();
		new BatikCleanerThreadManager().shutdownThread();
		new IioRegistryManager().deregisterProviders();
		servletContext = null;
		Log.finfo("CONTEXT DESTROYED: %s", sce.getServletContext().getContextPath());
	}
}

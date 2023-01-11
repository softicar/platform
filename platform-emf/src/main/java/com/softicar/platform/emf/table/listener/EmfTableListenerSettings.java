package com.softicar.platform.emf.table.listener;

import com.softicar.platform.db.core.connection.DbConnections;

public class EmfTableListenerSettings {

	private static final boolean DEFAULT_LOGGING_ENABLED = true;
	private boolean loggingEnabled;

	public EmfTableListenerSettings() {

		this.loggingEnabled = DEFAULT_LOGGING_ENABLED;
	}

	public static void setLoggingEnabled(boolean loggingEnabled) {

		DbConnections//
			.getOrPutTransactionData(EmfTableListenerSettings.class, EmfTableListenerSettings::new)
			.ifPresent(settings -> settings.loggingEnabled = loggingEnabled);
	}

	public static boolean isLoggingEnabled() {

		return DbConnections//
			.getTransactionData(EmfTableListenerSettings.class)
			.map(settings -> settings.loggingEnabled)
			.orElse(DEFAULT_LOGGING_ENABLED);
	}
}

package com.softicar.platform.emf.table.listener;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.transaction.DbTransaction;

/**
 * Configures EMF specific settings for the current {@link DbTransaction}.
 *
 * @author Oliver Richers
 */
public class EmfTableListenerSettings {

	private static final boolean DEFAULT_LOGGING_ENABLED = true;
	private boolean loggingEnabled;

	public EmfTableListenerSettings() {

		this.loggingEnabled = DEFAULT_LOGGING_ENABLED;
	}

	/**
	 * Enables or disabled the writing of log records during the current
	 * {@link DbTransaction}.
	 * <p>
	 * The default is <i>true</i>, thus log records will written by default.
	 *
	 * @param loggingEnabled
	 *            <i>true</i> to enable writing of log records; <i>false</i> to
	 *            disable writing of log records
	 */
	public static void setLoggingEnabled(boolean loggingEnabled) {

		DbConnections//
			.getOrPutTransactionData(EmfTableListenerSettings.class, EmfTableListenerSettings::new)
			.ifPresent(settings -> settings.loggingEnabled = loggingEnabled);
	}

	/**
	 * Returns whether log records shall be written during the current
	 * {@link DbTransaction}.
	 *
	 * @return <i>true</i> if log records shall be written; <i>false</i>
	 *         otherwise
	 */
	public static boolean isLoggingEnabled() {

		return DbConnections//
			.getTransactionData(EmfTableListenerSettings.class)
			.map(settings -> settings.loggingEnabled)
			.orElse(DEFAULT_LOGGING_ENABLED);
	}
}

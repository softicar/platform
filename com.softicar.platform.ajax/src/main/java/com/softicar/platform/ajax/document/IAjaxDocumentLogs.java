package com.softicar.platform.ajax.document;

import com.softicar.platform.common.date.DayTime;

/**
 * Keeps all the log information about an {@link IAjaxDocument} instance.
 * <p>
 * The logged data is the last access time, the current request index and the
 * estimated time and memory consumption of the {@link IAjaxDocument}.
 *
 * @author Oliver Richers
 */
public interface IAjaxDocumentLogs {

	/**
	 * Returns the time of the last access of the {@link IAjaxDocument}.
	 *
	 * @return last {@link IAjaxDocument} access time (never null)
	 */
	DayTime getLastAccess();

	/**
	 * Sets the last access to the specified value.
	 *
	 * @param lastAccess
	 *            the new last access time (not null)
	 */
	void setLastAccess(DayTime lastAccess);

	/**
	 * Sets the last access to now.
	 */
	void updateLastAccess();

	/**
	 * Returns the amount of seconds that the {@link IAjaxDocument} is idle.
	 *
	 * @return number of idling seconds
	 */
	double getIdleSeconds();
}

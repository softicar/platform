package com.softicar.platform.core.module.event;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.page.badge.EmfPageBadge;
import com.softicar.platform.emf.page.badge.EmfPageErrorBadge;

/**
 * Utility methods for {@link AGSystemEvent}.
 *
 * @author Oliver Richers
 */
public class SystemEvents {

	/**
	 * Returns the number of {@link AGSystemEvent} records that need to be
	 * confirmed.
	 *
	 * @return the {@link AGSystemEvent} record count with pending confirmation
	 */
	public static int getNumberOfEventsToConfirm() {

		return AGSystemEvent.TABLE//
			.createSelect()
			.where(AGSystemEvent.NEEDS_CONFIRMATION)
			.count();
	}

	/**
	 * Returns an {@link EmfPageBadge} for {@link AGSystemEvent} records that
	 * need to be confirmed.
	 *
	 * @return an {@link EmfPageBadge} for {@link AGSystemEvent} records that
	 *         need to be confirmed (never <i>null</i>)
	 */
	public static EmfPageBadge getPageBadgeForNumberOfEventsToConfirm() {

		return new EmfPageErrorBadge(SystemEvents::getNumberOfEventsToConfirm)//
			.setTitle(CoreI18n.UNCONFIRMED_EVENTS)
			.setRefreshClasses(AGSystemEvent.class);
	}
}

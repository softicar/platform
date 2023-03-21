package com.softicar.platform.core.module.page.header.notification;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;

public class PageHeaderMaintenanceNotificationArea extends DomDiv {

	public PageHeaderMaintenanceNotificationArea() {

		addCssClass(CoreCssClasses.PAGE_HEADER_MAINTENANCE_NOTIFICATION_AREA);

		AGMaintenanceWindow.getTodaysMaintenanceIfPresent().ifPresent(window -> {
			IDisplayString message = CoreI18n.PENDING_MAINTENANCE_FROM_ARG1_TO_ARG2.toDisplay(getStartTime(window), getEndTime(window));
			appendChild(new DomMessageDiv(DomMessageType.WARNING, message));
		});
	}

	private String getStartTime(AGMaintenanceWindow window) {

		return window.getExpectedStart().getTimeAsStringHM();
	}

	private String getEndTime(AGMaintenanceWindow window) {

		return window.getExpectedEnd().getTimeAsStringHM();
	}
}

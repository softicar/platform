package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;

public class ProgramMaintenanceMessageDiv extends DomBar {

	private final DomMessageDiv messageDiv;

	public ProgramMaintenanceMessageDiv() {

		this.messageDiv = new DomMessageDiv(
			DomMessageType.WARNING,
			CoreI18n.MAINTENANCE_IS_CURRENTLY_IN_PROGRESS//
				.concat("\n")
				.concatSentence(CoreI18n.PROGRAMS_WILL_NOT_BE_EXECUTED)
				.concat("\n")
				.concatSentence(CoreI18n.PROGRAM_EXECUTION_WILL_RESUME_AFTER_MAINTENANCE));
		if (AGMaintenanceWindow.isMaintenanceInProgress()) {
			appendChild(messageDiv);
		}
	}
}

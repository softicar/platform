package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;

public class ProgramMaintenanceMessageBar extends DomBar {

	private final DomMessageDiv messageDiv;

	public ProgramMaintenanceMessageBar() {

		this.messageDiv = new DomMessageDiv(
			DomMessageType.WARNING,
			CoreI18n.MAINTENANCE_IS_CURRENTLY_IN_PROGRESS//
				.concat("\n")
				.concat(CoreI18n.NO_NEW_PROGRAM_EXECUTIONS_WILL_BE_LAUNCHED)
				.concat("\n")
				.concat(CoreI18n.CURRENT_PROGRAM_EXECUTIONS_WILL_CONTINUE_TO_RUN_UNTIL_THEY_ARE_FINISHED)
				.concat("\n")
				.concat(CoreI18n.PROGRAM_EXECUTION_WILL_RESUME_AFTER_MAINTENANCE));
		if (AGMaintenanceWindow.isMaintenanceInProgress()) {
			appendChild(messageDiv);
		}
	}
}

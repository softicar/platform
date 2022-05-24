package com.softicar.platform.core.module.start.page;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.event.AGSystemEvent;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.impersonation.UserImpersonationSessionManager;
import com.softicar.platform.core.module.user.impersonation.UserImpersonationTerminationDiv;
import com.softicar.platform.core.module.user.password.change.UserPasswordChangeDiv;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class StartPageDiv extends DomDiv {

	public StartPageDiv() {

		var sections = new ArrayList<IDomElement>();
		addPendingSystemEventsSection(sections);
		addPendingMaintenanceSection(sections);
		addPasswordChangeSection(sections);
		addUserImpersonationTerminationSection(sections);
		appendSectionsSeparatedByHr(sections);

		addMarker(StartPageMarker.MAIN_ELEMENT);
	}

	private void addPendingSystemEventsSection(Collection<IDomElement> sections) {

		if (AGSystemEvent.TABLE.createSelect().where(AGSystemEvent.NEEDS_ATTENTION).exists()) {
			sections.add(new EmfManagementDivBuilder<>(AGSystemEvent.TABLE, CoreModule.getModuleInstance()).build());
		}
	}

	private void addPendingMaintenanceSection(Collection<IDomElement> sections) {

		var maintenanceWindows = AGMaintenanceWindow.getAllPendingMaintenanceWindows();
		if (!maintenanceWindows.isEmpty()) {
			List<IDisplayString> displayStrings = maintenanceWindows//
				.stream()
				.map(AGMaintenanceWindow::toDisplayWithoutId)
				.collect(Collectors.toList());
			IDisplayString message = CoreI18n.PENDING_MAINTENANCE_INFO//
				.concat("\n\n")
				.concat(Imploder.implode(displayStrings, "\n"));
			sections.add(new DomMessageDiv(DomMessageType.INFO, message));
		}
	}

	private void addPasswordChangeSection(Collection<IDomElement> sections) {

		if (CurrentUser.get().isPasswordChangeNecessary()) {
			sections.add(new UserPasswordChangeDiv(SofticarPasswordPolicy.get()));
		}
	}

	private void addUserImpersonationTerminationSection(Collection<IDomElement> sections) {

		UserImpersonationSessionManager//
			.getImpersonatingUserFromCurrentSession()
			.map(UserImpersonationTerminationDiv::new)
			.ifPresent(sections::add);
	}

	private void appendSectionsSeparatedByHr(Collection<IDomElement> sections) {

		for (var iterator = sections.iterator(); iterator.hasNext();) {
			appendChild(iterator.next());
			if (iterator.hasNext()) {
				appendNewChild(DomElementTag.HR);
			}
		}
	}
}

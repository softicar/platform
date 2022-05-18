package com.softicar.platform.core.module.start.page;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.impersonation.UserImpersonationSessionManager;
import com.softicar.platform.core.module.user.impersonation.UserImpersonationTerminationDiv;
import com.softicar.platform.core.module.user.password.change.UserPasswordChangeDiv;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class StartPageDiv extends DomDiv {

	public StartPageDiv() {

		var nodes = new ArrayList<>();
		createPendingMaintenanceInfo().ifPresent(nodes::add);
		createPasswordChangeNode().ifPresent(nodes::add);
		createUserImpersonationTerminationNode().ifPresent(nodes::add);
		appendSeparatedByHr(nodes);

		addMarker(StartPageMarker.MAIN_ELEMENT);
	}

	private Optional<IDomNode> createPendingMaintenanceInfo() {

		var maintenanceWindows = AGMaintenanceWindow.getAllPendingMaintenanceWindows();
		if (!maintenanceWindows.isEmpty()) {
			List<IDisplayString> displayStrings = maintenanceWindows//
				.stream()
				.map(AGMaintenanceWindow::toDisplayWithoutId)
				.collect(Collectors.toList());
			IDisplayString message = CoreI18n.PENDING_MAINTENANCE_INFO//
				.concat("\n\n")
				.concat(Imploder.implode(displayStrings, "\n"));
			return Optional.of(new DomMessageDiv(DomMessageType.INFO, message));
		} else {
			return Optional.empty();
		}
	}

	private Optional<IDomNode> createPasswordChangeNode() {

		if (CurrentUser.get().isPasswordChangeNecessary()) {
			return Optional.of(new UserPasswordChangeDiv(SofticarPasswordPolicy.get()));
		} else {
			return Optional.empty();
		}
	}

	private Optional<IDomNode> createUserImpersonationTerminationNode() {

		return UserImpersonationSessionManager//
			.getImpersonatingUserFromCurrentSession()
			.map(UserImpersonationTerminationDiv::new);
	}

	private void appendSeparatedByHr(ArrayList<Object> nodes) {

		for (var iterator = nodes.iterator(); iterator.hasNext();) {
			appendChild(iterator.next());
			if (iterator.hasNext()) {
				appendNewChild(DomElementTag.HR);
			}
		}
	}
}

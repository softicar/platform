package com.softicar.platform.core.module.start.page;

import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.impersonation.UserImpersonationSessionManager;
import com.softicar.platform.core.module.user.impersonation.UserImpersonationTerminationDiv;
import com.softicar.platform.core.module.user.password.change.UserPasswordChangeDiv;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.Optional;

class StartPageDiv extends DomDiv {

	public StartPageDiv() {

		var nodes = new ArrayList<>();
		createPasswordChangeNode().ifPresent(nodes::add);
		createUserImpersonationTerminationNode().ifPresent(nodes::add);
		appendSeparatedByHr(nodes);

		setMarker(StartPageMarker.MAIN_ELEMENT);
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

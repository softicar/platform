package com.softicar.platform.workflow.module.workflow.transients.field;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.workflow.module.WorkflowI18n;
import java.util.Set;

public class WorkflowUserFieldDiv extends DomDiv {

	public WorkflowUserFieldDiv(Set<String> users) {

		if (users.size() > 0) {
			var userListDiv = new DomDiv();
			appendChild(new ToggleUserListButton(userListDiv, users.size()));

			for (String user: users) {
				userListDiv.appendChild(new DomDiv()).appendText(user);
			}
		}
	}

	private class ToggleUserListButton extends DomButton {

		private final DomDiv userListDiv;
		private final int userCount;

		public ToggleUserListButton(DomDiv userListDiv, int userCount) {

			this.userListDiv = userListDiv;
			this.userCount = userCount;
			setClickCallback(this::handleClick);
			updateLabel();
		}

		private void handleClick() {

			if (userListDiv.getParent() == null) {
				WorkflowUserFieldDiv.this.appendChild(userListDiv);
			} else {
				userListDiv.disappend();
			}
			updateLabel();
		}

		private void updateLabel() {

			setLabel(
				(userListDiv.getParent() == null//
						? WorkflowI18n.SHOW_USERS
						: WorkflowI18n.HIDE_USERS).concatFormat(" (%s)", userCount));
		}
	}
}

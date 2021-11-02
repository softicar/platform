package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import java.util.Objects;

public class UserImpersonationTerminationDiv extends DomDiv {

	public UserImpersonationTerminationDiv(AGUser impersonatingUser) {

		Objects.requireNonNull(impersonatingUser);
		appendChild(new DomMessageDiv(DomMessageType.WARNING, new MessageElementDiv(impersonatingUser)));
	}

	private void terminate() {

		UserImpersonationSessionManager.terminateUserImpersonation();

		removeChildren();
		appendText(CoreI18n.PLEASE_PRESS_F5_TO_REFRESH);
	}

	private class MessageElementDiv extends DomDiv {

		public MessageElementDiv(AGUser impersonatingUser) {

			appendText(CoreI18n.YOU_ARE_CURRENTLY_IMPERSONATING_USER_ARG1.toDisplay(CurrentUser.get().toDisplay()));
			appendNewChild(DomElementTag.BR);

			appendChild(
				new DomButton()//
					.setIcon(CoreImages.USER_IMPERSONATION_TERMINATE.getResource())
					.setLabel(CoreI18n.RETURN_TO_USER_ARG1.toDisplay(impersonatingUser.toDisplay()))
					.setClickCallback(() -> terminate()));
		}
	}
}

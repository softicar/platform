package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelect;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelectBuilder;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;

public class UserImpersonationPageDiv extends DomDiv {

	private final InputStack inputTable;

	public UserImpersonationPageDiv() {

		this.inputTable = new InputStack();

		appendChild(inputTable);
		new DomWikiDivBuilder()
			.addUnorderedListItem(CoreI18n.PLEASE_SPECIFY_THE_RATIONALE_TO_IMPERSONATE_THE_SELECTED_USER)
			.addUnorderedListItem(
				CoreI18n.PLEASE_SPECIFY_THE_ID_OF_THE_ISSUE_YOU_ARE_WORKING_ON_IN_THE_ARG1_FIELD_FOR_EXAMPLE_I123.toDisplay(CoreI18n.RATIONALE))
			.buildAndAppendTo(this);
		appendChild(
			new DomMessageDiv(
				DomMessageType.WARNING,
				CoreI18n.THE_IMPERSONATED_USER_WILL_BE_INFORMED//
					.concatSentence(CoreI18n.PROVIDE_A_POLITE_AND_REASONABLE_RATIONALE)));
		appendChild(new BeginUserImpersonationButton());
	}

	private void impersonateUser() {

		UserImpersonationSessionManager.beginUserImpersonation(inputTable.getTargetUser(), inputTable.getRationale());

		removeChildren();
		appendText(CoreI18n.PLEASE_PRESS_F5_TO_REFRESH);
	}

	private static class InputStack extends DomLabelGrid {

		private final DomSimpleValueSelect<AGUser> userSelect;
		private final DomTextArea rationaleInput;

		public InputStack() {

			this.userSelect = new DomSimpleValueSelectBuilder<AGUser>()//
				.setValues(AGUser.TABLE.loadAll())
				.setValueDisplayStringFunction(this::getUserDisplayString)
				.build();
			this.rationaleInput = new DomTextArea().setSize(3, 60);

			add(CoreI18n.IMPERSONATED_USER, userSelect);
			add(CoreI18n.RATIONALE, rationaleInput);
		}

		public AGUser getTargetUser() {

			var user = userSelect.getSelectedValue();
			if (!user.isPresent()) {
				throw new SofticarUserException(CoreI18n.PLEASE_SELECT_THE_USER_TO_IMPERSONATE);
			} else {
				return user.get();
			}
		}

		public String getRationale() {

			String rationale = rationaleInput.getValueTextTrimmed();
			//FIXME Arbitrary check for rationale length, decide for better check later
			if (rationale.length() > 25) {
				return rationale;
			} else {
				throw new SofticarUserException(CoreI18n.RATIONALE_NEEDS_A_MINIMUM_OF_ARG1_CHARACTERS.toDisplay(25));
			}
		}

		private IDisplayString getUserDisplayString(AGUser user) {

			StringBuilder displayName = new StringBuilder(user.toDisplay().toString());
			if (!user.isActive()) {
				displayName.append(" (");
				displayName.append(CoreI18n.DEACTIVATED.toString().toUpperCase());
				displayName.append(")");
			}
			return IDisplayString.create(displayName.toString());
		}
	}

	private class BeginUserImpersonationButton extends DomButton {

		public BeginUserImpersonationButton() {

			setIcon(CoreImages.USER_IMPERSONATION.getResource());
			setLabel(CoreI18n.IMPERSONATE_USER);
			setClickCallback(() -> impersonateUser());
		}
	}
}

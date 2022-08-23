package com.softicar.platform.core.module.user.pseudonymization;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.UserPredicates;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.action.AbstractEmfPromptAction;
import com.softicar.platform.emf.action.IEmfPromptActionInput;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.Objects;

public class UserPseudonymizationAction extends AbstractEmfPromptAction<AGUser> {

	@Override
	public IEmfPredicate<AGUser> getPrecondition() {

		return UserPredicates.NOT_ACTIVE;
	}

	@Override
	public IEmfPermission<AGUser> getRequiredPermission() {

		return CoreModule.getModuleAdministation();
	}

	@Override
	public IResource getIcon() {

		return CoreImages.USER_PSEUDONYMIZATION.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.PSEUDONYMIZE_USER;
	}

	@Override
	protected void buildPrompt(AGUser user, IEmfPromptActionInput<AGUser> input) {

		input.appendNode(new DomMessageDiv(DomMessageType.WARNING, CoreI18n.THIS_ACTION_CANNOT_BE_UNDONE));
		PseudonymInput pseudonymInput = new PseudonymInput(user);
		ConfirmationInput confirmationInput = new ConfirmationInput();
		input.appendRow(CoreI18n.USER_PSEUDONYM, pseudonymInput);
		input.appendRow(CoreI18n.CONFIRMATION, confirmationInput);
		input.addSaveHandler(() -> pseudonymizeUser(user, pseudonymInput, confirmationInput));
	}

	private void pseudonymizeUser(AGUser user, PseudonymInput pseudonymInput, ConfirmationInput confirmationInput) {

		confirmationInput.assertConfirmation();

		new UserPseudonymizer(user, pseudonymInput.getValue()).pseudonymize();
	}

	private class PseudonymInput extends DomDiv {

		private final DomTextInput input;

		public PseudonymInput(AGUser user) {

			this.input = new DomTextInput("" + user.getId());

			appendChild(new DomMessageDiv(DomMessageType.INFO, CoreI18n.THE_PSEUDONYM_WILL_BE_USED_AS_THE_LAST_NAME));
			appendChild(input);
		}

		public String getValue() {

			return input.getValueTextTrimmed();
		}
	}

	private class ConfirmationInput extends DomDiv {

		private final DomTextInput input;

		public ConfirmationInput() {

			this.input = new DomTextInput();

			appendChild(
				new DomMessageDiv(//
					DomMessageType.INFO,
					CoreI18n.ENTER_THIS_TEXT_AS_CONFIRMATION_ARG1.toDisplay(getConfirmationText())));
			appendChild(input);
		}

		public void assertConfirmation() {

			if (!isCorrectConfirmationText()) {
				throw new DomInputException(CoreI18n.ENTER_THIS_TEXT_AS_CONFIRMATION_ARG1.toDisplay(getConfirmationText()));
			}
		}

		private boolean isCorrectConfirmationText() {

			return Objects.equals(input.getValueTextTrimmed(), getConfirmationText().toString());
		}

		private IDisplayString getConfirmationText() {

			return CoreI18n.PSEUDONYMIZE_USER;
		}
	}
}

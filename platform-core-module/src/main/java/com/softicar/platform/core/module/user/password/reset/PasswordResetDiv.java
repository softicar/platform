package com.softicar.platform.core.module.user.password.reset;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.page.service.PageServiceDocumentBuilder;
import com.softicar.platform.core.module.page.service.PageServiceFactory;
import com.softicar.platform.core.module.user.password.policy.IPasswordPolicy;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.core.module.user.password.quality.UserPasswordQualityDiv;
import com.softicar.platform.core.module.web.service.WebServiceUrlBuilder;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.DomPasswordInput;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.dialog.DomModalAlertDialog;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.AbstractDomInput;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Optional;

public class PasswordResetDiv extends DomDiv {

	private final PageServiceDocumentBuilder documentBuilder;
	private final IAjaxDocument document;
	private final Optional<AGUserPasswordResetRequest> passwordResetRequest;

	public PasswordResetDiv(PageServiceDocumentBuilder documentBuilder, IAjaxDocument document) {

		this.documentBuilder = documentBuilder;
		this.document = document;
		this.passwordResetRequest = new PasswordResetRequestParameterParser(document.getParameters())//
			.getPasswordResetRequest();

		addCssClass(CoreCssClasses.PAGE_SERVICE_PASSWORD_RESET_DIV);
		addMarker(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_DIV);
		if (passwordResetRequest.isPresent()) {
			appendChild(new PasswordChangeForm());
		} else {
			appendChild(new NoRequestErrorElement());
		}
		if (AGCoreModuleInstance.getInstance().isTestSystem()) {
			addCssClass(CoreCssClasses.TEST_SYSTEM);
		}
	}

	private class PasswordChangeForm extends DomForm {

		private final ChangePasswordButton changePasswordButton;
		private final PasswordInput passwordInput;
		private final PasswordInput confirmPasswordInput;
		private final PasswordChangeErrorElement errorDiv;

		public PasswordChangeForm() {

			this.changePasswordButton = new ChangePasswordButton();
			this.passwordInput = new PasswordInput("new-password", CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_NEW_PASSWORD_INPUT);
			this.confirmPasswordInput = new PasswordInput("confirm-password", CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CONFIRM_PASSWORD_INPUT);
			this.errorDiv = new PasswordChangeErrorElement();

			addMarker(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_FORM);

			Optional//
				.ofNullable(AGCoreModuleInstance.getInstance().getPortalLogo())
				.ifPresent(this::appendLogo);

			DomDiv passwordChangeFormDiv = new DomDiv();
			DomLabelGrid display = new DomLabelGrid();
			display.add(CoreI18n.PASSWORD, passwordInput);
			display.add(CoreI18n.CONFIRM_PASSWORD, confirmPasswordInput);

			passwordChangeFormDiv.appendChild(display);
			passwordChangeFormDiv.appendChild(errorDiv);

			appendChild(passwordChangeFormDiv);
			appendChild(new DomActionBar(changePasswordButton));
		}

		private void changePassword() {

			if (isPasswordPolicyFulfilled() && isMatchingInput()) {
				try (DbTransaction transaction = new DbTransaction()) {
					var passwordResetRequest = new PasswordResetRequestParameterParser(document.getParameters())//
						.getPasswordResetRequest()
						.get();
					passwordResetRequest.getUser().updatePassword(passwordInput.getValueText());
					passwordResetRequest.setActive(false).save();
					transaction.commit();
				}
				CurrentDomDocument
					.get()
					.getEngine()
					.pushBrowserHistoryState(CoreI18n.LOGIN.toString(), new WebServiceUrlBuilder(PageServiceFactory.class).build().getStartingFromPath());
				documentBuilder.refreshBody();
				new DomModalAlertDialog(CoreI18n.THE_PASSWORD_WAS_UPDATED_SUCCESSFULLY).open();
			}
		}

		private boolean isPasswordPolicyFulfilled() {

			SofticarPasswordPolicy passwordPolicy = SofticarPasswordPolicy.get();
			String password = passwordInput.getValueText();
			var policyFulfilled = passwordPolicy.isFulfilled(password);
			if (!policyFulfilled) {
				errorDiv.showPasswordQuality(passwordPolicy, password);
				return false;
			}
			return true;
		}

		private boolean isMatchingInput() {

			var inputMatches = passwordInput.getValueText().equals(confirmPasswordInput.getValueText());
			if (!inputMatches) {
				errorDiv.showPasswordMismatch();
				return false;
			}
			return true;
		}

		private void appendLogo(AGStoredFile logoFile) {

			appendChild(new DomImage(new StoredFileResource(logoFile)));
		}

		private class ChangePasswordButton extends DomButton {

			public ChangePasswordButton() {

				setLabel(CoreI18n.CHANGE_PASSWORD);
				addMarker(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CHANGE_PASSWORD_BUTTON);

				HiddenSubmitInput submitInput = new HiddenSubmitInput();
				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.CLICK, submitInput);
				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.ENTER, submitInput);
				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.SPACE, submitInput);
				appendChild(submitInput);
			}
		}

		private class PasswordInput extends DomPasswordInput {

			public PasswordInput(String name, ITestMarker marker) {

				setAttribute("name", name);
				setAttribute("autocomplete", "off");

				addMarker(marker);

				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.ENTER, changePasswordButton);
			}
		}

		private class PasswordChangeErrorElement extends DomDiv {

			public PasswordChangeErrorElement() {

				setCssClass(CoreCssClasses.PAGE_SERVICE_PASSWORD_RESET_ERROR_DIV);
				addMarker(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_ERROR_ELEMENT);
			}

			public void showPasswordMismatch() {

				removeChildren();
				appendChild(new DomMessageDiv(DomMessageType.ERROR, CoreI18n.THE_ENTERED_PASSWORDS_DO_NOT_MATCH));
			}

			public void showPasswordQuality(IPasswordPolicy passwordPolicy, String password) {

				removeChildren();
				appendChild(new UserPasswordQualityDiv(passwordPolicy, password));
			}
		}

		private class HiddenSubmitInput extends AbstractDomInput implements IDomClickEventHandler {

			public HiddenSubmitInput() {

				setAttribute("type", "submit");
				setStyle(CssDisplay.NONE);
			}

			@Override
			public void handleClick(IDomEvent event) {

				changePassword();
			}
		}
	}

	private class NoRequestErrorElement extends DomDiv {

		public NoRequestErrorElement() {

			setCssClass(CoreCssClasses.PAGE_SERVICE_PASSWORD_RESET_ERROR_DIV);
			addMarker(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_ERROR_ELEMENT);
			appendChild(new DomMessageDiv(DomMessageType.ERROR, CoreI18n.NO_PASSWORD_RESET_REQUEST_FOUND));
		}
	}
}

package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.ajax.login.OneTimePasswordChallenge;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.core.module.page.service.PageServiceDocumentBuilder;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.password.reset.PasswordResetRequestGenerator;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.DomPasswordInput;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.prompt.DomPromptButtonBuilder;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.AbstractDomInput;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.IDomFocusable;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Optional;

public class PageServiceLoginDiv extends DomDiv {

	private final PageServiceDocumentBuilder documentBuilder;
	private int passwordIndex;

	public PageServiceLoginDiv(PageServiceDocumentBuilder documentBuilder) {

		this.documentBuilder = documentBuilder;
		this.passwordIndex = 0;

		addCssClass(CoreCssClasses.PAGE_SERVICE_LOGIN_DIV);
		appendChild(new LoginForm());
		if (AGCoreModuleInstance.getInstance().isTestSystem()) {
			addCssClass(CoreCssClasses.TEST_SYSTEM);
		}
	}

	private void showForm(DomForm form) {

		removeChildren();
		appendChild(form);
		IDomFocusable.focusFirst(IDomTextualInput.class, form);
	}

	private void appendPortalLogo(DomForm form) {

		Optional//
			.ofNullable(AGCoreModuleInstance.getInstance().getPortalLogo())
			.ifPresent(logoFile -> form.appendChild(new DomImage(new StoredFileResource(logoFile))));
	}

	private class LoginForm extends DomForm {

		private final LoginButton loginButton;
		private final UserInput userInput;
		private final PasswordInput passwordInput;
		private final MaintenanceElement maintenanceDiv;
		private final LoginErrorElement errorDiv;

		public LoginForm() {

			this.loginButton = new LoginButton(this::login);
			this.userInput = new UserInput();
			this.passwordInput = new PasswordInput();
			this.maintenanceDiv = new MaintenanceElement();
			this.errorDiv = new LoginErrorElement();

			addMarker(CoreTestMarker.PAGE_SERVICE_LOGIN_FORM);
			appendPortalLogo(this);

			DomDiv loginFormDiv = new DomDiv();
			DomLabelGrid display = new DomLabelGrid();
			display.add(CoreI18n.USER, userInput);
			display.add(CoreI18n.PASSWORD, passwordInput);

			loginFormDiv.appendChild(maintenanceDiv);
			loginFormDiv.appendChild(display);
			loginFormDiv.appendChild(errorDiv);

			appendChild(loginFormDiv);
			var buttonDiv = new DomDiv();
			buttonDiv.appendChild(new DomActionBar(loginButton));
			if (AGCoreModuleInstance.getInstance().isPasswordResetFunctionality()) {
				buttonDiv.appendChild(buildResetPasswordButton());
			}
			appendChild(buttonDiv);
		}

		public void showError(String message) {

			errorDiv.showError(message);
		}

		private void login() {

			try {
				new PageServiceLoginExecutor()//
					.executeLogin(userInput.getValueText(), passwordInput.getValueText(), ++passwordIndex)
					.ifPresentOrElse(challenge -> showForm(new OneTimePasswordForm(challenge)), documentBuilder::refreshBody);
			} catch (SofticarUserException exception) {
				errorDiv.showError(exception.getMessage());
			}
		}

		private IDomElement buildResetPasswordButton() {

			var button = new DomPromptButtonBuilder()//
				.setLabel(CoreI18n.FORGOT_PASSWORD)
				.setPromptMessage(CoreI18n.PLEASE_ENTER_THE_USER_YOU_WANT_TO_RESET_THE_PASSWORD_FOR)
				.setPromptCallback(this::createResetRequest)
				.setDefaultValueSupplier(userInput::getValueText)
				.build();
			button.addMarker(CoreTestMarker.PAGE_SERVICE_LOGIN_RESET_PASSWORD_BUTTON);
			return button;
		}

		private void createResetRequest(String loginName) {

			AGUser.TABLE
				.createSelect()
				.where(AGUser.ACTIVE)
				.where(AGUser.LOGIN_NAME.isEqual(loginName))
				.getOneAsOptional()
				.ifPresentOrElse(it -> new PasswordResetRequestGenerator(it).insertResetRequest(), this::showCouldNotResetPasswordMessage);
		}

		private void showCouldNotResetPasswordMessage() {

			throw new SofticarUserException(CoreI18n.COULD_NOT_RESET_USER_PASSWORD);
		}

		private class UserInput extends DomTextInput {

			public UserInput() {

				setAttribute("name", "username");
				setAttribute("autocomplete", "username");

				addMarker(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT);

				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.ENTER, loginButton);
			}
		}

		private class PasswordInput extends DomPasswordInput {

			public PasswordInput() {

				setAttribute("name", "current-password");
				setAttribute("autocomplete", "current-password");

				addMarker(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT);

				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.ENTER, loginButton);
			}
		}

		private class MaintenanceElement extends DomDiv {

			public MaintenanceElement() {

				setCssClass(CoreCssClasses.PAGE_SERVICE_LOGIN_MAINTENANCE_DIV);
				AGMaintenanceWindow//
					.getMaintenanceInProgress()
					.ifPresentOrElse(this::appendMaintenanceInProgressInfo, this::checkIfMaintenanceIsConfiguredForToday);
			}

			private void appendMaintenanceInProgressInfo(AGMaintenanceWindow maintenanceWindow) {

				appendChild(
					new DomMessageDiv(
						DomMessageType.WARNING,
						CoreI18n.MAINTENANCE_IS_CURRENTLY_IN_PROGRESS
							.concat("\n")
							.concat(CoreI18n.EXPECTED_END_ARG1.toDisplay(maintenanceWindow.getExpectedEnd()))));
			}

			private void checkIfMaintenanceIsConfiguredForToday() {

				var today = Day.today();
				AGMaintenanceWindow.TABLE//
					.createSelect()
					.where(AGMaintenanceWindow.EXPECTED_START.isGreater(today.getBegin()))
					.where(AGMaintenanceWindow.EXPECTED_START.isLess(today.getEnd()))
					.where(AGMaintenanceWindow.EXPECTED_END.isGreater(DayTime.now()))
					.where(AGMaintenanceWindow.STATE.isEqual(AGMaintenanceStateEnum.PENDING.getRecord()))
					.orderBy(AGMaintenanceWindow.EXPECTED_START)
					.getFirstAsOptional()
					.ifPresent(this::appendPendingMaintenanceInfo);
			}

			private void appendPendingMaintenanceInfo(AGMaintenanceWindow maintenanceWindow) {

				appendChild(
					new DomMessageDiv(
						DomMessageType.INFO,
						CoreI18n.A_MAINTENANCE_WINDOW_IS_CONFIGURED_FOR_TODAY
							.concat("\n")
							.concat(
								CoreI18n.EXPECTED_DURATION_IS_FROM_ARG1_TO_ARG2
									.toDisplay(//
										maintenanceWindow.getExpectedStart().getTimeAsStringHM(),
										maintenanceWindow.getExpectedEnd().getTimeAsStringHM()))));
			}
		}

		private class LoginErrorElement extends DomDiv {

			public LoginErrorElement() {

				setCssClass(CoreCssClasses.PAGE_SERVICE_LOGIN_ERROR_DIV);
				addMarker(CoreTestMarker.PAGE_SERVICE_LOGIN_ERROR_MESSAGE_ELEMENT);
			}

			public void showError(String message) {

				removeChildren();
				appendChild(new DomMessageDiv(DomMessageType.ERROR, IDisplayString.create(message)));
			}
		}
	}

	private class OneTimePasswordForm extends DomForm {

		private final OneTimePasswordChallenge challenge;
		private final LoginButton loginButton;
		private final OneTimePasswordInput oneTimePasswordInput;

		public OneTimePasswordForm(OneTimePasswordChallenge challenge) {

			this.challenge = challenge;
			this.loginButton = new LoginButton(this::login);
			this.oneTimePasswordInput = new OneTimePasswordInput();

			addMarker(CoreTestMarker.PAGE_SERVICE_LOGIN_ONE_TIME_PASSWORD_FORM);
			appendPortalLogo(this);

			DomDiv oneTimePasswordFormDiv = new DomDiv();
			oneTimePasswordFormDiv
				.appendChild(
					new DomMessageDiv(
						DomMessageType.INFO,
						CoreI18n.TWO_FACTOR_AUTHENTICATION_IS_ACTIVE
							.concat("\n")
							.concat(CoreI18n.ARG1_ONE_TIME_PASSWORD_HAS_BEEN_SENT_TO_YOUR_EMAIL_ADDRESS.toDisplay(AGCoreModuleInstance.getSystemIdentifier()))));
			DomLabelGrid display = new DomLabelGrid();
			display.add(CoreI18n.ONE_TIME_PASSWORD.concat(" #" + challenge.getPasswordIndex()), oneTimePasswordInput);
			oneTimePasswordFormDiv.appendChild(display);

			appendChild(oneTimePasswordFormDiv);
			var buttonDiv = new DomDiv();
			buttonDiv.appendChild(new DomActionBar(loginButton));
			appendChild(buttonDiv);
		}

		private void login() {

			try {
				new PageServiceLoginExecutor().completeLogin(challenge, oneTimePasswordInput.getValueText());
				documentBuilder.refreshBody();
			} catch (SofticarUserException exception) {
				// the one-time password is invalidated, so the user needs to start over
				var loginForm = new LoginForm();
				showForm(loginForm);
				loginForm.showError(exception.getMessage());
			}
		}

		private class OneTimePasswordInput extends DomTextInput {

			public OneTimePasswordInput() {

				setAttribute("name", "one-time-code");
				setAttribute("autocomplete", "one-time-code");

				addMarker(CoreTestMarker.PAGE_SERVICE_LOGIN_ONE_TIME_PASSWORD_INPUT);

				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.ENTER, loginButton);
			}
		}
	}

	private class LoginButton extends DomButton {

		public LoginButton(Runnable loginAction) {

			setIcon(CoreImages.LOGIN.getResource());
			setLabel(CoreI18n.LOGIN);
			addMarker(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);

			HiddenSubmitInput submitInput = new HiddenSubmitInput(loginAction);
			getDomEngine().setClickTargetForEventDelegation(this, DomEventType.CLICK, submitInput);
			getDomEngine().setClickTargetForEventDelegation(this, DomEventType.ENTER, submitInput);
			getDomEngine().setClickTargetForEventDelegation(this, DomEventType.SPACE, submitInput);
			appendChild(submitInput);
		}
	}

	private class HiddenSubmitInput extends AbstractDomInput implements IDomClickEventHandler {

		private final Runnable loginAction;

		public HiddenSubmitInput(Runnable loginAction) {

			this.loginAction = loginAction;

			setAttribute("type", "submit");
			setStyle(CssDisplay.NONE);
		}

		@Override
		public void handleClick(IDomEvent event) {

			loginAction.run();
		}
	}
}

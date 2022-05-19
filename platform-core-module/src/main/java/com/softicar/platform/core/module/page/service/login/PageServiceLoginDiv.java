package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.page.PageCssClasses;
import com.softicar.platform.core.module.page.service.PageServiceDocumentBuilder;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.DomPasswordInput;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomInput;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.Optional;

public class PageServiceLoginDiv extends DomDiv {

	private final PageServiceDocumentBuilder documentBuilder;
	private final LoginForm loginForm;

	public PageServiceLoginDiv(PageServiceDocumentBuilder documentBuilder) {

		this.documentBuilder = documentBuilder;
		this.loginForm = new LoginForm();

		addCssClass(PageCssClasses.PAGE_SERVICE_LOGIN_DIV);
		appendChild(loginForm);
		if (AGCoreModuleInstance.getInstance().isTestSystem()) {
			addCssClass(PageCssClasses.TEST_SYSTEM);
		}
	}

	private class LoginForm extends DomForm {

		private final LoginButton loginButton;
		private final UserInput userInput;
		private final PasswordInput passwordInput;
		private final MaintenanceElement maintenanceDiv;
		private final LoginErrorElement errorDiv;

		public LoginForm() {

			this.loginButton = new LoginButton();
			this.userInput = new UserInput();
			this.passwordInput = new PasswordInput();
			this.maintenanceDiv = new MaintenanceElement();
			this.errorDiv = new LoginErrorElement();

			Optional//
				.ofNullable(AGCoreModuleInstance.getInstance().getPortalLogo())
				.ifPresent(this::appendLogo);

			DomDiv loginFormDiv = new DomDiv();
			DomLabelGrid display = new DomLabelGrid();
			display.add(CoreI18n.USER, userInput);
			display.add(CoreI18n.PASSWORD, passwordInput);

			loginFormDiv.appendChild(maintenanceDiv);
			loginFormDiv.appendChild(display);
			loginFormDiv.appendChild(errorDiv);

			appendChild(loginFormDiv);
			appendChild(new DomActionBar(loginButton));
		}

		private void appendLogo(AGStoredFile logoFile) {

			appendChild(new DomImage(new StoredFileResource(logoFile)));
		}

		private void login() {

			try {
				new PageServiceLoginExecutor(userInput.getInputText(), passwordInput.getInputText()).executeLogin();
				documentBuilder.refreshBody();
			} catch (SofticarUserException exception) {
				errorDiv.showError(exception.getMessage());
			}
		}

		private class LoginButton extends DomButton {

			public LoginButton() {

				setIcon(CoreImages.LOGIN.getResource());
				setLabel(CoreI18n.LOGIN);
				addMarker(PageServiceLoginDivMarker.LOGIN_BUTTON);

				HiddenSubmitInput submitInput = new HiddenSubmitInput();
				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.CLICK, submitInput);
				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.ENTER, submitInput);
				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.SPACE, submitInput);
				appendChild(submitInput);
			}
		}

		private class HiddenSubmitInput extends DomInput implements IDomClickEventHandler {

			public HiddenSubmitInput() {

				setAttribute("type", "submit");
				setStyle(CssDisplay.NONE);
			}

			@Override
			public void handleClick(IDomEvent event) {

				login();
			}
		}

		private class UserInput extends DomTextInput {

			public UserInput() {

				setAttribute("name", "username");
				setAttribute("autocomplete", "username");

				addMarker(PageServiceLoginDivMarker.USER_INPUT);

				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.ENTER, loginButton);
			}
		}

		private class PasswordInput extends DomPasswordInput {

			public PasswordInput() {

				setAttribute("name", "current-password");
				setAttribute("autocomplete", "current-password");

				addMarker(PageServiceLoginDivMarker.PASSWORD_INPUT);

				getDomEngine().setClickTargetForEventDelegation(this, DomEventType.ENTER, loginButton);
			}
		}

		private class MaintenanceElement extends DomDiv {

			public MaintenanceElement() {

				setCssClass(PageCssClasses.PAGE_SERVICE_LOGIN_MAINTENANCE_DIV);
				if (AGMaintenanceWindow.isMaintenanceInProgress()) {
					DayTime expectedEnd = AGMaintenanceWindow.TABLE//
						.createSelect()
						.where(AGMaintenanceWindow.STATE.equal(AGMaintenanceStateEnum.IN_PROGRESS.getRecord()))
						.getOne()
						.getExpectedEnd();
					appendChild(
						new DomMessageDiv(
							DomMessageType.WARNING,
							CoreI18n.MAINTENANCE_IS_CURRENTLY_IN_PROGRESS
								.concat("\n")
								.concat(CoreI18n.EXPECTED_END_IS_AT_ARG1.toDisplay(expectedEnd.getTime()))));
				}
			}
		}

		private class LoginErrorElement extends DomDiv {

			public LoginErrorElement() {

				setCssClass(PageCssClasses.PAGE_SERVICE_LOGIN_ERROR_DIV);
				addMarker(PageServiceLoginDivMarker.ERROR_MESSAGE_ELEMENT);
			}

			public void showError(String message) {

				removeChildren();
				appendChild(new DomMessageDiv(DomMessageType.ERROR, IDisplayString.create(message)));
			}
		}
	}
}

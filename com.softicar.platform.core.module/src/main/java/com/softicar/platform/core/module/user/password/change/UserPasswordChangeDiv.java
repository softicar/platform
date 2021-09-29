package com.softicar.platform.core.module.user.password.change;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import com.softicar.platform.core.module.user.password.UserPasswordGenerator;
import com.softicar.platform.core.module.user.password.policy.AGPasswordPolicy;
import com.softicar.platform.core.module.user.password.policy.IPasswordPolicy;
import com.softicar.platform.core.module.user.password.quality.UserPasswordQualityBar;
import com.softicar.platform.core.module.user.password.quality.UserPasswordQualityDiv;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomPasswordInput;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.emf.EmfImages;

public class UserPasswordChangeDiv extends DomDiv {

	private final IPasswordPolicy passwordPolicy;
	private final AGPasswordPolicy userPasswordPolicy;
	private final AGUser user;
	private final SecurePasswordMessageDiv securePasswordMessageDiv;
	private final PasswordInputStack inputTable;
	private final QualityMessageDiv qualityMessageDiv;
	private final PasswordSaveButton saveButton;
	private final DomCheckbox visiblePasswordCheckbox;
	private boolean maximumPasswordAgeReached;

	public UserPasswordChangeDiv(IPasswordPolicy passwordPolicy) {

		this.passwordPolicy = passwordPolicy;
		this.user = CurrentUser.get();
		this.userPasswordPolicy = user.getPasswordPolicy();
		this.maximumPasswordAgeReached = user.isMaximumPasswordAgeReached();
		this.visiblePasswordCheckbox = new DomCheckbox()//
			.setLabel(CoreI18n.SHOW_PASSWORD)
			.setChangeCallback(this::setPasswordVisible);
		this.inputTable = new PasswordInputStack();
		this.qualityMessageDiv = new QualityMessageDiv();
		this.securePasswordMessageDiv = new SecurePasswordMessageDiv();
		this.saveButton = new PasswordSaveButton();
		this.saveButton.setEnabled(false);

		appendChild(securePasswordMessageDiv);
		appendChild(new DomActionBar(new GeneratePasswordButton(), visiblePasswordCheckbox));
		appendChild(inputTable);
		appendChild(qualityMessageDiv);
		appendChild(new DomActionBar(new PasswordCheckButton(), saveButton));

		inputTable.updateQualityBar();
	}

	public boolean checkPassword() {

		String currentPassword = inputTable.getCurrentPassword();
		String newPassword = inputTable.getPassword();
		String repeatedPassword = inputTable.getRepeatedPassword();

		inputTable.updateQualityBar();

		if (!AGUserPassword.getActive(user).verifyPassword(currentPassword)) {
			qualityMessageDiv.showCurrentPasswordMismatch();
			saveButton.setEnabled(false);
			return false;
		} else if (!newPassword.equals(repeatedPassword)) {
			qualityMessageDiv.showPasswordMismatch();
			saveButton.setEnabled(false);
			return false;
		} else if (!passwordPolicy.isFulfilled(newPassword)) {
			qualityMessageDiv.showPasswordQuality(newPassword);
			saveButton.setEnabled(false);
			return false;
		} else {
			if (checkIfPasswordIsRepeated(newPassword)) {
				qualityMessageDiv.showPasswordRepeated();
				saveButton.setEnabled(false);
				return false;
			} else {
				qualityMessageDiv.showPasswordQuality(newPassword);
				saveButton.setEnabled(true);
				return true;
			}
		}
	}

	public void savePassword() {

		if (checkPassword()) {
			user.updatePassword(inputTable.getPassword());
			if (maximumPasswordAgeReached) {
				maximumPasswordAgeReached = false;
				appendChild(
					new DomMessageDiv(
						DomMessageType.INFO,
						CoreI18n.PLEASE_PRESS_F5_TO_REFRESH.concat(" ").concat(CoreI18n.THE_PORTAL_NAVIGATION_WILL_THEN_BE_ENABLED_AGAIN)));
			}
			securePasswordMessageDiv.refresh();
			qualityMessageDiv.showPasswordWasUpdated();
		}
	}

	public void setPasswordVisible(boolean visible) {

		inputTable.setPasswordVisible(visible);
	}

	public void generatePassword() {

		inputTable.setPassword(new UserPasswordGenerator().generatePassword());
		inputTable.setPasswordVisible(true);
		visiblePasswordCheckbox.setChecked(true);
		checkPassword();
	}

	private boolean checkIfPasswordIsRepeated(String newPassword) {

		if (userPasswordPolicy != null) {
			return AGUserPassword.TABLE//
				.createSelect()
				.where(AGUserPassword.USER.equal(user))
				.orderDescendingBy(AGUserPassword.CREATED_AT)
				.list(userPasswordPolicy.getMaximumPasswordReuse())
				.stream()
				.anyMatch(oldPassword -> checkIfPasswordMatchesOldOne(oldPassword, newPassword));
		}
		return false;
	}

	private boolean checkIfPasswordMatchesOldOne(AGUserPassword oldPassword, String newPassword) {

		return oldPassword.verifyPassword(newPassword);
	}

	private class SecurePasswordMessageDiv extends DomDiv {

		public SecurePasswordMessageDiv() {

			refresh();
		}

		public void refresh() {

			removeChildren();
			if (!user.isPasswordPolicyFulfilled()) {
				appendChild(
					new DomMessageDiv(
						DomMessageType.ERROR,
						CoreI18n.YOUR_CURRENT_PASSWORD_DOES_NOT_FULFILL_THE_PASSWORD_POLICY//
							.concatSentence(CoreI18n.PLEASE_DEFINE_A_NEW_PASSWORD_BELOW)));
			}
			if (user.isPasswordCompromised()) {
				appendChild(
					new DomMessageDiv(
						DomMessageType.ERROR,
						CoreI18n.YOUR_CURRENT_PASSWORD_MIGHT_HAVE_BEEN_COMPROMISED//
							.concatSentence(CoreI18n.PLEASE_DEFINE_A_NEW_PASSWORD_BELOW)));
			}
			if (maximumPasswordAgeReached) {
				appendChild(
					new DomMessageDiv(
						DomMessageType.ERROR,
						CoreI18n.YOUR_CURRENT_PASSWORD_IS_TOO_OLD//
							.concatSentence(CoreI18n.PLEASE_DEFINE_A_NEW_PASSWORD_BELOW)));
			}
		}
	}

	private class QualityMessageDiv extends DomDiv {

		public void showCurrentPasswordMismatch() {

			removeChildren();
			appendChild(new DomMessageDiv(DomMessageType.ERROR, CoreI18n.THE_ENTERED_CURRENT_PASSWORD_IS_WRONG));
		}

		public void showPasswordMismatch() {

			removeChildren();
			appendChild(new DomMessageDiv(DomMessageType.ERROR, CoreI18n.THE_ENTERED_PASSWORDS_DO_NOT_MATCH));
		}

		public void showPasswordQuality(String password) {

			removeChildren();
			appendChild(new UserPasswordQualityDiv(passwordPolicy, password));
		}

		public void showPasswordRepeated() {

			removeChildren();
			appendChild(
				new DomMessageDiv(
					DomMessageType.ERROR,
					CoreI18n.YOUR_ARG1_PREVIOUSLY_USED_PASSWORDS_CANNOT_BE_USED
						.toDisplay(userPasswordPolicy.getMaximumPasswordReuse())
						.concat(" ")
						.concat(CoreI18n.PLEASE_USE_A_DIFFERENT_PASSWORD)));
		}

		public void showPasswordWasUpdated() {

			removeChildren();
			appendChild(new DomMessageDiv(DomMessageType.SUCCESS, CoreI18n.THE_PASSWORD_WAS_UPDATED_SUCCESSFULLY));
		}
	}

	private class PasswordInputStack extends DomLabelGrid {

		private final PasswordInput passwordInput;
		private final PasswordInput repeatedPasswordInput;
		private final DomPasswordInput currentPasswordInput;
		private final UserPasswordQualityBar passwordQualityBar;

		public PasswordInputStack() {

			add(CoreI18n.USER, user.toDisplay());
			add(CoreI18n.LOGIN_NAME, user.getLoginName());
			add(CoreI18n.CURRENT_PASSWORD, currentPasswordInput = new DomPasswordInput());
			add(CoreI18n.PASSWORD, passwordInput = new PasswordInput());
			add(CoreI18n.REPEAT_PASSWORD, repeatedPasswordInput = new PasswordInput());
			add(CoreI18n.QUALITY, passwordQualityBar = new UserPasswordQualityBar(passwordPolicy));
		}

		public void updateQualityBar() {

			passwordQualityBar.update(getPassword());
		}

		public void setPassword(String password) {

			passwordInput.setPassword(password);
			repeatedPasswordInput.setPassword(password);
		}

		public String getPassword() {

			return passwordInput.getPassword();
		}

		public String getCurrentPassword() {

			return currentPasswordInput.getValue();
		}

		public String getRepeatedPassword() {

			return repeatedPasswordInput.getPassword();
		}

		public void setPasswordVisible(boolean visible) {

			passwordInput.setPasswordVisible(visible);
			repeatedPasswordInput.setPasswordVisible(visible);
		}
	}

	private class PasswordCheckButton extends DomButton {

		public PasswordCheckButton() {

			setIcon(CoreImages.EXECUTE.getResource());
			setLabel(CoreI18n.CHECK_PASSWORD);
			setClickCallback(this::handleClick);
		}

		public void handleClick() {

			checkPassword();
		}
	}

	private class PasswordSaveButton extends DomButton {

		public PasswordSaveButton() {

			setIcon(EmfImages.ENTITY_SAVE.getResource());
			setLabel(CoreI18n.SAVE_PASSWORD);
			setClickCallback(this::handleClick);
		}

		public void handleClick() {

			savePassword();
		}
	}

	private class PasswordInput extends DomPasswordInput implements IDomEventHandler {

		public PasswordInput() {

			setValue("");
			listenToEvent(DomEventType.CHANGE);
			listenToEvent(DomEventType.ENTER);
		}

		@Override
		public void handleDOMEvent(IDomEvent event) {

			switch (event.getType()) {
			case ENTER:
				savePassword();
				break;
			case CHANGE:
				inputTable.updateQualityBar();
				break;
			default:
				// ignored
			}
		}

		public void setPasswordVisible(boolean visible) {

			if (visible) {
				setAttribute("type", "text");
			} else {
				setAttribute("type", "password");
			}
		}

		public String getPassword() {

			return getValue();
		}

		public void setPassword(String password) {

			this.setValue(password);
		}
	}

	private class GeneratePasswordButton extends DomButton {

		public GeneratePasswordButton() {

			setIcon(CoreImages.EXECUTE.getResource());
			setLabel(CoreI18n.GENERATE_NEW_PASSWORD);
			setClickCallback(this::handleClick);
		}

		public void handleClick() {

			generatePassword();
		}
	}
}

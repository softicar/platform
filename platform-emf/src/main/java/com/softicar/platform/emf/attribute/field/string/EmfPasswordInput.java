package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomPasswordInput;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfPasswordInput extends AbstractDomValueInputDiv<String> implements IEmfInput<String> {

	private final DomPasswordInput passwordInput;
	private final PasswordVisibilityButton passwordVisibilityButton;
	private boolean passwordShown;

	public EmfPasswordInput() {

		this.passwordInput = new DomPasswordInput();
		this.passwordInput.addChangeCallback(this::executeChangeCallbacks);
		this.passwordInput.addCssClass(EmfCssClasses.EMF_PASSWORD_INPUT);
		this.passwordVisibilityButton = new PasswordVisibilityButton();
		hideText();
		appendChild(new DomBar(passwordInput, passwordVisibilityButton));
	}

	@Override
	public void setValue(String value) {

		passwordInput.setValue(value);
	}

	@Override
	public Optional<String> getValue() {

		return passwordInput.getValue();
	}

	private void showText() {

		passwordInput.setAttribute("type", "text");
		passwordInput.setAttribute("autocomplete", "");
		passwordShown = true;

		passwordVisibilityButton.addCssClass(DomCssPseudoClasses.ACTIVE);
		passwordVisibilityButton.setTitle(EmfI18n.HIDE_PASSWORD);
	}

	private void hideText() {

		passwordInput.setAttribute("type", "password");
		//Setting 'one-time-code' to prevent the browser of autofilling this input
		passwordInput.setAttribute("autocomplete", "one-time-code");
		passwordShown = false;

		passwordVisibilityButton.removeCssClass(DomCssPseudoClasses.ACTIVE);
		passwordVisibilityButton.setTitle(EmfI18n.SHOW_PASSWORD);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		passwordInput.setDisabled(disabled);
	}

	private class PasswordVisibilityButton extends DomButton {

		public PasswordVisibilityButton() {

			addCssClass(EmfCssClasses.EMF_PASSWORD_VISIBILITY_BUTTON);
			setIcon(EmfImages.SHOW_PASSWORD.getResource());
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			if (passwordShown) {
				hideText();
			} else {
				showText();
			}
		}
	}
}

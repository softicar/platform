package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.DomPasswordInput;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomInputException;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.input.IEmfInput;

public class EmfPasswordInput extends DomDiv implements IEmfInput<String> {

	private final DomPasswordInput passwordInput;
	private final PasswordVisibilityButton passwordVisibilityButton;
	private boolean passwordShown;

	public EmfPasswordInput() {

		this.passwordInput = appendChild(new DomPasswordInput());
		this.passwordVisibilityButton = appendChild(new PasswordVisibilityButton());
		hideText();
		setCssClass(EmfCssClasses.EMF_PASSWORD_INPUT);
	}

	@Override
	public void setValue(String value) {

		passwordInput.setValue(value);
	}

	@Override
	public String getValue() {

		return passwordInput.getValue();
	}

	@Override
	public String getValueOrThrow() throws DomInputException {

		return passwordInput.getValue();
	}

	public void showText() {

		passwordInput.setAttribute("type", "text");
		passwordInput.setAttribute("autocomplete", "");
		passwordShown = true;

		passwordVisibilityButton.addCssClass(DomCssPseudoClasses.ACTIVE);
	}

	public void hideText() {

		passwordInput.setAttribute("type", "password");
		//Setting 'one-time-code' to prevent the browser of autofilling this input
		passwordInput.setAttribute("autocomplete", "one-time-code");
		passwordShown = false;

		passwordVisibilityButton.removeCssClass(DomCssPseudoClasses.ACTIVE);
	}

	private class PasswordVisibilityButton extends DomImage implements IDomClickEventHandler {

		public PasswordVisibilityButton() {

			super(EmfImages.SHOW_PASSWORD.getResource());
			addCssClass(EmfCssClasses.EMF_PASSWORD_VISIBILITY_BUTTON);
		}

		@Override
		public void handleClick(IDomEvent event) {

			if (passwordShown) {
				hideText();
			} else {
				showText();
			}
		}
	}
}

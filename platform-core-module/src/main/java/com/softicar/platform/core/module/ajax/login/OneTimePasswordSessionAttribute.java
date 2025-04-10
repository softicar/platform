package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.ajax.session.AjaxSessionAttributeManager;
import java.util.Objects;
import jakarta.servlet.http.HttpSession;

class OneTimePasswordSessionAttribute {

	private OneTimePassword password;
	private int passwordIndex;

	public OneTimePasswordSessionAttribute() {

		this.password = null;
		this.passwordIndex = 1;
	}

	public static OneTimePasswordSessionAttribute getInstance(HttpSession session) {

		return new AjaxSessionAttributeManager(session)//
			.getOrAddInstance(OneTimePasswordSessionAttribute.class, OneTimePasswordSessionAttribute::new);
	}

	public synchronized OneTimePassword generateAndStorePasswort() {

		this.password = new OneTimePasswordGenerator().generate(passwordIndex);
		this.passwordIndex++;
		return password;
	}

	public synchronized boolean validatePassword(String givenPassword) {

		OneTimePassword expectedPassword = takePassword();
		if (expectedPassword != null && !expectedPassword.isExpired()) {
			return Objects.equals(givenPassword, expectedPassword.getText());
		} else {
			return false;
		}
	}

	protected OneTimePassword peekPassword() {

		return password;
	}

	private OneTimePassword takePassword() {

		OneTimePassword password = this.password;
		this.password = null;
		return password;
	}
}

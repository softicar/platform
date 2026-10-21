package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.core.module.user.AGUser;
import java.util.Objects;

/**
 * A one-time password which was sent to an {@link AGUser} via email, as the
 * second factor of a two-factor authentication.
 * <p>
 * In contrast to {@code OneTimePasswordSessionAttribute}, the one-time
 * password is bound to the user it was sent to. It can only be validated
 * once, and it expires after five minutes.
 *
 * @author Semsudin Mekanic
 */
public class OneTimePasswordChallenge {

	private final AGUser user;
	private final int passwordIndex;
	private OneTimePassword password;

	private OneTimePasswordChallenge(AGUser user, int passwordIndex) {

		this.user = Objects.requireNonNull(user);
		this.passwordIndex = passwordIndex;
		this.password = new OneTimePasswordGenerator().generate(passwordIndex);
	}

	/**
	 * Generates a new one-time password and sends it to the given
	 * {@link AGUser} via email.
	 *
	 * @param user
	 *            the {@link AGUser} trying to log in (never <i>null</i>)
	 * @param passwordIndex
	 *            the index of the one-time password, to be displayed to the
	 *            user
	 * @return the new {@link OneTimePasswordChallenge}
	 */
	public static OneTimePasswordChallenge sendTo(AGUser user, int passwordIndex) {

		var challenge = new OneTimePasswordChallenge(user, passwordIndex);
		new OneTimePasswordEmailSender(user).send(challenge.password);
		return challenge;
	}

	/**
	 * Returns the {@link AGUser} that the one-time password was sent to.
	 *
	 * @return the {@link AGUser} (never <i>null</i>)
	 */
	public AGUser getUser() {

		return user;
	}

	/**
	 * Returns the index of the one-time password, as displayed in the email.
	 *
	 * @return the index of the one-time password
	 */
	public int getPasswordIndex() {

		return passwordIndex;
	}

	/**
	 * Validates the given one-time password and invalidates it afterwards.
	 * <p>
	 * Leading and trailing whitespace is ignored, as well as the letter case.
	 *
	 * @param givenPassword
	 *            the one-time password entered by the user (may be
	 *            <i>null</i>)
	 * @return <i>true</i> if the given password is correct and not expired;
	 *         <i>false</i> otherwise
	 */
	public synchronized boolean validate(String givenPassword) {

		OneTimePassword expectedPassword = password;
		password = null;
		return expectedPassword != null//
				&& !expectedPassword.isExpired()
				&& givenPassword != null
				&& expectedPassword.getText().equalsIgnoreCase(givenPassword.trim());
	}

	OneTimePassword peekPassword() {

		return password;
	}
}

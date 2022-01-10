package com.softicar.platform.core.module.user.password;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.dialog.DomModalAlertPopup;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Provides a generation method for user passwords.
 *
 * @author Boris Schaa
 * @author Oliver Richers
 */
public class UserPasswordGenerator {

	private static final int GENERATION_ATTEMPT_COUNT = 100;
	private static final String LOWER_CONSONANTS = "bcdfghjklmnpqrstvwxyz";
	private static final String UPPER_CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZ";
	private static final String LOWER_VOWELS = "aeiou";
	private static final String UPPER_VOWELS = "AEIOU";
	private static final String SPECIAL_CHARS = "!#$%&*()_+-=<>;:?/";
	private static final String DIGITS = "0123456789";
	private static final String ALL = LOWER_CONSONANTS + UPPER_CONSONANTS + LOWER_VOWELS + UPPER_VOWELS + SPECIAL_CHARS + DIGITS;

	public static String generate() {

		return new UserPasswordGenerator().generatePassword();
	}

	public String generatePassword() {

		for (int i = 0; i < GENERATION_ATTEMPT_COUNT; ++i) {
			String password = generatePasswordOnce();
			if (SofticarPasswordPolicy.get().isFulfilled(password)) {
				return password;
			}
		}
		throw new SofticarUserException(CoreI18n.FAILED_TO_GENERATE_PASSWORD);
	}

	public void resetUserPassword(AGUser user) {

		if (user.getEmailAddress() == null || user.getEmailAddress().isEmpty()) {
			throw new SofticarUserException(
				CoreI18n.USER_ARG1_DOES_NOT_HAVE_AN_EMAIL_ADDRESS
					.toDisplay(user.getFirstAndLastName())
					.concatSentence(CoreI18n.USER_CANNOT_RECEIVE_NOTIFICATION_EMAILS));
		}

		try (DbTransaction transaction = new DbTransaction()) {
			String password = new UserPasswordGenerator().generatePassword();
			new UserPasswordUpdater(user, password).update();
			AGUser.sendPaswordResetNotification(user, password);
			new DomModalAlertPopup(
				CoreI18n.THE_PASSWORD_FOR_USER_ARG1_IS_NOW_ARG2
					.toDisplay(user.getLoginName(), password)
					.concat("\n\n")
					.concat(CoreI18n.IF_AN_EMAIL_SERVER_IS_CONFIGURED_THE_USER_WILL_RECEIVE_THIS_PASSWORD_VIA_EMAIL)).show();
			transaction.commit();
		} catch (Exception exception) {
			throw new SofticarUserException(exception, CoreI18n.COULD_NOT_RESET_USER_PASSWORD);
		}
	}

	private String generatePasswordOnce() {

		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();

		int minimumPasswordLength = SofticarPasswordPolicy.MINIMUM_PASSWORD_LENGTH;
		int passwordLength = minimumPasswordLength + random.nextInt(minimumPasswordLength / 2);
		for (int i = 0; i < passwordLength; ++i) {
			password.append(getRandomChar(random, ALL));
		}

		return password.toString();
	}

	private static char getRandomChar(Random random, String characters) {

		return characters.charAt(random.nextInt(characters.length()));
	}
}

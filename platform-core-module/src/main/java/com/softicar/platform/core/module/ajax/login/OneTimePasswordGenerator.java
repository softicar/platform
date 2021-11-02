package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.common.date.DayTime;
import java.security.SecureRandom;
import java.util.Random;

class OneTimePasswordGenerator {

	protected static final int PASSWORD_LENGTH = 5;

	public OneTimePassword generate(int passwordIndex) {

		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();

		for (int i = 0; i < PASSWORD_LENGTH; ++i) {
			password.append(getRandomChar(random, "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
		}

		return new OneTimePassword(passwordIndex, password.toString(), DayTime.now());
	}

	private static char getRandomChar(Random random, String characters) {

		return characters.charAt(random.nextInt(characters.length()));
	}
}

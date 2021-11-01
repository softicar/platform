package com.softicar.platform.core.module.email.address;

import java.util.Set;
import java.util.TreeSet;
import org.junit.Assert;
import org.junit.Test;

public class EmailAddressValidatorTest {

	@Test
	public void testValidAddresses() {

		Set<String> emails = new TreeSet<>();
		emails.add("user@domain.com");
		emails.add("user@domain.co.in");
		emails.add("user.name@domain.com");
		emails.add("user_name@domain.com");
		emails.add("username@yahoo.corporate.in");

		for (String email: emails) {
			Assert.assertTrue(new EmailAddessValidator(email).isValid());
		}
	}

	@Test
	public void testInvalidAddresses() {

		Set<String> emails = new TreeSet<>();
		emails.add(".username@yahoo.com");
		emails.add("username@yahoo.com.");
		emails.add("username@yahoo..com");
		emails.add("username@yahoo.c");
		emails.add("username@yahoo.corporate");
		emails.add("username@@yahoo.com");

		for (String email: emails) {
			Assert.assertFalse(new EmailAddessValidator(email).isValid());
		}
	}
}

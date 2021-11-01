package com.softicar.platform.core.module.email.address;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Compares an email address by the domain first and then by the local part.
 * <p>
 * This comparator is null-safe using {@link Comparator#nullsFirst}.
 *
 * @author Oliver Richers
 */
public class EmailAddressComparator implements Comparator<String> {

	@Override
	public int compare(String left, String right) {

		Comparator<String> comparator = Comparator//
			.comparing(EmailAddressComparator::getDomain)
			.thenComparing(Function.identity());
		return Comparator.nullsFirst(comparator).compare(left, right);
	}

	private static String getDomain(String address) {

		int index = address.indexOf('@');
		if (index > 0) {
			return address.substring(index + 1);
		} else {
			return "";
		}
	}
}

package com.softicar.platform.core.module.email.buffer;

import jakarta.mail.Address;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.util.Collection;

class BufferedEmailAddresses {

	public static InternetAddress parseAddress(String address) {

		try {
			return new InternetAddress(address.toString());
		} catch (AddressException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static InternetAddress[] parseAddresses(String address) {

		try {
			return InternetAddress.parse(address, true);
		} catch (AddressException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static String implodeAddresses(Collection<? extends Address> addresses) {

		return addresses.isEmpty()? "" : InternetAddress.toString(addresses.stream().toArray(Address[]::new));
	}
}

package com.softicar.platform.core.module.email.buffer;

import java.util.Collection;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

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

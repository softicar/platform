package com.softicar.platform.common.network.address;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Represents an IPv4 address.
 *
 * @author Oliver Richers
 */
public class Ipv4Address {

	private final int value;

	public Ipv4Address(int address) {

		this.value = address;
	}

	public Ipv4Address(String address) {

		this.value = new Ipv4AddressParser(address).parseToInteger();
	}

	public int getIntegerValue() {

		return value;
	}

	public int getPartValue(int index) {

		if (index < 0 || index > 3) {
			throw new IndexOutOfBoundsException();
		}

		return value >> (8 * (3 - index)) & 255;
	}

	public InetAddress toInetAddress() {

		byte[] bytes = new byte[4];
		bytes[0] = (byte) ((value >> 24) & 255);
		bytes[1] = (byte) ((value >> 16) & 255);
		bytes[2] = (byte) ((value >> 8) & 255);
		bytes[3] = (byte) (value & 255);
		try {
			return InetAddress.getByAddress(bytes);
		} catch (UnknownHostException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof Ipv4Address) {
			return value == ((Ipv4Address) object).value;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {

		return new StringBuilder()//
			.append(getPartValue(0))
			.append('.')
			.append(getPartValue(1))
			.append('.')
			.append(getPartValue(2))
			.append('.')
			.append(getPartValue(3))
			.toString();
	}
}

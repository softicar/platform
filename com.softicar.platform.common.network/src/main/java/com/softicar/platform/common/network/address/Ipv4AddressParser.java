package com.softicar.platform.common.network.address;

import com.softicar.platform.common.core.number.parser.IntegerParser;

/**
 * Parses a string representation of an IPv4 address into {@link Ipv4Address}.
 *
 * @author Oliver Richers
 */
public class Ipv4AddressParser {

	private final String address;

	public Ipv4AddressParser(String address) {

		this.address = address;
	}

	public Ipv4Address parse() {

		return new Ipv4Address(parseToInteger());
	}

	public int parseToInteger() {

		String[] parts = address.split("\\.");
		if (parts.length == 4) {
			return parseParts(parts);
		} else {
			throw new IllegalIpv4Address(address);
		}
	}

	private int parseParts(String[] parts) {

		int part0 = parsePart(parts[0]);
		int part1 = parsePart(parts[1]);
		int part2 = parsePart(parts[2]);
		int part3 = parsePart(parts[3]);
		return (part0 << 24) | (part1 << 16) | (part2 << 8) | part3;
	}

	private int parsePart(String part) {

		Integer number = IntegerParser.parseInteger(part);
		if (number == null) {
			throw new IllegalIpv4Address(address);
		}
		if (number < 0 || number > 255) {
			throw new IllegalIpv4Address(address);
		}
		return number;
	}
}

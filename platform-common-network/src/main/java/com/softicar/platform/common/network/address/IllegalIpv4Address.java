package com.softicar.platform.common.network.address;

public class IllegalIpv4Address extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalIpv4Address(String address) {

		super(String.format("The address '%s' is not a valid IPv4 address.", address));
	}
}

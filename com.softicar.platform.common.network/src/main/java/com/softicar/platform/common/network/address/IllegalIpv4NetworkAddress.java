package com.softicar.platform.common.network.address;

public class IllegalIpv4NetworkAddress extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalIpv4NetworkAddress(String address, String reason) {

		super(String.format("The address '%s' is not a valid IPv4 network address. %s", address, reason));
	}
}

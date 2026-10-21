package com.softicar.platform.core.module.web.servlet;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.network.address.IllegalIpv4Address;
import com.softicar.platform.common.network.address.Ipv4Address;
import com.softicar.platform.common.network.address.Ipv4NetworkAddress;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Determines the address of the client that sent an HTTP request.
 * <p>
 * The <i>X-Forwarded-For</i> header is only honored if the request was
 * received from a trusted proxy. In that case, the header is evaluated from
 * right to left, and the first address which is not a trusted proxy is
 * returned. Addresses further to the left were supplied by the client itself,
 * so they cannot be trusted.
 * <p>
 * Trusted proxies are specified as a list of IPv4 addresses and IPv4 networks
 * in CIDR notation, separated by commas or whitespace, e.g.
 * {@code "127.0.0.1, 10.0.0.0/8"}. An empty list disables the evaluation of
 * the <i>X-Forwarded-For</i> header.
 *
 * @author Semsudin Mekanic
 */
public class ClientAddressDeterminer {

	private final List<Predicate<Ipv4Address>> trustedProxyMatchers;

	/**
	 * @param trustedProxies
	 *            the list of trusted proxies (never <i>null</i>)
	 * @throws IllegalIpv4Address
	 *             if a trusted proxy is not a valid IPv4 address
	 * @throws com.softicar.platform.common.network.address.IllegalIpv4NetworkAddress
	 *             if a trusted proxy is not a valid IPv4 network
	 */
	public ClientAddressDeterminer(String trustedProxies) {

		this.trustedProxyMatchers = Arrays//
			.stream(trustedProxies.split("[,\\s]+"))
			.filter(proxy -> !proxy.isEmpty())
			.map(this::parseTrustedProxy)
			.toList();
	}

	/**
	 * Determines the client address.
	 *
	 * @param remoteAddress
	 *            the address of the peer that sent the request, as returned by
	 *            {@link jakarta.servlet.ServletRequest#getRemoteAddr()} (may be
	 *            <i>null</i>)
	 * @param forwardedFor
	 *            the value of the <i>X-Forwarded-For</i> header (may be
	 *            <i>null</i>)
	 * @return the client address (may be <i>null</i> if the remote address is
	 *         <i>null</i>)
	 */
	public String determine(String remoteAddress, String forwardedFor) {

		if (forwardedFor == null || !isTrustedProxy(remoteAddress)) {
			return remoteAddress;
		}

		List<String> forwardedAddresses = Arrays//
			.stream(forwardedFor.split(","))
			.map(String::trim)
			.filter(address -> !address.isEmpty())
			.toList();
		for (int i = forwardedAddresses.size() - 1; i >= 0; i--) {
			String address = forwardedAddresses.get(i);
			if (!isTrustedProxy(address)) {
				return address;
			}
		}
		return forwardedAddresses.isEmpty()? remoteAddress : forwardedAddresses.get(0);
	}

	private boolean isTrustedProxy(String address) {

		if (address == null) {
			return false;
		}
		try {
			var ipv4Address = new Ipv4Address(address);
			return trustedProxyMatchers.stream().anyMatch(matcher -> matcher.test(ipv4Address));
		} catch (IllegalIpv4Address exception) {
			DevNull.swallow(exception);
			return false;
		}
	}

	private Predicate<Ipv4Address> parseTrustedProxy(String proxy) {

		if (proxy.contains("/")) {
			return new Ipv4NetworkAddress(proxy)::contains;
		} else {
			return new Ipv4Address(proxy)::equals;
		}
	}
}

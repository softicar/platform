package com.softicar.platform.core.module.user.rule.ip;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.network.address.IllegalIpv4Address;
import com.softicar.platform.common.network.address.IllegalIpv4NetworkAddress;
import com.softicar.platform.common.network.address.Ipv4Address;
import com.softicar.platform.common.network.address.Ipv4NetworkAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserAllowedIpRuleParser {

	private final Collection<String> addresses;

	public UserAllowedIpRuleParser(AGUserAllowedIpRule rule) {

		this.addresses = Arrays//
			.asList(rule.getAddresses().split("\n"))
			.stream()
			.map(String::trim)
			.filter(address -> !address.isEmpty())
			.collect(Collectors.toList());
	}

	public Collection<String> validateAndGetIncorrectAddresses() {

		List<String> incorrectAddresses = new ArrayList<>();
		for (String address: addresses) {
			try {
				parseAddressIntoPredicate(address);
			} catch (IllegalIpv4Address | IllegalIpv4NetworkAddress exception) {
				DevNull.swallow(exception);
				incorrectAddresses.add(address);
			}
		}
		return incorrectAddresses;
	}

	public Collection<Predicate<Ipv4Address>> getMatchers() {

		return addresses//
			.stream()
			.map(this::parseAddressIntoPredicate)
			.collect(Collectors.toList());
	}

	public Predicate<Ipv4Address> parseAddressIntoPredicate(String address) {

		if (address.contains("/")) {
			return new Ipv4NetworkAddress(address)::contains;
		} else {
			return new Ipv4Address(address)::equals;
		}
	}
}

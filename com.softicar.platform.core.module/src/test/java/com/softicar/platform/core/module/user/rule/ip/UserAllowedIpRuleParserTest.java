package com.softicar.platform.core.module.user.rule.ip;

import com.softicar.platform.common.network.address.Ipv4Address;
import com.softicar.platform.common.network.address.Ipv4NetworkAddress;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class UserAllowedIpRuleParserTest extends AbstractDbTest {

	private static final String HOST_ADDRESS = "127.0.0.1";
	private static final String NETWORK_ADDRESS = "126.0.0.1/8";
	private final AGUserAllowedIpRule rule;

	public UserAllowedIpRuleParserTest() {

		this.rule = new AGUserAllowedIpRule()//
			.setActive(true)
			.setAddresses(HOST_ADDRESS + "\n" + NETWORK_ADDRESS)
			.setName("Test")
			.save();
	}

	@Test
	public void testHostAddressMatchingWithAllowedAddress() {

		assertTrue(
			new UserAllowedIpRuleParser(rule)//
				.getMatchers()
				.stream()
				.anyMatch(matcher -> matcher.test(new Ipv4Address(HOST_ADDRESS))));
	}

	@Test
	public void testHostAddressMatchingWithDisallowedAddress() {

		assertFalse(
			new UserAllowedIpRuleParser(rule)//
				.getMatchers()
				.stream()
				.anyMatch(matcher -> matcher.test(new Ipv4Address("127.0.0.2"))));
	}

	@Test
	public void testNetworkAddressMatchingWithAllowedAddress() {

		assertTrue(
			new UserAllowedIpRuleParser(rule)//
				.getMatchers()
				.stream()
				.anyMatch(
					matcher -> matcher.test(new Ipv4NetworkAddress(NETWORK_ADDRESS).getAddress()) //
							&& matcher.test(new Ipv4Address("126.1.2.3"))));
	}

	@Test
	public void testNetworkAddressMatchingWithDisallowedAddress() {

		assertFalse(
			new UserAllowedIpRuleParser(rule)//
				.getMatchers()
				.stream()
				.anyMatch(matcher -> matcher.test(new Ipv4Address("128.0.0.1"))));
	}
}

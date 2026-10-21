package com.softicar.platform.core.module.web.servlet;

import com.softicar.platform.common.network.address.IllegalIpv4Address;
import com.softicar.platform.common.network.address.IllegalIpv4NetworkAddress;
import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class ClientAddressDeterminerTest extends Asserts {

	private static final String PROXY = "127.0.0.1";
	private static final String INTERNAL_PROXY = "10.0.0.5";
	private static final String CLIENT = "203.0.113.7";
	private static final String SPOOFED = "198.51.100.66";

	private final ClientAddressDeterminer determiner = new ClientAddressDeterminer("127.0.0.1, 10.0.0.0/8");

	@Test
	public void testWithoutForwardedFor() {

		assertEquals(CLIENT, determiner.determine(CLIENT, null));
		assertEquals(PROXY, determiner.determine(PROXY, null));
	}

	@Test
	public void testWithForwardedForFromUntrustedPeer() {

		assertEquals(CLIENT, determiner.determine(CLIENT, SPOOFED));
	}

	@Test
	public void testWithForwardedForFromTrustedProxy() {

		assertEquals(CLIENT, determiner.determine(PROXY, CLIENT));
	}

	@Test
	public void testWithForwardedForSpoofedByClient() {

		assertEquals(CLIENT, determiner.determine(PROXY, SPOOFED + ", " + CLIENT));
	}

	@Test
	public void testWithForwardedForThroughProxyChain() {

		assertEquals(CLIENT, determiner.determine(PROXY, SPOOFED + ", " + CLIENT + ", " + INTERNAL_PROXY));
	}

	@Test
	public void testWithForwardedForContainingOnlyTrustedProxies() {

		assertEquals("10.1.2.3", determiner.determine(PROXY, "10.1.2.3, " + INTERNAL_PROXY));
	}

	@Test
	public void testWithForwardedForContainingNonIpv4Address() {

		assertEquals("unknown", determiner.determine(PROXY, SPOOFED + ", unknown"));
		assertEquals("2001:db8::1", determiner.determine(PROXY, "2001:db8::1"));
	}

	@Test
	public void testWithEmptyForwardedFor() {

		assertEquals(PROXY, determiner.determine(PROXY, ""));
		assertEquals(PROXY, determiner.determine(PROXY, " , "));
	}

	@Test
	public void testWithNonIpv4RemoteAddress() {

		assertEquals("0:0:0:0:0:0:0:1", determiner.determine("0:0:0:0:0:0:0:1", CLIENT));
	}

	@Test
	public void testWithNullRemoteAddress() {

		assertNull(determiner.determine(null, CLIENT));
	}

	@Test
	public void testWithEmptyTrustedProxies() {

		assertEquals(PROXY, new ClientAddressDeterminer("").determine(PROXY, CLIENT));
	}

	@Test
	public void testWithWhitespaceSeparatedTrustedProxies() {

		assertEquals(CLIENT, new ClientAddressDeterminer("192.168.0.1\n127.0.0.1").determine(PROXY, CLIENT));
	}

	@Test
	public void testWithIllegalTrustedProxies() {

		assertException(IllegalIpv4Address.class, () -> new ClientAddressDeterminer("localhost"));
		assertException(IllegalIpv4NetworkAddress.class, () -> new ClientAddressDeterminer("10.0.0.0/33"));
	}
}

package com.softicar.platform.common.network.address;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class Ipv4AddressTest extends AbstractTest {

	@Test
	public void testToString() {

		// border cases
		assertEquals("0.0.0.0", new Ipv4Address(0x00000000).toString());
		assertEquals("0.0.0.1", new Ipv4Address(0x00000001).toString());
		assertEquals("0.0.0.255", new Ipv4Address(0x000000ff).toString());
		assertEquals("0.0.1.0", new Ipv4Address(0x00000100).toString());
		assertEquals("1.255.255.255", new Ipv4Address(0x01ffffff).toString());
		assertEquals("255.255.255.255", new Ipv4Address(0xffffffff).toString());

		// example addresses
		assertEquals("127.0.0.1", new Ipv4Address(0x7f000001).toString());
		assertEquals("192.168.1.1", new Ipv4Address(0xc0a80101).toString());
		assertEquals("10.1.2.3", new Ipv4Address(0x0a010203).toString());
	}

	@Test
	public void testParsing() {

		// border cases
		assertEquals(0x0000000, new Ipv4Address("0.0.0.0").getIntegerValue());
		assertEquals(0x00000001, new Ipv4Address("0.0.0.1").getIntegerValue());
		assertEquals(0x000000ff, new Ipv4Address("0.0.0.255").getIntegerValue());
		assertEquals(0x00000100, new Ipv4Address("0.0.1.0").getIntegerValue());
		assertEquals(0x01ffffff, new Ipv4Address("1.255.255.255").getIntegerValue());
		assertEquals(0xffffffff, new Ipv4Address("255.255.255.255").getIntegerValue());

		// example addresses
		assertEquals(0x7f000001, new Ipv4Address("127.0.0.1").getIntegerValue());
		assertEquals(0xc0a80101, new Ipv4Address("192.168.1.1").getIntegerValue());
		assertEquals(0x0a010203, new Ipv4Address("10.1.2.3").getIntegerValue());
	}

	@Test
	public void testToInetAddress() {

		assertEquals("127.0.0.1", new Ipv4Address("127.0.0.1").toInetAddress().getHostAddress());
		assertEquals("192.168.1.1", new Ipv4Address("192.168.1.1").toInetAddress().getHostAddress());
		assertEquals("10.1.2.3", new Ipv4Address("10.1.2.3").toInetAddress().getHostAddress());
	}
}

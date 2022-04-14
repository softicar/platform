package com.softicar.platform.common.network.address;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class Ipv4NetworkAddressTest extends AbstractTest {

	@Test
	public void testToString() {

		assertEquals("0.0.0.0/0", new Ipv4NetworkAddress(0x00000000, 0).toString());
		assertEquals("192.168.1.0/24", new Ipv4NetworkAddress(0xc0a80100, 24).toString());
		assertEquals("255.255.255.255/32", new Ipv4NetworkAddress(0xffffffff, 32).toString());
	}

	@Test
	public void testParsing() {

		assertEquals(0x00000000, new Ipv4NetworkAddress("0.0.0.0/0").getAddress().getIntegerValue());
		assertEquals(0xc0a80100, new Ipv4NetworkAddress("192.168.1.0/24").getAddress().getIntegerValue());
		assertEquals(0xffffffff, new Ipv4NetworkAddress("255.255.255.255/32").getAddress().getIntegerValue());

		assertEquals(0, new Ipv4NetworkAddress("0.0.0.0/0").getBits());
		assertEquals(24, new Ipv4NetworkAddress("192.168.1.0/24").getBits());
		assertEquals(32, new Ipv4NetworkAddress("255.255.255.255/32").getBits());
	}

	@Test
	public void testIntegerMask() {

		assertEquals(0x00000000, new Ipv4NetworkAddress("0.0.0.0/0").getIntegerMask());
		assertEquals(0x80000000, new Ipv4NetworkAddress("0.0.0.0/1").getIntegerMask());
		assertEquals(0xc0000000, new Ipv4NetworkAddress("0.0.0.0/2").getIntegerMask());
		assertEquals(0xe0000000, new Ipv4NetworkAddress("0.0.0.0/3").getIntegerMask());
		assertEquals(0xf0000000, new Ipv4NetworkAddress("0.0.0.0/4").getIntegerMask());
		assertEquals(0xfffffc00, new Ipv4NetworkAddress("192.168.0.0/22").getIntegerMask());
		assertEquals(0xffffff00, new Ipv4NetworkAddress("192.168.1.0/24").getIntegerMask());
		assertEquals(0xffffffff, new Ipv4NetworkAddress("255.255.255.255/32").getIntegerMask());
	}

	@Test
	public void testContains() {

		assertTrue(new Ipv4NetworkAddress("192.168.1.0/24").contains(new Ipv4Address("192.168.1.0")));
		assertTrue(new Ipv4NetworkAddress("192.168.1.0/24").contains(new Ipv4Address("192.168.1.23")));
		assertTrue(new Ipv4NetworkAddress("192.168.1.0/24").contains(new Ipv4Address("192.168.1.255")));

		assertFalse(new Ipv4NetworkAddress("192.168.1.0/24").contains(new Ipv4Address("192.168.0.255")));
		assertFalse(new Ipv4NetworkAddress("192.168.1.0/24").contains(new Ipv4Address("192.168.2.0")));
	}
}

package com.softicar.platform.core.module.email.address;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class EmailAddressComparatorTest extends AbstractTest {

	@Test
	public void test() {

		assertEquals(-1, new EmailAddressComparator().compare("A@X", "B@X"));
		assertEquals(+1, new EmailAddressComparator().compare("A@Y", "B@X"));
	}

	@Test
	public void testWithMissingDomain() {

		assertEquals(-1, new EmailAddressComparator().compare("A", "B"));
		assertEquals(+1, new EmailAddressComparator().compare("A@X", "B"));
		assertEquals(-1, new EmailAddressComparator().compare("A", "B@Y"));
	}

	@Test
	public void testWithNull() {

		assertEquals(0, new EmailAddressComparator().compare(null, null));
		assertEquals(+1, new EmailAddressComparator().compare("A", null));
		assertEquals(-1, new EmailAddressComparator().compare(null, "A"));
	}
}

package com.softicar.platform.core.module.page.header;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import org.junit.Test;

public class PageHeaderUserPopoverButtonTest extends AbstractCoreTest {

	@Test
	public void testGetInitials() {

		assertEquals("AU", PageHeaderUserPopoverButton.getInitials("Admin User"));
		assertEquals("ÉM", PageHeaderUserPopoverButton.getInitials("  élodie   martin  "));
		assertEquals("AB", PageHeaderUserPopoverButton.getInitials("anna berta carla"));
		assertEquals("A", PageHeaderUserPopoverButton.getInitials("anna"));
		assertEquals("", PageHeaderUserPopoverButton.getInitials("   "));
	}
}

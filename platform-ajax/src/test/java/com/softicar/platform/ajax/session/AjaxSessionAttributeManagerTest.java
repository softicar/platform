package com.softicar.platform.ajax.session;

import com.softicar.platform.ajax.simple.SimpleHttpSession;
import com.softicar.platform.common.testing.AbstractTest;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;

public class AjaxSessionAttributeManagerTest extends AbstractTest {

	private final HttpSession session;

	public AjaxSessionAttributeManagerTest() {

		this.session = new SimpleHttpSession("");
	}

	@Test
	public void testGetOrAddInstance() {

		TestSingleton singleton1 = new AjaxSessionAttributeManager(session)//
			.getOrAddInstance(TestSingleton.class, TestSingleton::new);
		TestSingleton singleton2 = new AjaxSessionAttributeManager(session)//
			.getOrAddInstance(TestSingleton.class, TestSingleton::new);

		assertNotNull(singleton1);
		assertSame(singleton1, singleton2);
	}

	private static class TestSingleton {

		// nothing to add
	}
}

package com.softicar.platform.emf.table.listener;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class EmfTableListenerSettingsTest extends AbstractDbTest {

	@Test
	public void testWithoutTransaction() {

		assertTrue(EmfTableListenerSettings.isLoggingEnabled());

		// without an active transaction, disabling has no effect
		EmfTableListenerSettings.setLoggingEnabled(false);
		assertTrue(EmfTableListenerSettings.isLoggingEnabled());
	}

	@Test
	public void test() {

		try (var transaction = new DbTransaction()) {
			assertTrue(EmfTableListenerSettings.isLoggingEnabled());

			EmfTableListenerSettings.setLoggingEnabled(false);
			assertFalse(EmfTableListenerSettings.isLoggingEnabled());

			try (var nestedTransaction = new DbTransaction()) {
				assertFalse(EmfTableListenerSettings.isLoggingEnabled());

				EmfTableListenerSettings.setLoggingEnabled(true);
				assertTrue(EmfTableListenerSettings.isLoggingEnabled());
			}

			assertFalse(EmfTableListenerSettings.isLoggingEnabled());
		}

		try (var transaction = new DbTransaction()) {
			assertTrue(EmfTableListenerSettings.isLoggingEnabled());
		}
	}
}

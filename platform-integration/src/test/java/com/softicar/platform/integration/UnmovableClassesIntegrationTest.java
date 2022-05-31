package com.softicar.platform.integration;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Ensures that specific classes, which are referenced by canonical name, are
 * not erroneously moved or renamed.
 */
public class UnmovableClassesIntegrationTest extends AbstractTest {

	@Test
	public void testUnmovableClasses() {

		assertClassExists("com.softicar.platform.core.module.program.ProgramStarter");
	}

	private static void assertClassExists(String canonicalName) {

		try {
			Class.forName(canonicalName);
		} catch (ClassNotFoundException exception) {
			String message = String.format("Failed to load unmovable class: '%s'", canonicalName);
			throw new AssertionError(message, exception);
		}
	}
}

package com.softicar.platform.common.io.file.name;

import org.junit.Assert;
import org.junit.Test;

public class FilenameCleanerTest extends Assert {

	@Test
	public void test() {

		assertEquals("clean-filename", new FilenameCleaner("clean-filename").getCleanFilename());

		assertEquals("backslash_plus_slash_", new FilenameCleaner("backslash\\plus+slash/").getCleanFilename());
		assertEquals("space_quote_doublequote_", new FilenameCleaner("space quote'doublequote\"").getCleanFilename());

		assertEquals("with-aeoeue-characters", new FilenameCleaner("with-äöü-characters").getCleanFilename());
		assertEquals("with-AeOeUe-characters", new FilenameCleaner("with-ÄÖÜ-characters").getCleanFilename());
		assertEquals("with-ss-character", new FilenameCleaner("with-ß-character").getCleanFilename());
	}
}

package com.softicar.platform.db.structure.utils;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class DbColumnCommentParserTest extends AbstractTest {

	private static final String NAME_OVERRIDE = "someStupidName";

	@Test
	public void testNameOverrideComment() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser(String.format("@name=%s@", NAME_OVERRIDE));
		assertEquals(NAME_OVERRIDE, commentParser.getNameOverride());
	}

	@Test
	public void testWrongNameOverrideComment() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser(String.format("@name=%s", NAME_OVERRIDE));
		assertEquals(null, commentParser.getNameOverride());
	}

	@Test
	public void testIgnoreOverrideComment() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser("@ignore@");
		assertTrue(commentParser.isIgnoreOverride());
	}

	@Test
	public void testWrongIgnoreOverrideComment() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser("ignore@");
		assertFalse(commentParser.isIgnoreOverride());
	}

	@Test
	public void testDoubleOverrideComment() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser("@double@");
		assertTrue(commentParser.isDoubleOverride());
	}

	@Test
	public void testWrongDoubleOverrideComment() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser("double");
		assertFalse(commentParser.isDoubleOverride());
	}

	@Test
	public void testBaseComment() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser("@base@");
		assertTrue(commentParser.isBaseColumn());
	}

	@Test
	public void testWrongBaseComment() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser("@@base@@");
		assertFalse(commentParser.isBaseColumn());
	}

	@Test
	public void testNameAndIgnoreOverrideComments() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser(String.format("@name=%s,ignore@", NAME_OVERRIDE));
		assertEquals(NAME_OVERRIDE, commentParser.getNameOverride());
		assertTrue(commentParser.isIgnoreOverride());
	}

	@Test
	public void testAllParsedComments() {

		DbColumnCommentParser commentParser = new DbColumnCommentParser(String.format("@name=%s,ignore,base,double@", NAME_OVERRIDE));
		assertEquals(NAME_OVERRIDE, commentParser.getNameOverride());
		assertTrue(commentParser.isDoubleOverride());
		assertTrue(commentParser.isIgnoreOverride());
		assertTrue(commentParser.isBaseColumn());
	}
}

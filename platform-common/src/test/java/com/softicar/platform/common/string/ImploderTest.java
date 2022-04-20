package com.softicar.platform.common.string;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * @author Robert Kühne
 */
public class ImploderTest extends AbstractTest {

	@Test
	public void implodeEmptyArray() {

		String s = Imploder.implode(new String[] {}, "sep");

		assertEquals("", s);
	}

	@Test
	public void implodeSingleEntryArray() {

		String s = Imploder.implode(new String[] { "House" }, "Wall");

		assertEquals("House", s);
	}

	@Test
	public void implodeArray() {

		String s = Imploder.implode(new String[] { "a", "b", "c" }, ":");

		assertEquals("a:b:c", s);
	}

	@Test
	public void repeat() {

		String s = Imploder.implode("Cat", 3, "woman");

		assertEquals("CatwomanCatwomanCat", s);
	}

	@Test
	public void repeatEmptySeparator() {

		String s = Imploder.implode("Emma", 3, "");

		assertEquals("EmmaEmmaEmma", s);
	}

	@Test
	public void repeatEmptyItem() {

		String s = Imploder.implode("", 4, "sep");

		assertEquals("sepsepsep", s);
	}

	@Test
	public void repeatNegativeCount() {

		String s = Imploder.implode("Batman", -3, "");

		assertEquals("", s);
	}

	@Test
	public void implodeQuotedNoEntry() {

		String s = Imploder.implodeQuoted(new String[] {}, ", ", "X");

		assertEquals("", s);
	}

	@Test
	public void implodeQuotedOneEntry() {

		String s = Imploder.implodeQuoted(new String[] { "Garfield" }, ", ", "X");

		assertEquals("XGarfieldX", s);
	}

	@Test
	public void implodeQuoted() {

		String s = Imploder.implodeQuoted(new String[] { "Batman", "Robin" }, ", ", "+++");

		assertEquals("+++Batman+++, +++Robin+++", s);
	}
}

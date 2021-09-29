package com.softicar.platform.common.code.java;

import com.softicar.platform.common.code.java.WordFragment.Type;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class WordFragmentTest extends Assert {

	@Test
	public void testParse() {

		List<WordFragment> list = WordFragment.parse("TCPCompanyID");

		assertEquals(3, list.size());

		assertEquals("TCP", list.get(0).getText());
		assertEquals(Type.UPPER, list.get(0).getType());

		assertEquals("Company", list.get(1).getText());
		assertEquals(Type.NORMAL, list.get(1).getType());

		assertEquals("ID", list.get(2).getText());
		assertEquals(Type.UPPER, list.get(2).getType());
	}
}

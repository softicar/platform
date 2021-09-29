package com.softicar.platform.common.string;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class TokenizerTest extends Assert {

	@Test
	public void test() {

		List<String> list = Tokenizer.tokenize("ARD+44929470000::CFF DOS D AB?+AC VP2 TECTONIK+0:PCE+5500112182", '+', '?');

		assertEquals(4, list.size());
		assertEquals("ARD", list.get(0));
		assertEquals("44929470000::CFF DOS D AB+AC VP2 TECTONIK", list.get(1));
		assertEquals("0:PCE", list.get(2));
		assertEquals("5500112182", list.get(3));
	}
}

package com.softicar.platform.common.string.tag;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Before;
import org.junit.Test;

public class TagReplacerTest extends AbstractTest {

	private TagReplacer replacer;

	@Before
	public void before() {

		this.replacer = new TagReplacer();
		replacer.addTag("foo", "bar");
	}

	@Test
	public void replacesNothingIfNoTagFound() {

		String result = replacer.replace("this is foo!");

		assertEquals("this is foo!", result);
	}

	@Test
	public void replacesTagWithReplacement() {

		String result = replacer.replace("this is {{foo}}!");

		assertEquals("this is bar!", result);
	}

	@Test
	public void replacesTagSeveralTimes() {

		String result = replacer.replace("this is {{foo}}, or not {{foo}}?");

		assertEquals("this is bar, or not bar?", result);
	}

	@Test
	public void supportsLineBreaks() {

		String result = replacer.replace("this is\n{{foo}}.");

		assertEquals("this is\nbar.", result);
	}

	@Test
	public void supportsCustomPattetrn1() {

		TagReplacer replacer = new TagReplacer("<(.*?)>");
		replacer.addTag("foo", "FOO");
		replacer.addTag("bar", "BAR");

		String result = replacer.replace("this is <foo> and not <bar>!");

		assertEquals("this is FOO and not BAR!", result);
	}

	@Test
	public void supportsCustomPattern2() {

		TagReplacer replacer = new TagReplacer("XX([a-z]+)XX");
		replacer.addTag("foo", "FOO");
		replacer.addTag("bar", "BAR");

		String result = replacer.replace("this is XXfooXX and not XXbarXX!");

		assertEquals("this is FOO and not BAR!", result);
	}

	@Test(expected = SofticarException.class)
	public void throwsOnUnknownTag() {

		replacer.replace("this is {{xxx}}!");
	}
}

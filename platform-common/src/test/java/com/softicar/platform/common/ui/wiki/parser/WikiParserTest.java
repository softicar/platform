package com.softicar.platform.common.ui.wiki.parser;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.ui.wiki.element.block.WikiDocument;
import org.junit.Test;

public class WikiParserTest extends AbstractTest {

	// -------------------- formatting -------------------- //

	@Test
	public void testBold() {

		assertParsed("P(T(Hello )B(T(world))T(.))", "Hello **world**.");
	}

	@Test
	public void testMonospaceWithDoubleSingleQuotes() {

		assertParsed("P(T(Hello )M(T(world))T(.))", "Hello ''world''.");
	}

	@Test
	public void testMonospaceWithBackticksQuotes() {

		assertParsed("P(T(Hello )M(T(world))T(.))", "Hello `world`.");
	}

	@Test
	public void testBoldInItalic() {

		assertParsed("P(I(T(Hello )B(T(world)))T(.))", "//Hello **world**//.");
	}

	@Test
	public void testMonospaceInUnderline() {

		assertParsed("P(U(T(Hello )M(T(world)))T(.))", "__Hello ''world''__.");
	}

	// -------------------- paragraph and line break -------------------- //

	@Test
	public void testLineBreak() {

		assertParsed("P(T(Hello)BR()T(world))", "Hello\\\\ world");
	}

	@Test
	public void testNonLineBreak() {

		assertParsed("P(T(Hello\\\\world))", "Hello\\\\world");
	}

	@Test
	public void testImplicitParagraph() {

		assertParsed("P(T(Hello))", "Hello");
		assertParsed("H2(T(Hello))P(T(world))", "==Hello== world");
	}

	@Test
	public void testParagraph() {

		assertParsed("P(T(Hello))", "\n\nHello");
		assertParsed("P(T(Hello))P(T(world))", "Hello\n\nworld");
		assertParsed("P(T(Hello))P(T(world))", "Hello\n\n\nworld");
		assertParsed("P(T(Hello))P(T(world))", "Hello\n\n\n\nworld");
	}

	@Test
	public void testNonParagraph() {

		assertParsed("P(T(Hello world))", "Hello\nworld");
	}

	// -------------------- headline -------------------- //

	@Test
	public void testHeadline() {

		assertParsed("H2(T(Hello))", "==Hello==");
		assertParsed("H3(T(Hello))", "===Hello===");
		assertParsed("H4(T(Hello))", "====Hello====");
		assertParsed("H5(T(Hello))", "=====Hello=====");
		assertParsed("H6(T(Hello))", "======Hello======");
	}

	@Test
	public void testHeadlineWithSpaces() {

		assertParsed("H2(T(Hello))", "==Hello==");
		assertParsed("H3(T(Hello))", "=== Hello===");
		assertParsed("H4(T(Hello))", "==== Hello ====");
	}

	@Test
	public void testBadHeadline() {

		assertParsed("P(T(=Hello=))", "=Hello=");
		assertParsed("H3(T(Hello))P(T(world))", "===Hello==world");
		assertParsed("H6(T(Hello))", "========Hello==");
	}

	// -------------------- text -------------------- //

	@Test
	public void testText() {

		assertParsed("P(T(Hello world))", "Hello\n\tworld");
		assertParsed("P(T(Hello world))", " \nHello  world\t");
	}

	// -------------------- lists -------------------- //

	@Test
	public void testLists() {

		assertParsed("UL1(LI(T(Hello)))", "  * Hello");
		assertParsed("OL1(LI(T(Hello)))", "  - Hello");

		assertParsed("UL1(LI(T(Hello))LI(T(world)))", "  * Hello  * world");
		assertParsed("UL1(LI(T(Hello)))OL1(LI(T(world)))", "  * Hello  - world");
		assertParsed("UL1(LI(T(Hello)UL2(LI(T(world))))LI(T(there)))", "  * Hello    * world  * there");
	}

	@Test
	public void testListsWithNesting() {

		assertParsed("UL1(LI(T(One)UL2(LI(T(Sub))))LI(T(Two)))", "  * One\n    * Sub\n  * Two");
		assertParsed("OL1(LI(T(One)UL2(LI(T(Sub))))LI(T(Two)))", "  - One\n    * Sub\n  - Two");
		assertParsed("OL1(LI(T(One)UL2(LI(T(Sub)))))UL1(LI(T(Two)))", "  - One\n    * Sub\n  * Two");
	}

	@Test
	public void testListsWithWhitespace() {

		assertParsed("UL1(LI(T(Hello )B(T(World)))LI(T(Hello )I(T(World ))))", "  * Hello **World**\n  * Hello //World //");
	}

	// -------------------- box -------------------- //

	@Test
	public void testBox() {

		assertParsed("ERROR(T(Hello))", "<error>Hello</error>");
		assertParsed("INFO(UL1(LI(T(Hello))))", "<info>  *Hello</info>");
		assertParsed("WARNING(URL(http://|)B(T(Hello)))", "<warning>[[http://]]**Hello**</warning>");
	}

	@Test
	public void testBoxWithListItems() {

		assertParsed("ERROR(T(Hello )B(T(world))UL1(LI(T(Foo))LI(T(Bar))))", "<error>\nHello **world**\n  * Foo\n  * Bar</error>\n");
	}

	// -------------------- resource and URL -------------------- //

	@Test
	public void testResource() {

		assertParsed("P(RSC(http://www.example.com|))", "{{http://www.example.com}}");
		assertParsed("P(RSC(http://www.example.com|Label))", "{{http://www.example.com|Label}}");
	}

	@Test
	public void testUrl() {

		assertParsed("P(URL(http://www.example.com|))", "[[http://www.example.com]]");
		assertParsed("P(URL(http://www.example.com|Label))", "[[http://www.example.com|Label]]");
	}

	// -------------------- raw text -------------------- //

	@Test
	public void testRawText() {

		assertParsed("P(CODE(foo)B(T(bar)))", "<code>foo</code>**bar**");
		assertParsed("P(T(foo)CODE(bar)T(foo))", "foo<code>bar</code>foo");
		assertParsed("CODE(int foo() {\n\treturn 1;\n})", "<code>int foo() {\n\treturn 1;\n}</code>");

		assertParsed("P(PRE(==Foo==  * Bar))", "<pre>==Foo==  * Bar</pre>");
		assertParsed("PRE(  - one\n  - two)", "<pre>\n  - one\n  - two</pre>");
	}

	// -------------------- table -------------------- //

	@Test
	public void testTable() {

		assertParsed("TAB(TR(TH(foo)TH(bar))TR(TD(1)TD(2)))", "^ foo ^ bar ^\n| 1 | 2 |");
		assertParsed("TAB(TR(TH(foo)TD(bar)))", "^ foo | bar |");
	}

	// -------------------- private -------------------- //

	private void assertParsed(String expected, String wikiText) {

		WikiDocument document = new WikiParser().parse(wikiText);
		assertEquals(expected, new WikiStringifier().stringify(document));
	}
}

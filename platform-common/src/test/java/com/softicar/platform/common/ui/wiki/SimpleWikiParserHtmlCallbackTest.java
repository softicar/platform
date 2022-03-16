package com.softicar.platform.common.ui.wiki;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SimpleWikiParserHtmlCallbackTest {

	private final SimpleWikiParserHtmlCallback callback;
	private final SimpleWikiParser parser;

	public SimpleWikiParserHtmlCallbackTest() {

		this.callback = new SimpleWikiParserHtmlCallback();
		this.parser = new SimpleWikiParser(callback);
	}

	@Test
	public void reducesWhitespaceToSpace() {

		assertEquals("<p>hello world</p>", parse("hello  world"));
		assertEquals("<p>hello world</p>", parse("hello\tworld"));
		assertEquals("<p>hello world</p>", parse("hello\nworld"));
	}

	@Test
	public void parseBold() {

		assertEquals("<p>hello <b>world</b></p>", parse("hello **world**"));
	}

	@Test
	public void parseBoldAtStart() {

		assertEquals("<p><b>hello</b> world</p>", parse("**hello** world"));
	}

	@Test
	public void parseItalic() {

		assertEquals("<p>hello <i>world</i></p>", parse("hello //world//"));
	}

	@Test
	public void parseItalicAtStart() {

		assertEquals("<p><i>hello</i> world</p>", parse("//hello// world"));
	}

	@Test
	public void parseDoubleBackslash() {

		assertEquals("<p>hello<br>world</p>", parse("hello\\\\world"));
	}

	@Test
	public void parseColor() {

		assertEquals("<p>hello <span style='color: #ABCDEF'>world</span></p>", parse("hello <color #abcdef>world</color>"));
	}

	@Test
	public void parseHeadlines() {

		assertEquals("<p>hello </p><div><h1>world</h1></div>", parse("hello ======world======"));
		assertEquals("<p>hello </p><div><h2>world</h2></div>", parse("hello =====world====="));
		assertEquals("<p>hello </p><div><h3>world</h3></div>", parse("hello ====world===="));
		assertEquals("<p>hello </p><div><h4>world</h4></div>", parse("hello ===world==="));
		assertEquals("<p>hello </p><div><h5>world</h5></div>", parse("hello ==world=="));
	}

	@Test
	public void parseSections() {

		addLine("======Section 1======");
		addLine("text");
		addLine("=====Section 2=====");
		addLine("text");
		assertEquals("<div><h1>Section 1</h1><p> text </p></div><div><h2>Section 2</h2><p> text </p></div>", parse());
	}

	@Test
	public void parseSectionsWithLists() {

		addLine("==Section 1==");
		addLine("  * text");
		addLine("==Section 2==");
		addLine("  * text");
		assertEquals("<div><h5>Section 1</h5><p> <ul><li>text</li></ul></p></div><div><h5>Section 2</h5><p> <ul><li>text</li></ul></p></div>", parse());
	}

	@Test
	public void parseBoldBeforeAndAfterLists() {

		addLine("**AAA**");
		addLine("  * text");
		addLine("**BBB**");
		addLine("  * text");
		assertEquals("<p><b>AAA</b> <ul><li>text</li></ul><b>BBB</b> <ul><li>text</li></ul></p>", parse());
	}

	@Test
	public void parseItalicBeforeAndAfterWithLists() {

		addLine("//AAA//");
		addLine("  * text");
		addLine("//BBB//");
		addLine("  * text");
		assertEquals("<p><i>AAA</i> <ul><li>text</li></ul><i>BBB</i> <ul><li>text</li></ul></p>", parse());
	}

	@Test
	public void parseBoldAndItalicWithinListItem() {

		addLine("  * this is **bold** text");
		addLine("  * this is //italic// text");
		assertEquals("<ul><li>this is <b>bold</b> text</li><li>this is <i>italic</i> text</li></ul>", parse());
	}

	@Test
	public void parseNewParagraph() {

		assertEquals("<p>hello</p><p>world</p>", parse("hello\n\nworld"));
	}

	@Test
	public void parseUnorderedList() {

		addLine("  * hello");
		addLine("  * world");
		assertEquals("<ul><li>hello</li><li>world</li></ul>", parse());
	}

	@Test
	public void parseOrderedList() {

		addLine("  - hello");
		addLine("  - world");
		assertEquals("<ol><li>hello</li><li>world</li></ol>", parse());
	}

	@Test
	public void parseSubLists() {

		addLine("  * hello");
		addLine("    - world");
		assertEquals("<ul><li>hello</li><ol><li>world</li></ol></ul>", parse());
	}

	@Test
	public void parseParagraphAfterList() {

		addLine("  * hello");
		addLine("world");
		assertEquals("<ul><li>hello</li></ul><p>world </p>", parse());
	}

	private void addLine(String line) {

		parser.parseLine(line);
	}

	private String parse() {

		parser.flush();
		return callback.getHtml();
	}

	private String parse(String wikiText) {

		callback.clear();
		parser.parseText(wikiText);
		parser.flush();
		return callback.getHtml();
	}
}

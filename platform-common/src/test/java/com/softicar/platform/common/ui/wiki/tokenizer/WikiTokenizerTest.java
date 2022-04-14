package com.softicar.platform.common.ui.wiki.tokenizer;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.Test;

public class WikiTokenizerTest extends AbstractTest {

	@Test
	public void testBold() {

		assertTokens("T(foo) B(**) T(bar)", "foo**bar");
		assertTokens("T(foo) B(**) T( bar)", "foo** bar");
		assertTokens("T(foo ) B(**) T(bar)", "foo **bar");
	}

	@Test
	public void testHeadline() {

		assertTokens("H(===) T( Hello ) H(==) T(world)", "=== Hello ==world");
		assertTokens("H(=======) T( Hello)", "======= Hello");
	}

	@Test
	public void testItalic() {

		assertTokens("T(foo) I(//) T(bar)", "foo//bar");
		assertTokens("T(foo) I(//) T( bar)", "foo// bar");
		assertTokens("T(foo ) I(//) T(bar)", "foo //bar");
	}

	@Test
	public void testLineBreak() {

		assertTokens("T(\\\\Hello) LB(\\\\ ) T(world\\\\)", "\\\\Hello\\\\ world\\\\");
	}

	@Test
	public void testListItem() {

		assertTokens("LI(  *) T( foo)", "  * foo");
		assertTokens("LI(  *) T(foo)", "  *foo");
		assertTokens("LI(   -) T( foo)", "   - foo");
		assertTokens("T(foo) LI(  *) T(bar)", "foo  *bar");
	}

	@Test
	public void testMonospace() {

		assertTokens("T(foo) MS('') T(bar)", "foo''bar");
		assertTokens("T(foo) MS('') T( bar)", "foo'' bar");
		assertTokens("T(foo ) MS('') T(bar)", "foo ''bar");
	}

	@Test
	public void testParagraph() {

		assertTokens("T(foo\nbar)", "foo\nbar");
		assertTokens("T(foo) P(\n\n) T(bar)", "foo\n\nbar");
		assertTokens("T(foo) P(\n\n\n) T(bar)", "foo\n\n\nbar");
	}

	@Test
	public void testRawText() {

		assertTokens("TAG(<pre>) T(foo <p> bar\nbaz) TAG(</pre>)", "<pre>foo <p> bar\nbaz</pre>");
		assertTokens("TAG(<code>) T(foo **bar** baz) TAG(</code>)", "<code>foo **bar** baz</code>");
		assertTokens("T(foo) TAG(<code>) T(bar) TAG(</code>) B(**) T(foo) B(**)", "foo<code>bar</code>**foo**");
	}

	@Test
	public void testResource() {

		assertTokens("RSC({{foo}})", "{{foo}}");
		assertTokens("RSC({{foo|bar}})", "{{foo|bar}}");
	}

	@Test
	public void testTableRow() {

		// test matches
		assertTokens("TR(^ foo ^ bar ^)", "^ foo ^ bar ^");
		assertTokens("TR(| foo ^ bar |)", "| foo ^ bar |");

		// test non-matches
		assertTokens("T(foo ^ bar ^)", "foo ^ bar ^");
		assertTokens("T(foo | bar |)", "foo | bar |");
	}

	@Test
	public void testTag() {

		// test matches
		assertTokens("T(foo) TAG(<info>)", "foo<info>");
		assertTokens("T(foo) TAG(<pre bar>)", "foo<pre bar>");
		assertTokens("T(foo) TAG(</code>)", "foo</code>");

		// test non-matches
		assertTokens("T(foo<>bar)", "foo<>bar");
		assertTokens("T(foo < bar)", "foo < bar");
		assertTokens("T(foo < bar > baz)", "foo < bar > baz");
	}

	@Test
	public void testUnderline() {

		assertTokens("T(foo) UL(__) T(bar)", "foo__bar");
		assertTokens("T(foo) UL(__) T( bar)", "foo__ bar");
		assertTokens("T(foo ) UL(__) T(bar)", "foo __bar");
	}

	@Test
	public void testUrl() {

		assertTokens("URL([[foo]])", "[[foo]]");
		assertTokens("URL([[foo|bar]])", "[[foo|bar]]");
	}

	private void assertTokens(String expected, String wikiText) {

		assertTokens(expected, new WikiTokenizer(wikiText).tokenize());
	}

	private void assertTokens(String expected, Collection<WikiToken> tokens) {

		String actual = tokens//
			.stream()
			.map(this::getTokenAsText)
			.collect(Collectors.joining(" "));
		expected = expected.replace("\n", "\\n");
		actual = actual.replace("\n", "\\n");
		assertEquals(expected, actual);
	}

	private String getTokenAsText(WikiToken token) {

		return new StringBuilder()//
			.append(getAbbreviation(token.getTokenType()))
			.append("(")
			.append(token.getConsumedText())
			.append(")")
			.toString();
	}

	private String getAbbreviation(WikiTokenType tokenType) {

		switch (tokenType) {
		case BOLD:
			return "B";
		case HEADLINE:
			return "H";
		case ITALIC:
			return "I";
		case LINE_BREAK:
			return "LB";
		case LIST_ITEM:
			return "LI";
		case MONOSPACE:
			return "MS";
		case PARAGRAPH:
			return "P";
		case RESOURCE:
			return "RSC";
		case TABLE_ROW:
			return "TR";
		case TAG:
			return "TAG";
		case TEXT:
			return "T";
		case UNDERLINE:
			return "UL";
		case URL:
			return "URL";
		}
		throw new SofticarUnknownEnumConstantException(tokenType);
	}
}

package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.util.Collection;
import java.util.stream.Collectors;
import org.junit.Test;

public class EmfEnumTableRowInputEngineTest extends AbstractDbTest {

	private final EmfEnumTableRowInputEngine<EmfTestEnumRow> inputEngine;

	public EmfEnumTableRowInputEngineTest() {

		this.inputEngine = new EmfEnumTableRowInputEngine<>(EmfTestEnumRow.TABLE);
	}

	@Test
	public void testWithEmptyPattern() {

		assertEquals("[]", toDisplayStrings(inputEngine.findMatches("", 0)));
		assertEquals("[Five]", toDisplayStrings(inputEngine.findMatches("", 1)));
		assertEquals("[Five, Four, One]", toDisplayStrings(inputEngine.findMatches("", 3)));
		assertEquals("[Five, Four, One, Three, Two]", toDisplayStrings(inputEngine.findMatches("", 5)));
		assertEquals("[Five, Four, One, Three, Two]", toDisplayStrings(inputEngine.findMatches("", 10)));
	}

	@Test
	public void testWithOneLetterPattern() {

		assertEquals("[]", toDisplayStrings(inputEngine.findMatches("e", 0)));
		assertEquals("[Five]", toDisplayStrings(inputEngine.findMatches("e", 1)));
		assertEquals("[Five, One]", toDisplayStrings(inputEngine.findMatches("e", 2)));
		assertEquals("[Five, One, Three]", toDisplayStrings(inputEngine.findMatches("e", 3)));
		assertEquals("[Five, One, Three]", toDisplayStrings(inputEngine.findMatches("e", 4)));
	}

	@Test
	public void testWithTwoLetterPattern() {

		assertEquals("[]", toDisplayStrings(inputEngine.findMatches("iv", 0)));
		assertEquals("[Five]", toDisplayStrings(inputEngine.findMatches("iv", 1)));
		assertEquals("[Five]", toDisplayStrings(inputEngine.findMatches("iv", 2)));
	}

	@Test
	public void testWithOneLetterPatternAndTranslation() {

		CurrentLocale.set(new Locale().setLanguage(LanguageEnum.GERMAN));

		assertEquals("[]", toDisplayStrings(inputEngine.findMatches("e", 0)));
		assertEquals("[Drei]", toDisplayStrings(inputEngine.findMatches("e", 1)));
		assertEquals("[Drei, Eins]", toDisplayStrings(inputEngine.findMatches("e", 2)));
		assertEquals("[Drei, Eins, Vier]", toDisplayStrings(inputEngine.findMatches("e", 3)));
		assertEquals("[Drei, Eins, Vier, Zwei]", toDisplayStrings(inputEngine.findMatches("e", 4)));
		assertEquals("[Drei, Eins, Vier, Zwei]", toDisplayStrings(inputEngine.findMatches("e", 5)));
	}

	@Test
	public void testWithTwoLetterPatternAndTranslation() {

		CurrentLocale.set(new Locale().setLanguage(LanguageEnum.GERMAN));

		assertEquals("[]", toDisplayStrings(inputEngine.findMatches("ei", 0)));
		assertEquals("[Drei]", toDisplayStrings(inputEngine.findMatches("ei", 1)));
		assertEquals("[Drei, Eins]", toDisplayStrings(inputEngine.findMatches("ei", 2)));
		assertEquals("[Drei, Eins, Zwei]", toDisplayStrings(inputEngine.findMatches("ei", 3)));
		assertEquals("[Drei, Eins, Zwei]", toDisplayStrings(inputEngine.findMatches("ei", 4)));
	}

	private String toDisplayStrings(Collection<EmfTestEnumRow> matches) {

		return matches//
			.stream()
			.map(inputEngine::getDisplayString)
			.collect(Collectors.toList())
			.toString();
	}
}

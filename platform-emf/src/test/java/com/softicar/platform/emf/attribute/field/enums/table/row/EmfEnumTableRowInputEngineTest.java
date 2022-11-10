package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.dom.elements.input.auto.matching.AutoCompleteMatch;
import com.softicar.platform.dom.elements.input.auto.matching.IAutoCompleteMatches;
import java.util.stream.Collectors;
import org.junit.Test;

public class EmfEnumTableRowInputEngineTest extends AbstractDbTest {

	private final EmfEnumTableRowInputEngine<EmfTestEnumRow> inputEngine;

	public EmfEnumTableRowInputEngineTest() {

		this.inputEngine = new EmfEnumTableRowInputEngine<>(EmfTestEnumRow.TABLE);
	}

	@Test
	public void testWithEmptyPattern() {

		assertEquals("[Five]", toDisplayStrings(inputEngine.findMatches("", 1)));
		assertEquals("[Five, Four, One]", toDisplayStrings(inputEngine.findMatches("", 3)));
		assertEquals("[Five, Four, One, Three, Two]", toDisplayStrings(inputEngine.findMatches("", 5)));
		assertEquals("[Five, Four, One, Three, Two]", toDisplayStrings(inputEngine.findMatches("", 10)));
	}

	@Test
	public void testWithOneLetterPattern() {

		assertEquals("[Five]", toDisplayStrings(inputEngine.findMatches("e", 1)));
		assertEquals("[Five, One]", toDisplayStrings(inputEngine.findMatches("e", 2)));
		assertEquals("[Five, One, Three]", toDisplayStrings(inputEngine.findMatches("e", 3)));
		assertEquals("[Five, One, Three]", toDisplayStrings(inputEngine.findMatches("e", 4)));
	}

	@Test
	public void testWithTwoLetterPattern() {

		assertEquals("[Five]", toDisplayStrings(inputEngine.findMatches("iv", 1)));
		assertEquals("[Five]", toDisplayStrings(inputEngine.findMatches("iv", 2)));
	}

	@Test
	public void testWithOneLetterPatternAndTranslation() {

		CurrentLocale.set(new Locale().setLanguage(LanguageEnum.GERMAN));

		assertEquals("[Eins]", toDisplayStrings(inputEngine.findMatches("e", 1)));
		assertEquals("[Eins, Drei]", toDisplayStrings(inputEngine.findMatches("e", 2)));
		assertEquals("[Eins, Drei, Vier]", toDisplayStrings(inputEngine.findMatches("e", 3)));
		assertEquals("[Eins, Drei, Vier, Zwei]", toDisplayStrings(inputEngine.findMatches("e", 4)));
		assertEquals("[Eins, Drei, Vier, Zwei]", toDisplayStrings(inputEngine.findMatches("e", 5)));
	}

	@Test
	public void testWithTwoLetterPatternAndTranslation() {

		CurrentLocale.set(new Locale().setLanguage(LanguageEnum.GERMAN));

		assertEquals("[Eins]", toDisplayStrings(inputEngine.findMatches("ei", 1)));
		assertEquals("[Eins, Drei]", toDisplayStrings(inputEngine.findMatches("ei", 2)));
		assertEquals("[Eins, Drei, Zwei]", toDisplayStrings(inputEngine.findMatches("ei", 3)));
		assertEquals("[Eins, Drei, Zwei]", toDisplayStrings(inputEngine.findMatches("ei", 4)));
	}

	private String toDisplayStrings(IAutoCompleteMatches<EmfTestEnumRow> matches) {

		return matches//
			.getAll()
			.stream()
			.map(AutoCompleteMatch::getValue)
			.map(inputEngine::getDisplayString)
			.collect(Collectors.toList())
			.toString();
	}
}

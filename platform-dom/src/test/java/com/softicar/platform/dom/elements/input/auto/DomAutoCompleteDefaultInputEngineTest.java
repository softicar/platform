package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.Locale;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.input.auto.matching.DomAutoCompleteMatch;
import com.softicar.platform.dom.elements.input.auto.matching.IDomAutoCompleteMatches;
import java.util.stream.Collectors;
import org.junit.Test;

public class DomAutoCompleteDefaultInputEngineTest extends AbstractDomAutoCompleteDefaultInputEngineTest {

	private final DomAutoCompleteDefaultInputEngine<TestValue> inputEngine;

	public DomAutoCompleteDefaultInputEngineTest() {

		this.inputEngine = new DomAutoCompleteDefaultInputEngine<>();
		inputEngine.setLoader(() -> values);

		addTestValue(DomI18n.ONE, 1);
		addTestValue(DomI18n.TWO, 2);
		addTestValue(DomI18n.THREE, 3);
		addTestValue(DomI18n.FOUR, 4);
		addTestValue(DomI18n.FIVE, 5);
	}

	// ------------------------------ unique display strings ------------------------------ //

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

	// ------------------------------ unique display strings with translation ------------------------------ //

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

	// ------------------------------ redundant display strings ------------------------------ //

	@Test
	public void testWithRedundantDisplayStrings() {

		addTestValue("four", 44);
		addTestValue("five", 55);

		assertEquals("[Five (1), five (2), Four (1), four (2)]", toDisplayStrings(inputEngine.findMatches("f", 9)));
		assertEquals("[Five (1), five (2)]", toDisplayStrings(inputEngine.findMatches("fi", 9)));
		assertEquals("[Five (1), five (2)]", toDisplayStrings(inputEngine.findMatches("five", 9)));
		assertEquals("[Five (1)]", toDisplayStrings(inputEngine.findMatches("five (1", 9)));
		assertEquals("[Five (1)]", toDisplayStrings(inputEngine.findMatches("five (1)", 9)));
	}

	@Test
	public void testWithRedundantSubStrings() {

		addTestValue("FourA", 6);
		addTestValue("AFour", 7);

		assertEquals("[Five, Four, FourA, AFour]", toDisplayStrings(inputEngine.findMatches("f", 9)));
		assertEquals("[Four, FourA, AFour]", toDisplayStrings(inputEngine.findMatches("fo", 9)));
		assertEquals("[Four, FourA, AFour]", toDisplayStrings(inputEngine.findMatches("four", 9)));
	}

	// ------------------------------ with custom display function ------------------------------ //

	@Test
	public void testWithRedundantDisplayStringsAndCustomDisplayFunction() {

		inputEngine.setDisplayFunction(value -> IDisplayString.create("%s [%s]".formatted(value.toDisplay(), value.getValue())));

		addTestValue("four", 44);
		addTestValue("five", 55);

		assertEquals("[five [55], Five [5], four [44], Four [4]]", toDisplayStrings(inputEngine.findMatches("f", 9)));
		assertEquals("[five [55], Five [5]]", toDisplayStrings(inputEngine.findMatches("fi", 9)));
		assertEquals("[five [55], Five [5]]", toDisplayStrings(inputEngine.findMatches("five", 9)));
		assertEquals("[five [55], Five [5]]", toDisplayStrings(inputEngine.findMatches("five [5", 9)));
		assertEquals("[Five [5]]", toDisplayStrings(inputEngine.findMatches("five [5]", 9)));
	}

	// ------------------------------ with multiple patterns ------------------------------ //

	@Test
	public void testWithMultiplePatterns() {

		clearTestValues();
		addTestValue("My Test Item", 11);
		addTestValue("My Test Value", 22);
		addTestValue("Some Test Value", 33);
		addTestValue("Some Other Value", 44);

		assertEquals("[Some Other Value, Some Test Value]", toDisplayStrings(inputEngine.findMatches("ome lue", 9)));
		assertEquals("[My Test Item, My Test Value, Some Other Value, Some Test Value]", toDisplayStrings(inputEngine.findMatches("t", 9)));
		assertEquals("[My Test Item, My Test Value, Some Test Value]", toDisplayStrings(inputEngine.findMatches("te t", 9)));
	}

	// ------------------------------ with diactritics ------------------------------ //

	@Test
	public void testWithDiacritics() {

		clearTestValues();
		addTestValue("foo", 11);
		addTestValue("bar", 22);
		addTestValue("bar", 33);
		addTestValue("bâr", 44);
		addTestValue("bár", 55);

		assertEquals("[bar (1), bar (2), bâr (3), bár (4)]", toDisplayStrings(inputEngine.findMatches("a", 9)));
	}

	// ------------------------------ with id match ------------------------------ //

	@Test
	public void testWithIdMatch() {

		inputEngine.setDisplayFunction(value -> IDisplayString.create("%s [%s]".formatted(value.toDisplay(), value.getValue())));

		addTestValue("Eleven", 11);
		addTestValue("Twelve", 12);

		assertEquals("[One [1]]", toDisplayStrings(inputEngine.findMatches("1", 9)));
		assertEquals("[Two [2]]", toDisplayStrings(inputEngine.findMatches("2", 9)));
		assertEquals("[Eleven [11]]", toDisplayStrings(inputEngine.findMatches("11", 9)));
		assertEquals("[Twelve [12]]", toDisplayStrings(inputEngine.findMatches("12", 9)));

		assertEquals("[Eleven [11], One [1], Twelve [12]]", toDisplayStrings(inputEngine.findMatches("[1", 9)));
		assertEquals("[Eleven [11]]", toDisplayStrings(inputEngine.findMatches("[11", 9)));

		assertEquals("[Twelve [12], Two [2]]", toDisplayStrings(inputEngine.findMatches("2]", 9)));
		assertEquals("[Twelve [12]]", toDisplayStrings(inputEngine.findMatches("12]", 9)));
	}

	// ------------------------------ miscellaneous ------------------------------ //

	@Test
	public void testGetDisplayStringWithUnkownValue() {

		IDisplayString unknown = IDisplayString.create("UNKNOWN");

		assertEquals(unknown, inputEngine.getDisplayString(new TestValue(unknown, 999)));
	}

	// ------------------------------ private ------------------------------ //

	private String toDisplayStrings(IDomAutoCompleteMatches<TestValue> matches) {

		return matches//
			.getAll()
			.stream()
			.map(DomAutoCompleteMatch::getValue)
			.map(inputEngine::getDisplayString)
			.collect(Collectors.toList())
			.toString();
	}
}

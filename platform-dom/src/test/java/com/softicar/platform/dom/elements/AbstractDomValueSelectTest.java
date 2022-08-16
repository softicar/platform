package com.softicar.platform.dom.elements;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.container.pair.PairFactory;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.input.DomOption;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link AbstractDomValueSelect}.
 *
 * @author Oliver Richers
 */
public class AbstractDomValueSelectTest extends AbstractTest {

	private TestSelect select;
	private Pair<Integer, IDisplayString> value;
	private Pair<Integer, IDisplayString> equalValue;
	private Pair<Integer, IDisplayString> differentValue;

	@Before
	public void before() {

		CurrentDomDocument.set(new DomDocument());
		select = new TestSelect();
		value = PairFactory.create(1337, IDisplayString.create("I don't care"));
		equalValue = PairFactory.create(1337, IDisplayString.create("I do care"));
		differentValue = PairFactory.create(42, IDisplayString.create("I am different"));
	}

	@Test
	public void startsWithNoOptions() {

		assertEquals(0, select.getChildCount());
	}

	@Test
	public void addsOptionOnAddValue() {

		select.addValue(value);

		assertEquals(1, select.getChildCount());
		assertTrue(select.getChild(0) instanceof DomOption);
	}

	@Test
	public void addsValueOnAddValue() {

		select.addValue(value);

		assertEquals(1, select.getValueList().size());
		assertSame(value, select.getValue(0));
	}

	@Test
	public void updatesOptionWhenAddingSameValue() {

		select.addValue(value);
		select.addValue(equalValue);

		assertEquals(1, select.getChildCount());
	}

	@Test
	public void updatesValueWhenAddingSameValue() {

		select.addValue(value);
		select.addValue(equalValue);

		assertSame(equalValue, select.getValue(0));
		assertSame(equalValue, select.getValueList().get(0));
		assertEquals(1, select.getValueList().size());
	}

	@Test
	public void containsValueReturnsFalseWhenMissing() {

		assertFalse(select.containsValue(value));
		assertFalse(select.containsValue(equalValue));
	}

	@Test
	public void containsValueReturnsTrueForSameID() {

		select.addValue(value);

		assertTrue(select.containsValue(value));
		assertTrue(select.containsValue(equalValue));
	}

	@Test
	public void containsValueReturnsFalseForDifferentID() {

		select.addValue(value);

		assertFalse(select.containsValue(differentValue));
	}

	@Test
	public void getVisualValueList() {

		select.addValue(PairFactory.create(11, IDisplayString.create("ccc")));
		select.addValue(PairFactory.create(33, IDisplayString.create("aaa")));
		select.addNilValue(IDisplayString.create("nnn"));
		select.addValue(PairFactory.create(22, IDisplayString.create("bbb")));
		assertDisplayNames(select.getVisualValueList(), "ccc", "aaa", null, "bbb");

		select.sortValuesByDisplayString();
		assertDisplayNames(select.getVisualValueList(), null, "aaa", "bbb", "ccc");

		select.sortValuesByDisplayString(OrderDirection.DESCENDING);
		assertDisplayNames(select.getVisualValueList(), null, "ccc", "bbb", "aaa");
	}

	private void assertDisplayNames(List<Pair<Integer, IDisplayString>> values, String...displayNames) {

		assertEquals(displayNames.length, values.size());

		for (int i = 0; i < displayNames.length; ++i) {
			if (displayNames[i] != null) {
				IDisplayString expected = IDisplayString.create(displayNames[i]);
				IDisplayString actual = values.get(i).getSecond();
				assertTrue(expected.toString().compareTo(actual.toString()) == 0);
			} else {
				assertNull(values.get(i));
			}
		}
	}
}

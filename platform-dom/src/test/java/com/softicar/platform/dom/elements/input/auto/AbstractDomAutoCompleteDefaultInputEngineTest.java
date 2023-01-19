package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractDomAutoCompleteDefaultInputEngineTest extends AbstractTest {

	protected final Collection<TestValue> values;

	public AbstractDomAutoCompleteDefaultInputEngineTest() {

		this.values = new ArrayList<>();
	}

	protected void addTestValue(String string, int value) {

		addTestValue(IDisplayString.create(string), value);
	}

	protected void addTestValue(IDisplayString displayString, int value) {

		values.add(new TestValue(displayString, value));
	}

	protected void clearTestValues() {

		values.clear();
	}

	protected void assertMapInArbitraryOrder(Map<String, TestValue> map, String...expectedEntries) {

		Set<String> actualEntries = map.entrySet().stream().map(Entry::toString).collect(Collectors.toSet());
		Arrays.asList(expectedEntries).forEach(expectedEntry -> {
			assertTrue(//
				"Entry '%s' was not found in: '%s'".formatted(expectedEntry, actualEntries),
				actualEntries.contains(expectedEntry));
		});
		assertEquals(expectedEntries.length, map.size());
	}

	protected static class TestValue implements IDisplayable, Comparable<TestValue> {

		private final IDisplayString displayString;
		private final int value;

		public TestValue(IDisplayString displayString, int value) {

			this.displayString = displayString;
			this.value = value;
		}

		public int getValue() {

			return value;
		}

		@Override
		public IDisplayString toDisplay() {

			return displayString;
		}

		@Override
		public int compareTo(TestValue other) {

			return Integer.compare(value, other.value);
		}

		@Override
		public String toString() {

			return "" + value;
		}
	}
}

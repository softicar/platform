package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AbstractDomAutoCompleteDefaultInputEngineTest extends AbstractTest {

	protected final Collection<TestElement> elements;

	public AbstractDomAutoCompleteDefaultInputEngineTest() {

		this.elements = new ArrayList<>();
	}

	protected void addTestElement(String string, int value) {

		addTestElement(IDisplayString.create(string), value);
	}

	protected void addTestElement(IDisplayString displayString, int value) {

		elements.add(new TestElement(displayString, value));
	}

	protected void assertMap(String expected, Map<String, TestElement> map) {

		var exptectedToString = List//
			.of(expected.split("\n"))
			.stream()
			.collect(Collectors.joining(", "));
		assertEquals("{" + exptectedToString + "}", map.toString());
	}

	protected static class TestElement implements IDisplayable, Comparable<TestElement> {

		private final IDisplayString displayString;
		private final int value;

		public TestElement(IDisplayString displayString, int value) {

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
		public int compareTo(TestElement other) {

			return Integer.compare(value, other.value);
		}

		@Override
		public String toString() {

			return "" + value;
		}
	}
}

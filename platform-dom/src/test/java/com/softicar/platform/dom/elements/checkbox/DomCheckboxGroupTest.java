package com.softicar.platform.dom.elements.checkbox;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.Rule;
import org.junit.Test;

public class DomCheckboxGroupTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	private static final IDisplayString A = IDisplayString.create("A");
	private static final IDisplayString B = IDisplayString.create("B");
	private static final IDisplayString C = IDisplayString.create("C");

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final DomDiv node;

	public DomCheckboxGroupTest() {

		this.node = setNode(new DomDiv());
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testAddOption() {

		var group = initializeGroup();

		group.addOption(new Option(10), A);
		group.addOption(new Option(20), B, true);
		group.addOption(new Option(30), C);

		new Asserter<>(group)//
			.assertAvailableOptions(3)
			.assertAvailableOptionLabels("A", "B", "C")
			.assertSelectedOption(new Option(20))
			.assertSelectedOptionNumber(2);
	}

	@Test
	public void testAddDisplayableOption() {

		var group = initializeDisplayableGroup();

		group.addOption(new DisplayableOption(10, A));
		group.addOption(new DisplayableOption(20, B), true);
		group.addOption(new DisplayableOption(30, C));

		new Asserter<>(group)//
			.assertAvailableOptions(3)
			.assertAvailableOptionLabels("A", "B", "C")
			.assertSelectedOption(new DisplayableOption(20, B))
			.assertSelectedOptionNumber(2);
	}

	@Test
	public void testGetValueWithMultipleOptions() {

		var group = initializeSimpleGroup();

		var option = group.getValue().orElseThrow();

		assertEquals(10, option.getInteger());
		new Asserter<>(group).assertSelectedOptionNumber(1);
	}

	@Test
	public void testGetValueWithSingleOption() {

		var group = initializeGroup();
		group.addOption(new Option(10), A);

		var option = group.getValue().orElseThrow();

		assertEquals(10, option.getInteger());
		new Asserter<>(group).assertSelectedOptionNumber(1);
	}

	@Test
	public void testGetValueWithoutOptions() {

		var group = initializeGroup();

		var option = group.getValue();

		assertTrue(option.isEmpty());
	}

	@Test
	public void testSetValue() {

		var group = initializeSimpleGroup();

		group.setValue(new Option(30));
		group.setValue(new Option(20));

		new Asserter<>(group)//
			.assertSelectedOption(new Option(20))
			.assertSelectedOptionNumber(2);
	}

	@Test
	public void testSetValueWithUnknownOption() {

		var group = initializeSimpleGroup();

		group.setValue(new Option(20));
		group.setValue(new Option(99));

		new Asserter<>(group)//
			.assertSelectedOption(new Option(20))
			.assertSelectedOptionNumber(2);
	}

	@Test
	public void testSetValueWithNull() {

		var group = initializeSimpleGroup();

		group.setValue(new Option(20));
		group.setValue(null);

		new Asserter<>(group)//
			.assertSelectedOption(new Option(20))
			.assertSelectedOptionNumber(2);
	}

	@Test
	public void testSetValueWithCallback() {

		var callback = new Callback();
		var group = initializeSimpleGroup();
		group.addChangeCallback(callback);

		group.setValue(new Option(30));
		group.setValue(new Option(20));
		group.setValue(new Option(10));
		group.setValue(new Option(20));

		assertEquals(4, callback.getCallCount());
	}

	private DomCheckboxGroup<Option> initializeGroup() {

		return node.appendChild(new DomCheckboxGroup<>());
	}

	private DomCheckboxGroup<Option> initializeSimpleGroup() {

		return initializeGroup()//
			.addOption(new Option(10), A)
			.addOption(new Option(20), B)
			.addOption(new Option(30), C);
	}

	private DomCheckboxGroup<DisplayableOption> initializeDisplayableGroup() {

		return node.appendChild(new DomCheckboxGroup<>());
	}

	private static class Option {

		private final Integer integer;

		public Option(Integer integer) {

			this.integer = integer;
		}

		@Override
		public String toString() {

			return integer + "";
		}

		@Override
		public boolean equals(Object other) {

			if (other instanceof Option) {
				var otherOption = (Option) other;
				return integer.equals(otherOption.integer);
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {

			return Objects.hash(integer);
		}

		public Integer getInteger() {

			return integer;
		}
	}

	private static class DisplayableOption implements IDisplayable {

		private final Integer integer;
		private final IDisplayString label;

		public DisplayableOption(Integer integer, IDisplayString label) {

			this.integer = integer;
			this.label = label;
		}

		@Override
		public IDisplayString toDisplay() {

			return label;
		}

		@Override
		public boolean equals(Object other) {

			if (other instanceof DisplayableOption) {
				var otherOption = (DisplayableOption) other;
				return integer.equals(otherOption.integer) && label.equals(otherOption.label);
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {

			return Objects.hash(integer, label);
		}
	}

	private class Callback implements INullaryVoidFunction {

		private int callCount = 0;

		@Override
		public void apply() {

			++callCount;
		}

		public int getCallCount() {

			return callCount;
		}
	}

	private class Asserter<V> {

		private final DomCheckboxGroup<V> group;

		public Asserter(DomCheckboxGroup<V> group) {

			this.group = group;
		}

		public Asserter<V> assertAvailableOptions(int size) {

			assertEquals(size, findCheckboxes().size());
			return this;
		}

		public Asserter<V> assertAvailableOptionLabels(String...labels) {

			List<String> actualLabels = asTester(group).getAllTextsInTree().collect(Collectors.toList());

			assertEquals(labels.length, actualLabels.size());
			for (int i = 0; i < labels.length; i++) {
				assertEquals(labels[i], actualLabels.get(i));
			}

			return this;
		}

		public Asserter<V> assertSelectedOption(V value) {

			assertEquals(value, group.getValueOrThrow());
			return this;
		}

		public Asserter<V> assertSelectedOptionNumber(int number) {

			var checkboxes = findCheckboxes();
			assertTrue(number <= checkboxes.size());

			for (int i = 0; i < checkboxes.size(); i++) {
				var checkbox = checkboxes.get(i);
				if (i + 1 == number) {
					assertTrue("Expected the checkbox number %s to be checked but it was unchecked.".formatted(number), checkbox.isChecked());
				} else {
					assertFalse("Expected the checkbox number %s to be unchecked but it was checked.".formatted(number), checkbox.isChecked());
				}
			}

			return this;
		}

		private List<DomCheckbox> findCheckboxes() {

			return asTester(group).findNodes(DomCheckbox.class).toList(Function.identity());
		}
	}
}

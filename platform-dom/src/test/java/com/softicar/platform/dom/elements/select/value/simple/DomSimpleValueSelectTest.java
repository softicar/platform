package com.softicar.platform.dom.elements.select.value.simple;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.elements.testing.node.tester.DomSelectTester;
import com.softicar.platform.dom.input.DomOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.junit.Rule;
import org.junit.Test;

public class DomSimpleValueSelectTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	private static final String NIL = "[none]";

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final CallbackCounter callbackCounter;
	private final Asserter asserter;

	public DomSimpleValueSelectTest() {

		this.callbackCounter = new CallbackCounter();
		this.asserter = new Asserter(engine, this::findSelect, () -> callbackCounter);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	// -------------------------------- setValues -------------------------------- //

	@Test
	public void testSetValuesWithSeveralValuesOfString() {

		var select = setupWithString()//
			.addCallback()
			.getSelect();

		select.setValues(Arrays.asList("foo", "bar", "foo", ""));

		asserter//
			.assertOptions("", "bar", "foo")
			.assertNilSelection()
			.assertNoCallbacks();
	}

	@Test
	public void testSetValuesWithSeveralValuesOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.getSelect();

		select.setValues(Arrays.asList(Entity.foo(), Entity.bar(), Entity.foo()));

		asserter//
			.assertOptions("bar [2]", "foo [1]")
			.assertNilSelection()
			.assertNoCallbacks();
	}

	@Test
	public void testSetValuesWithPreviousSelectionOfString() {

		var select = setupWithString()//
			.addCallback()
			.setValues("foo")
			.getSelect();
		select.selectValue("foo");

		select.setValues(Arrays.asList("foo", "bar", "baz"));

		asserter//
			.assertOptions("bar", "baz", "foo")
			.assertNilSelection()
			.assertCallbacks(2);
	}

	@Test
	public void testSetValuesWithPreviousSelectionOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.setValues(Entity.foo())
			.getSelect();
		select.selectValue(Entity.foo());

		select.setValues(Arrays.asList(Entity.foo(), Entity.bar(), Entity.baz()));

		asserter//
			.assertOptions("bar [2]", "baz [3]", "foo [1]")
			.assertNilSelection()
			.assertCallbacks(2);
	}

	@Test
	public void testSetValuesWithPreviousSelectionAndWithoutNilOptionOfString() {

		var select = setupWithString(config -> config.setNilOptionAvailable(false))//
			.addCallback()
			.setValues("foo")
			.getSelect();
		select.selectValue("foo");

		select.setValues(Arrays.asList("baz", "bar"));

		asserter//
			.assertOptionsWithoutDefaultNil("bar", "baz")
			.assertSelection("bar")
			.assertCallbacks(2);
	}

	@Test
	public void testSetValuesWithPreviousSelectionAndWithoutNilOptionOfEntity() {

		var select = setupWithEntity(config -> config.setNilOptionAvailable(false))//
			.addCallback()
			.setValues(Entity.foo())
			.getSelect();
		select.selectValue(Entity.foo());

		select.setValues(Arrays.asList(Entity.baz(), Entity.bar()));

		asserter//
			.assertOptionsWithoutDefaultNil("bar [2]", "baz [3]")
			.assertSelection("bar [2]")
			.assertCallbacks(2);
	}

	@Test
	public void testSetValuesWithEmptyCollectionOfString() {

		var select = setupWithString()//
			.addCallback()
			.getSelect();

		select.setValues(Collections.emptySet());

		asserter//
			.assertOnlyDefaultNilOption()
			.assertNilSelection()
			.assertNoCallbacks();
	}

	@Test
	public void testSetValuesWithEmptyCollectionOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.getSelect();

		select.setValues(Collections.emptySet());

		asserter//
			.assertOnlyDefaultNilOption()
			.assertNilSelection()
			.assertNoCallbacks();
	}

	@Test
	public void testSetValuesWithNullCollectionEntry() {

		var select = setupWithString().getSelect();

		select.setValues(Arrays.asList("foo", null));

		asserter//
			.assertOptions("foo")
			.assertNilSelection()
			.assertNoCallbacks();
	}

	@Test(expected = NullPointerException.class)
	public void testSetValuesWithNullCollection() {

		var select = setupWithString().getSelect();

		select.setValues(null);
	}

	// -------------------------------- getSelectedValue -------------------------------- //

	@Test
	public void testGetSelectedValueOfString() {

		var select = setupWithString()//
			.addCallback()
			.setValues("foo", "bar")
			.getSelect();
		select.selectValue("foo");

		assertEquals("foo", select.getSelectedValue().get());
	}

	@Test
	public void testGetSelectedValueOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.setValues(Entity.foo(), Entity.bar())
			.getSelect();
		select.selectValue(Entity.foo());

		assertEquals("foo [1]", select.getSelectedValue().get().toDisplay().toString());
	}

	@Test
	public void testGetSelectedValueWithoutSelectionOfString() {

		var select = setupWithString()//
			.addCallback()
			.setValues("foo", "bar")
			.getSelect();

		assertTrue(select.getSelectedValue().isEmpty());
	}

	@Test
	public void testGetSelectedValueWithoutSelectionOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.setValues(Entity.foo(), Entity.bar())
			.getSelect();

		assertTrue(select.getSelectedValue().isEmpty());
	}

	@Test
	public void testGetSelectedValueAfterNilOptionSelectionOfString() {

		var select = setupWithString()//
			.addCallback()
			.setValues("foo", "bar")
			.getSelect();
		select.selectValue("foo");
		select.selectValue(null);

		assertTrue(select.getSelectedValue().isEmpty());
	}

	@Test
	public void testGetSelectedValueAfterNilOptionSelectionOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.setValues(Entity.foo(), Entity.bar())
			.getSelect();
		select.selectValue(Entity.foo());
		select.selectValue(null);

		assertTrue(select.getSelectedValue().isEmpty());
	}

	// -------------------------------- selectValue -------------------------------- //

	@Test
	public void testSelectValueOfString() {

		var select = setupWithString()//
			.addCallback()
			.setValues("foo", "bar")
			.getSelect();

		select.selectValue("foo");

		asserter//
			.assertOptions("bar", "foo")
			.assertSelection("foo")
			.assertCallbacks(1);
	}

	@Test
	public void testSelectValueOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.setValues(Entity.foo(), Entity.bar())
			.getSelect();

		select.selectValue(Entity.foo());

		asserter//
			.assertOptions("bar [2]", "foo [1]")
			.assertSelection("foo [1]")
			.assertCallbacks(1);
	}

	@Test
	public void testSelectValueWithPreviousSelectionOfString() {

		var select = setupWithString()//
			.addCallback()
			.setValues("foo")
			.getSelect();
		select.selectValue("foo");

		select.selectValue("foo");

		asserter//
			.assertOptions("foo")
			.assertSelection("foo")
			.assertCallbacks(1);
	}

	@Test
	public void testSelectValueWithPreviousSelectionOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.setValues(Entity.foo())
			.getSelect();
		select.selectValue(Entity.foo());

		select.selectValue(Entity.foo());

		asserter//
			.assertOptions("foo [1]")
			.assertSelection("foo [1]")
			.assertCallbacks(1);
	}

	@Test
	public void testSelectValueWithoutCallbackOfString() {

		var select = setupWithString()//
			.setValues("foo", "bar")
			.getSelect();

		select.selectValue("foo");

		asserter//
			.assertOptions("bar", "foo")
			.assertSelection("foo")
			.assertNoCallbacks();
	}

	@Test
	public void testSelectValueWithoutCallbackOfEntity() {

		var select = setupWithEntity()//
			.setValues(Entity.foo(), Entity.bar())
			.getSelect();

		select.selectValue(Entity.foo());

		asserter//
			.assertOptions("bar [2]", "foo [1]")
			.assertSelection("foo [1]")
			.assertNoCallbacks();
	}

	@Test
	public void testSelectValueWithUnknownValueOfString() {

		var select = setupWithString()//
			.addCallback()
			.setValues("foo", "bar")
			.getSelect();

		select.selectValue("ccc");

		asserter//
			.assertOptions("bar", "ccc", "foo")
			.assertSelection("ccc")
			.assertCallbacks(1);
	}

	@Test
	public void testSelectValueWithUnknownValueOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.setValues(Entity.foo(), Entity.bar())
			.getSelect();

		select.selectValue(Entity.of(999, "ccc"));

		asserter//
			.assertOptions("bar [2]", "ccc [999]", "foo [1]")
			.assertSelection("ccc [999]")
			.assertCallbacks(1);
	}

	@Test
	public void testSelectValueWithNullOfString() {

		var select = setupWithString()//
			.addCallback()
			.setValues("foo", "bar")
			.selectValue("foo")
			.getSelect();

		select.selectValue(null);

		asserter//
			.assertOptions("bar", "foo")
			.assertNilSelection()
			.assertCallbacks(2);
	}

	@Test
	public void testSelectValueWithNullOfEntity() {

		var select = setupWithEntity()//
			.addCallback()
			.setValues(Entity.foo(), Entity.bar())
			.selectValue(Entity.foo())
			.getSelect();

		select.selectValue(null);

		asserter//
			.assertOptions("bar [2]", "foo [1]")
			.assertNilSelection()
			.assertCallbacks(2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelectValueWithNullAndWithoutNilOption() {

		var select = setupWithString(config -> config.setNilOptionAvailable(false))//
			.setValues("foo", "bar")
			.getSelect();

		select.selectValue(null);
	}

	// -------------------------------- addCallbackOnChange -------------------------------- //

	@Test
	public void testSetCallbackOnChange() {

		var select = setupWithString()//
			.setValues("foo")
			.getSelect();

		select.setCallbackOnChange(callbackCounter::call);
		select.selectValue("foo");

		asserter//
			.assertOptions("foo")
			.assertSelection("foo")
			.assertCallbacks(1);
	}

	@Test(expected = NullPointerException.class)
	public void testSetCallbackOnChangeWithNull() {

		var select = setupWithString()//
			.getSelect();

		select.setCallbackOnChange(null);
	}

	// -------------------------------- non-default configurations -------------------------------- //

	@Test
	public void testConfiguratorConstructorWithValues() {

		setupWithString(config -> config.setValues(Arrays.asList("foo", "bar", "baz"))).getSelect();

		asserter//
			.assertOptions("bar", "baz", "foo")
			.assertNilSelection()
			.assertNoCallbacks();
	}

	@Test
	public void testConfiguratorConstructorWithDisplayStringFunction() {

		setupWithString(config -> config.setValueDisplayStringFunction(value -> IDisplayString.create(value + "xxx")))//
			.setValues("foo", "bar")
			.getSelect();

		asserter//
			.assertOptions("barxxx", "fooxxx")
			.assertNilSelection()
			.assertNoCallbacks();
	}

	@Test
	public void testConfiguratorConstructorWithoutNilOption() {

		setupWithString(config -> config.setNilOptionAvailable(false))//
			.setValues("foo", "bar")
			.getSelect();

		asserter//
			.assertOptionsWithoutDefaultNil("bar", "foo")
			.assertSelection("bar")
			.assertNoCallbacks();
	}

	@Test
	public void testConfiguratorConstructorWithoutNilOptionAndWithoutValueOptions() {

		setupWithString(config -> config.setNilOptionAvailable(false)).getSelect();

		asserter//
			.assertNoOptions()
			.assertNoCallbacks();
	}

	@Test
	public void testConfiguratorConstructorWithNilOptionDisplayString() {

		setupWithString(config -> config.setNilOptionDisplayString(IDisplayString.create("I AM NIL")))//
			.setValues("foo", "bar")
			.getSelect();

		asserter//
			.assertOptionsWithoutDefaultNil("I AM NIL", "bar", "foo")
			.assertSelection("I AM NIL")
			.assertNoCallbacks();
	}

	@Test
	public void testConfiguratorConstructorWithValueComparator() {

		setupWithString(config -> config.setValueComparator(config.getValueComparator().reversed()))//
			.setValues("foo", "bar")
			.getSelect();

		asserter//
			.assertOptions("foo", "bar")
			.assertNilSelection()
			.assertNoCallbacks();
	}

	@Test
	public void testConfiguratorConstructorWithCallbacksOnChange() {

		var select = setupWithString(config -> config.setCallbackOnChange(callbackCounter::call))//
			.setValues("foo", "bar")
			.getSelect();
		select.selectValue("foo");

		asserter//
			.assertOptions("bar", "foo")
			.assertSelection("foo")
			.assertCallbacks(1);
	}

	// -------------------------------- edge cases -------------------------------- //

	@Test
	public void testWithDefaultConfiguration() {

		setupWithString();

		asserter//
			.assertOnlyDefaultNilOption()
			.assertNilSelection();
	}

	@Test(expected = NullPointerException.class)
	public void testWithNullConfiguration() {

		DevNull.swallow(new DomSimpleValueSelect<>((IDomSimpleValueSelectConfiguration<Object>) null));
	}

	@Test(expected = NullPointerException.class)
	public void testWithNullConfigurator() {

		DevNull.swallow(new DomSimpleValueSelect<>((Consumer<DomSimpleValueSelectBuilder<Object>>) null));
	}

	// -------------------------------- internal -------------------------------- //

	private <V> DomSelectTester<V> findSelect() {

		return findSelect(Marker.SELECT);
	}

	private Setup<String> setupWithString() {

		return setupWithString(null);
	}

	private Setup<String> setupWithString(Consumer<DomSimpleValueSelectBuilder<String>> configurator) {

		return new Setup<String>().initialize(configurator);
	}

	private Setup<Entity> setupWithEntity() {

		return setupWithEntity(null);
	}

	private Setup<Entity> setupWithEntity(Consumer<DomSimpleValueSelectBuilder<Entity>> configurator) {

		return new Setup<Entity>().initialize(configurator);
	}

	private class Setup<V> {

		private DomSimpleValueSelect<V> select;

		public Setup<V> initialize(Consumer<DomSimpleValueSelectBuilder<V>> configurator) {

			TestNode<V> testNode = new TestNode<>(configurator);
			setNodeSupplier(() -> testNode);
			this.select = testNode.getSelect();
			return this;
		}

		public Setup<V> addCallback() {

			select.setCallbackOnChange(callbackCounter::call);
			return this;
		}

		@SuppressWarnings("unchecked")
		public Setup<V> setValues(V...values) {

			select.setValues(Arrays.asList(values));
			return this;
		}

		public Setup<V> selectValue(V value) {

			select.selectValue(value);
			return this;
		}

		public DomSimpleValueSelect<V> getSelect() {

			return select;
		}
	}

	private class TestNode<V> extends DomDiv {

		private final DomSimpleValueSelect<V> select;

		public TestNode(Consumer<DomSimpleValueSelectBuilder<V>> configurator) {

			this.select = configurator != null? new DomSimpleValueSelect<>(configurator) : new DomSimpleValueSelect<>();
			appendChild(select).setMarker(Marker.SELECT);
		}

		public DomSimpleValueSelect<V> getSelect() {

			return select;
		}
	}

	private static class Asserter {

		private final IDomTestExecutionEngine engine;
		private final Supplier<DomSelectTester<?>> selectFinder;
		private final Supplier<CallbackCounter> callbackCounterSupplier;

		public Asserter(IDomTestExecutionEngine engine, Supplier<DomSelectTester<?>> selectFinder, Supplier<CallbackCounter> callbackCounterSupplier) {

			this.engine = engine;
			this.selectFinder = selectFinder;
			this.callbackCounterSupplier = callbackCounterSupplier;
		}

		public Asserter assertOnlyDefaultNilOption() {

			return assertOptions(true, new String[0]);
		}

		public Asserter assertNoOptions() {

			return assertOptions(false, new String[0]);
		}

		public Asserter assertOptions(String...expectedOptions) {

			return assertOptions(true, expectedOptions);
		}

		public Asserter assertOptionsWithoutDefaultNil(String...expectedOptions) {

			return assertOptions(false, expectedOptions);
		}

		public Asserter assertOptions(boolean expectDefaultNil, String...expectedOptions) {

			List<String> expectedOptionList = new ArrayList<>();
			if (expectDefaultNil) {
				expectedOptionList.add(NIL);
			}
			expectedOptionList.addAll(Arrays.asList(expectedOptions));

			String expected = Imploder.implode(expectedOptionList, ";");
			String actual = findOptions()//
				.stream()
				.map(it -> it.getAllTextsInTree().collect(Collectors.joining()))
				.collect(Collectors.joining(";"));
			assertEquals("Unexpected list of options.", expected, actual);

			return this;
		}

		public Asserter assertSelection(String expectedSelection) {

			DomOption option = selectFinder//
				.get()
				.getNode()
				.getSelectedOption();
			assertNotNull("Failed to determine selected option.", option);

			String selectedText = new DomNodeTester(engine, option).getAllTextsInTree().collect(Collectors.joining());
			boolean selected = selectedText.equals(expectedSelection);
			assertTrue(String.format("Expected selection of '%s' but '%s' was selected.", expectedSelection, selectedText), selected);

			return this;
		}

		public Asserter assertNilSelection() {

			return assertSelection(NIL);
		}

		public Asserter assertNoCallbacks() {

			return assertCallbacks(0);
		}

		public Asserter assertCallbacks(int expectedCount) {

			assertEquals(expectedCount, callbackCounterSupplier.get().getCount());
			return this;
		}

		private List<DomNodeTester> findOptions() {

			return selectFinder//
				.get()
				.findNodes(DomOption.class)
				.toList();
		}
	}

	private static class CallbackCounter {

		private int count;

		public CallbackCounter() {

			this.count = 0;
		}

		public void call() {

			this.count++;
		}

		public int getCount() {

			return count;
		}
	}

	private static class Entity implements IEntity {

		private final int id;
		private final String name;

		public static Entity foo() {

			return of(1, "foo");
		}

		public static Entity bar() {

			return of(2, "bar");
		}

		public static Entity baz() {

			return of(3, "baz");
		}

		public static Entity of(int id, String name) {

			return new Entity(id, name);
		}

		private Entity(int id, String name) {

			this.id = id;
			this.name = name;
		}

		@Override
		public Integer getId() {

			return id;
		}

		@Override
		public IDisplayString toDisplayWithoutId() {

			return IDisplayString.create(name);
		}

		@Override
		public int compareTo(IBasicItem other) {

			return Comparator//
				.comparing(Entity::getId)
				.compare(this, (Entity) other);
		}
	}

	private static enum Marker implements IStaticObject {

		SELECT;
	}
}

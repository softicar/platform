package com.softicar.platform.dom.elements.input.auto;

import java.util.Map;
import org.junit.Test;

public class DomAutoCompleteDisplayStringDeduplicatorTest extends AbstractDomAutoCompleteDefaultInputEngineTest {

	private final DomAutoCompleteDisplayStringDeduplicator<TestValue> deduplicator;

	public DomAutoCompleteDisplayStringDeduplicatorTest() {

		this.deduplicator = new DomAutoCompleteDisplayStringDeduplicator<>(TestValue::toDisplay, TestValue::compareTo);
	}

	@Test
	public void testWithDistinctDisplayStringsAndDistinctValues() {

		// different strings with different values
		addTestValue("FOO", 1);
		addTestValue("baz", 2);
		addTestValue("Bar", 3);

		Map<String, TestValue> map = deduplicator.apply(values);

		assertMapInArbitraryOrder(//
			map,
			"FOO=1",
			"baz=2",
			"Bar=3");
	}

	@Test
	public void testWithDistinctDisplayStringsAndRedundantValues() {

		// different strings but equal value
		addTestValue("FOO", 3);
		addTestValue("baz", 3);
		addTestValue("Bar", 3);

		Map<String, TestValue> map = deduplicator.apply(values);

		assertMapInArbitraryOrder(//
			map,
			"FOO=3",
			"baz=3",
			"Bar=3");
	}

	@Test
	public void testWithRedundantDisplayStringsAndDistinctValues() {

		// some entries
		addTestValue("FOO", 1);
		addTestValue("foo", 2);
		addTestValue("Foo", 3);
		addTestValue("fóô", 4);

		// further entries, with redundant strings but different values
		addTestValue("FOO", 9);
		addTestValue("foo", 8);
		addTestValue("Foo", 7);
		addTestValue("fóô", 6);

		Map<String, TestValue> map = deduplicator.apply(values);

		assertMapInArbitraryOrder(//
			map,
			"FOO (1)=1",
			"foo (2)=2",
			"Foo (3)=3",
			"fóô (4)=4",
			"fóô (5)=6",
			"Foo (6)=7",
			"foo (7)=8",
			"FOO (8)=9");
	}

	@Test
	public void testWithRedundantDisplayStringsAndRedundantValues() {

		// some entries
		addTestValue("FOO", 3);
		addTestValue("foo", 3);
		addTestValue("Foo", 3);
		addTestValue("fóô", 3);

		// further entries, with redundant strings and redundant values
		addTestValue("fóô", 3);
		addTestValue("Foo", 3);
		addTestValue("foo", 3);
		addTestValue("FOO", 3);

		Map<String, TestValue> map = deduplicator.apply(values);

		assertMapInArbitraryOrder(//
			map,
			"FOO (1)=3",
			"foo (2)=3",
			"Foo (3)=3",
			"fóô (4)=3",
			"fóô (5)=3",
			"Foo (6)=3",
			"foo (7)=3",
			"FOO (8)=3");
	}

	@Test
	public void testWithDeeplyRedundantDisplayStringsAndDistinctValues() {

		addTestValue("foo", 3);
		addTestValue("Foo", 2);
		addTestValue("fOO", 7);
		addTestValue("foo (1)", 4);
		addTestValue("foo (1)", 9);
		addTestValue("Foo (1)", 5);
		addTestValue("foo (3)", 6);
		addTestValue("foobar", 1);

		Map<String, TestValue> map = deduplicator.apply(values);

		assertMapInArbitraryOrder(//
			map,
			"foo (1) (1)=4",
			"Foo (1) (2)=5",
			"foo (1) (3)=9",
			"Foo (2)=2",
			"foo (3)=6",
			"foo (4)=3",
			"fOO (5)=7",
			"foobar=1");
	}
}

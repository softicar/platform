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

		addTestValue("FOO", 1);
		addTestValue("baz", 2);
		addTestValue("Bar", 3);

		Map<String, TestValue> map = deduplicator.apply(values);

		assertMap("""
				Bar=3
				baz=2
				FOO=1""", map);
	}

	@Test
	public void testWithDistinctDisplayStringsAndRedundantValues() {

		// different strings but equal value
		addTestValue("FOO", 3);
		addTestValue("baz", 3);
		addTestValue("Bar", 3);

		Map<String, TestValue> map = deduplicator.apply(values);

		assertMap("""
				Bar=3
				baz=3
				FOO=3""", map);
	}

	@Test
	public void testWithRedundantDisplayStringsAndDistinctValues() {

		// ascending values
		addTestValue("FOO", 1);
		addTestValue("foo", 2);
		addTestValue("Foo", 3);

		// descending values
		addTestValue("FOO", 9);
		addTestValue("foo", 8);
		addTestValue("Foo", 7);

		Map<String, TestValue> map = deduplicator.apply(values);

		// assert ordered by value
		assertMap("""
				FOO (1)=1
				foo (2)=2
				Foo (3)=3
				Foo (4)=7
				foo (5)=8
				FOO (6)=9""", map);
	}

	@Test
	public void testWithRedundantDisplayStringsAndRedundantValues() {

		// initial order of display strings
		addTestValue("FOO", 3);
		addTestValue("foo", 3);
		addTestValue("Foo", 3);

		// reverse order of display strings
		addTestValue("Foo", 3);
		addTestValue("foo", 3);
		addTestValue("FOO", 3);

		Map<String, TestValue> map = deduplicator.apply(values);

		// assert insertion order
		assertMap("""
				FOO (1)=3
				foo (2)=3
				Foo (3)=3
				Foo (4)=3
				foo (5)=3
				FOO (6)=3""", map);
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

		assertMap("""
				foo (1) (1)=4
				Foo (1) (2)=5
				foo (1) (3)=9
				Foo (2)=2
				foo (3)=6
				foo (4)=3
				fOO (5)=7
				foobar=1""", map);
	}
}

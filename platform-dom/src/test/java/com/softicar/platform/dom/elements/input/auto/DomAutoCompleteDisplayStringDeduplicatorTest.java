package com.softicar.platform.dom.elements.input.auto;

import java.util.Map;
import org.junit.Test;

public class DomAutoCompleteDisplayStringDeduplicatorTest extends AbstractDomAutoCompleteDefaultInputEngineTest {

	private final DomAutoCompleteDisplayStringDeduplicator<TestElement> deduplicator;

	public DomAutoCompleteDisplayStringDeduplicatorTest() {

		this.deduplicator = new DomAutoCompleteDisplayStringDeduplicator<>(TestElement::toDisplay, TestElement::compareTo);
	}

	@Test
	public void testWithDistinctDisplayStringsAndDistinctElements() {

		addTestElement("FOO", 1);
		addTestElement("baz", 2);
		addTestElement("Bar", 3);

		Map<String, TestElement> map = deduplicator.apply(elements);

		assertMap("""
				Bar=3
				baz=2
				FOO=1""", map);
	}

	@Test
	public void testWithDistinctDisplayStringsAndRedundantElements() {

		// different strings but equal value
		addTestElement("FOO", 3);
		addTestElement("baz", 3);
		addTestElement("Bar", 3);

		Map<String, TestElement> map = deduplicator.apply(elements);

		assertMap("""
				Bar=3
				baz=3
				FOO=3""", map);
	}

	@Test
	public void testWithRedundantDisplayStringsAndDistinctElements() {

		// ascending values
		addTestElement("FOO", 1);
		addTestElement("foo", 2);
		addTestElement("Foo", 3);

		// descending values
		addTestElement("FOO", 9);
		addTestElement("foo", 8);
		addTestElement("Foo", 7);

		Map<String, TestElement> map = deduplicator.apply(elements);

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
	public void testWithRedundantDisplayStringsAndRedundantElements() {

		// initial order of display strings
		addTestElement("FOO", 3);
		addTestElement("foo", 3);
		addTestElement("Foo", 3);

		// reverse order of display strings
		addTestElement("Foo", 3);
		addTestElement("foo", 3);
		addTestElement("FOO", 3);

		Map<String, TestElement> map = deduplicator.apply(elements);

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
	public void testWithDeeplyRedundantDisplayStringsAndDistinctElements() {

		addTestElement("foo", 3);
		addTestElement("Foo", 2);
		addTestElement("fOO", 7);
		addTestElement("foo (1)", 4);
		addTestElement("foo (1)", 9);
		addTestElement("Foo (1)", 5);
		addTestElement("foo (3)", 6);
		addTestElement("foobar", 1);

		Map<String, TestElement> map = deduplicator.apply(elements);

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

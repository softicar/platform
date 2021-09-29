package com.softicar.platform.dom.node.tree;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.stream.Collectors;
import org.junit.Test;

public class DomNodeTreeTest extends AbstractTest {

	private final Node a;
	private final Node b;
	private final Node c;
	private final Node d;

	public DomNodeTreeTest() {

		CurrentDomDocument.set(new DomDocument());

		this.a = new Node("A");
		this.b = new Node("B");
		this.c = new Node("C");
		this.d = new Node("D");
	}

	@Test
	public void testHierarchy1() {

		//   A   //
		//  /|\  //
		// B C D //
		a.appendChild(b);
		a.appendChild(c);
		a.appendChild(d);

		assertEquals(
			"ABCD",
			new DomNodeTree(a)//
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining()));
	}

	@Test
	public void testHierarchy2() {

		//   A   //
		//  / \  //
		// C   D //
		//     | //
		//     B //
		a.appendChild(c);
		a.appendChild(d);
		d.appendChild(b);

		assertEquals(
			"ACDB",
			new DomNodeTree(a)//
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining()));
	}

	@Test
	public void testHierarchy3() {

		//   A   //
		//  / \  //
		// B   C //
		// |     //
		// D     //
		a.appendChild(b);
		b.appendChild(d);
		a.appendChild(c);

		assertEquals(
			"ABDC",
			new DomNodeTree(a)//
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining()));
	}

	@Test
	public void testHierarchy4() {

		// A //
		// | //
		// D //
		// | //
		// C //
		// | //
		// B //
		a.appendChild(d);
		d.appendChild(c);
		c.appendChild(b);

		assertEquals(
			"ADCB",
			new DomNodeTree(a)//
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining()));
	}

	private static class Node extends DomDiv {

		private final String name;

		public Node(String name) {

			this.name = name;
		}

		@Override
		public String toString() {

			return name;
		}
	}
}

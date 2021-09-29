package com.softicar.platform.dom.parent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import org.junit.Before;
import org.junit.Test;

public class DomParentElementTest {

	private TestElement parent;
	private TestElement child1;
	private TestElement child2;
	private TestElement child3;

	@Before
	public void before() {

		CurrentDomDocument.set(new DomDocument());
		this.parent = new TestElement();
		this.child1 = new TestElement();
		this.child2 = new TestElement();
		this.child3 = new TestElement();
	}

	@Test
	public void prependChild() {

		parent.prependChild(child3);
		parent.prependChild(child2);
		parent.prependChild(child1);

		check();
	}

	@Test
	public void appendChild() {

		parent.appendChild(child1);
		parent.appendChild(child2);
		parent.appendChild(child3);

		check();
	}

	@Test
	public void appendChildren() {

		parent.appendChildren(child1, child2, child3);

		check();
	}

	@Test
	public void insertAt() {

		parent.appendChild(child1);
		parent.appendChild(child3);
		parent.insertAt(child2, 1);

		check();
	}

	@Test
	public void insertBefore() {

		parent.appendChild(child3);
		parent.insertBefore(child1, child3);
		parent.insertBefore(child2, child3);

		check();
	}

	@Test
	public void insertAfter() {

		parent.appendChild(child1);
		parent.insertAfter(child3, child1);
		parent.insertAfter(child2, child1);

		check();
	}

	@Test
	public void hasChild() {

		assertFalse(parent.hasChild(child1));
		parent.appendChild(child1);
		assertTrue(parent.hasChild(child1));
	}

	@Test
	public void getChildCount() {

		assertEquals(0, parent.getChildCount());
		parent.appendChild(child1);
		assertEquals(1, parent.getChildCount());
		parent.appendChild(child2);
		assertEquals(2, parent.getChildCount());
	}

	@Test
	public void getChildIndex() {

		parent.appendChild(child1);
		parent.appendChild(child2);
		parent.appendChild(child3);

		assertEquals(0, parent.getChildIndex(child1));
		assertEquals(1, parent.getChildIndex(child2));
		assertEquals(2, parent.getChildIndex(child3));
	}

	@Test
	public void getChild() {

		parent.appendChild(child1);
		parent.appendChild(child2);
		parent.appendChild(child3);

		assertSame(child1, parent.getChild(0));
		assertSame(child2, parent.getChild(1));
		assertSame(child3, parent.getChild(2));
	}

	@Test(expected = SofticarDeveloperException.class)
	public void throwsIfAppendingSameChildTwice() {

		parent.appendChild(child1);
		parent.appendChild(child1);
	}

	private void check() {

		assertEquals(3, parent.getChildCount());
		assertSame(child1, parent.getChild(0));
		assertSame(child2, parent.getChild(1));
		assertSame(child3, parent.getChild(2));
	}
}

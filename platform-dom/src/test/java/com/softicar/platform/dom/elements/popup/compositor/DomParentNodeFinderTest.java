package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Optional;
import org.junit.Test;

public class DomParentNodeFinderTest extends AbstractTest {

	private final DomDocument document;

	public DomParentNodeFinderTest() {

		this.document = new DomDocument();
		CurrentDomDocument.set(document);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNull() {

		DevNull.swallow(new DomParentNodeFinder<>(null));
	}

	@Test
	public void testFindClosestParent() {

		DomDiv upper = appendChild(new TestParent());
		DomDiv middle = upper.appendChild(new TestParent());
		DomDiv lower = middle.appendChild(new DomDiv());

		Optional<TestParent> parent = createFetcher(TestParent.class).findClosestParent(lower);

		assertTrue(parent.isPresent());
		assertSame(middle, parent.get());
	}

	@Test
	public void testFindClosestParentIncludingRoot() {

		DomDiv upper = appendChild(new TestParent());
		DomDiv lower = upper.appendChild(new TestParent());

		Optional<TestParent> parent = createFetcher(TestParent.class).findClosestParent(lower, true);

		assertTrue(parent.isPresent());
		assertSame(lower, parent.get());
	}

	@Test
	public void testFindClosestParentExcludingRoot() {

		DomDiv upper = appendChild(new TestParent());
		DomDiv lower = upper.appendChild(new TestParent());

		Optional<TestParent> parent = createFetcher(TestParent.class).findClosestParent(lower, false);

		assertTrue(parent.isPresent());
		assertSame(upper, parent.get());
	}

	@Test
	public void testFindClosestParentWithoutParent() {

		Optional<TestParent> parent = createFetcher(TestParent.class).findClosestParent(new DomDiv());
		assertTrue(parent.isEmpty());
	}

	@Test
	public void testFindClosestParentWithoutMatchingParent() {

		DomDiv upper = appendChild(new DomDiv());
		DomDiv lower = upper.appendChild(new DomDiv());

		Optional<TestParent> parent = createFetcher(TestParent.class).findClosestParent(lower);

		assertTrue(parent.isEmpty());
	}

	@Test(expected = NullPointerException.class)
	public void testFindClosestParentWithNull() {

		createFetcher(IDomNode.class).findClosestParent(null);
	}

	@Test
	public void testFindMostDistantParent() {

		DomDiv top = appendChild(new DomDiv());
		DomDiv upper = top.appendChild(new TestParent());
		DomDiv middle = upper.appendChild(new DomDiv());
		DomDiv lower = middle.appendChild(new TestParent());
		DomDiv bottom = lower.appendChild(new DomDiv());

		Optional<TestParent> parent = createFetcher(TestParent.class).findMostDistantParent(bottom);

		assertTrue(parent.isPresent());
		assertSame(upper, parent.get());
	}

	@Test
	public void testFindMostDistantParentOfAnyType() {

		DomDiv top = appendChild(new DomDiv());
		DomDiv middle = top.appendChild(new TestParent());
		DomDiv lower = middle.appendChild(new DomDiv());

		Optional<IDomNode> parent = createFetcher(IDomNode.class).findMostDistantParent(lower);

		assertTrue(parent.isPresent());
		assertSame(document.getBody(), parent.get());
	}

	@Test
	public void testFindMostDistantParentWithoutParent() {

		Optional<TestParent> parent = createFetcher(TestParent.class).findMostDistantParent(new DomDiv());

		assertFalse(parent.isPresent());
	}

	@Test
	public void testFindMostDistantParentWithoutMatchingParent() {

		DomDiv upper = appendChild(new DomDiv());
		DomDiv lower = upper.appendChild(new DomDiv());

		Optional<TestParent> parent = createFetcher(TestParent.class).findMostDistantParent(lower);

		assertFalse(parent.isPresent());
	}

	private <T extends IDomNode> T appendChild(T node) {

		return document.getBody().appendChild(node);
	}

	private <T extends IDomNode> DomParentNodeFinder<T> createFetcher(Class<T> parentNodeClass) {

		return new DomParentNodeFinder<>(parentNodeClass);
	}

	private class TestParent extends DomDiv {

		// nothing
	}
}

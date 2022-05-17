package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.HashMap;
import org.junit.Test;

public class DomPopupHierarchyGraphTest extends AbstractTest {

	private final DomPopupHierarchyGraph graph;

	public DomPopupHierarchyGraphTest() {

		this.graph = new DomPopupHierarchyGraph();
		CurrentDomDocument.set(new DomDocument());
	}

	@Test
	public void testAdd() {

		var parent = new DomPopup();
		var child = new DomPopup();
		graph.add(parent, child);
	}

	@Test(expected = NullPointerException.class)
	public void testAddWithNullParent() {

		graph.add(null, new DomPopup());
	}

	@Test(expected = NullPointerException.class)
	public void testAddWithNullChild() {

		graph.add(new DomPopup(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddWithSameObject() {

		var popup = new DomPopup();
		graph.add(popup, popup);
	}

	@Test
	public void testGetAllChildPopups() {

		var alpha = new DomPopup();
		var beta = new DomPopup();
		var gamma = new DomPopup();
		var delta = new DomPopup();
		graph.add(alpha, beta);
		graph.add(alpha, gamma);
		graph.add(beta, delta);

		var alphaChildren = graph.getAllChildPopups(alpha);
		assertEquals(3, alphaChildren.size());
		assertSame(delta, alphaChildren.get(0));
		assertSame(beta, alphaChildren.get(1));
		assertSame(gamma, alphaChildren.get(2));

		var betaChildren = graph.getAllChildPopups(beta);
		assertEquals(1, betaChildren.size());
		assertSame(delta, betaChildren.get(0));

		var gammaChildren = graph.getAllChildPopups(gamma);
		assertEquals(0, gammaChildren.size());

		var deltaChildren = graph.getAllChildPopups(delta);
		assertEquals(0, deltaChildren.size());
	}

	@Test
	public void testGetAllChildPopupsWithNonExistingEntry() {

		var children = graph.getAllChildPopups(new DomPopup());
		assertEquals(0, children.size());
	}

	@Test(expected = NullPointerException.class)
	public void testGetAllChildPopupsWithNull() {

		graph.getAllChildPopups(null);
	}

	@Test
	public void testClear() {

		// setup
		var alpha = new DomPopup();
		var alphaChild = new DomPopup();
		var beta = new DomPopup();
		var betaChild = new DomPopup();

		graph.add(alpha, alphaChild);
		graph.add(beta, betaChild);

		// assert initial state
		var alphaChildren = graph.getAllChildPopups(alpha);
		assertEquals(1, alphaChildren.size());
		assertSame(alphaChild, alphaChildren.get(0));
		var betaChildren = graph.getAllChildPopups(beta);
		assertEquals(1, betaChildren.size());
		assertSame(betaChild, betaChildren.get(0));

		// execute
		graph.clear();

		// assert result
		alphaChildren = graph.getAllChildPopups(alpha);
		assertEquals(0, alphaChildren.size());
		betaChildren = graph.getAllChildPopups(beta);
		assertEquals(0, betaChildren.size());
	}

	@Test
	public void testClearWithEmptyGraph() {

		graph.clear();
		// expect no Exception
	}

	@Test
	public void testClearNonMatching() {

		// setup
		var alpha = new DomPopup();
		var alphaChild = new DomPopup();
		var beta = new DomPopup();
		var betaChild = new DomPopup();
		var gamma = new DomPopup();
		var gammaChild = new DomPopup();

		var stateMap = new HashMap<DomPopup, Boolean>();
		stateMap.put(alpha, false);
		stateMap.put(alphaChild, false);
		stateMap.put(beta, true);
		stateMap.put(betaChild, true);
		stateMap.put(gamma, true);
		stateMap.put(gammaChild, false);
		graph.add(alpha, alphaChild);
		graph.add(beta, betaChild);
		graph.add(gamma, gammaChild);

		// assert initial state
		var alphaChildren = graph.getAllChildPopups(alpha);
		assertEquals(1, alphaChildren.size());
		assertSame(alphaChild, alphaChildren.get(0));
		var betaChildren = graph.getAllChildPopups(beta);
		assertEquals(1, betaChildren.size());
		assertSame(betaChild, betaChildren.get(0));
		var gammaChildren = graph.getAllChildPopups(gamma);
		assertEquals(1, gammaChildren.size());
		assertSame(gammaChild, gammaChildren.get(0));

		// execute
		graph.removeAllNonMatching(stateMap::get);

		// assert result
		alphaChildren = graph.getAllChildPopups(alpha);
		assertEquals(0, alphaChildren.size());
		betaChildren = graph.getAllChildPopups(beta);
		assertEquals(1, betaChildren.size());
		gammaChildren = graph.getAllChildPopups(gamma);
		assertEquals(0, gammaChildren.size());
	}

	@Test
	public void testClearNonMatchingWithEmptyGraph() {

		graph.removeAllNonMatching(it -> true);
		graph.removeAllNonMatching(it -> false);
		// expect no Exception
	}

	@Test(expected = NullPointerException.class)
	public void testClearNonMatchingWithNull() {

		graph.removeAllNonMatching(null);
	}
}

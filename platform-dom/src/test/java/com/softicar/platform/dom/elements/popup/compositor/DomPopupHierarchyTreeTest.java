package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.HashMap;
import java.util.Optional;
import org.junit.Test;

public class DomPopupHierarchyTreeTest extends AbstractTest {

	private final DomPopupHierarchyTree tree;

	public DomPopupHierarchyTreeTest() {

		this.tree = new DomPopupHierarchyTree();
		CurrentDomDocument.set(new DomDocument());
	}

	@Test
	public void testGetAllChildPopups() {

		var alpha = new DomPopup();
		var beta = new DomPopup();
		var gamma = new DomPopup();
		var delta = new DomPopup();
		tree.add(alpha, beta);
		tree.add(alpha, gamma);
		tree.add(beta, delta);

		var alphaChildren = tree.getAllChildPopups(alpha);
		assertEquals(3, alphaChildren.size());
		assertSame(delta, alphaChildren.get(0));
		assertSame(beta, alphaChildren.get(1));
		assertSame(gamma, alphaChildren.get(2));

		var betaChildren = tree.getAllChildPopups(beta);
		assertEquals(1, betaChildren.size());
		assertSame(delta, betaChildren.get(0));

		var gammaChildren = tree.getAllChildPopups(gamma);
		assertEquals(0, gammaChildren.size());

		var deltaChildren = tree.getAllChildPopups(delta);
		assertEquals(0, deltaChildren.size());
	}

	@Test
	public void testGetAllChildPopupsWithNonExistingEntry() {

		var children = tree.getAllChildPopups(new DomPopup());
		assertEquals(0, children.size());
	}

	@Test(expected = NullPointerException.class)
	public void testGetAllChildPopupsWithNull() {

		tree.getAllChildPopups(null);
	}

	@Test
	public void testGetParentPopup() {

		// setup
		var alpha = new DomPopup();
		var beta = new DomPopup();
		tree.add(alpha, beta);

		// execute
		Optional<DomPopup> parent = tree.getParentPopup(beta);

		// assert result
		assertTrue(parent.isPresent());
		assertSame(alpha, parent.get());
	}

	@Test
	public void testGetParentPopupWithClosedParentPopup() {

		// setup
		var alpha = new DomPopup();
		var beta = new DomPopup();
		tree.add(alpha, beta);

		var stateMap = new HashMap<DomPopup, Boolean>();
		stateMap.put(alpha, false);
		stateMap.put(beta, true);

		tree.removeAllClosedLeaves(stateMap::get);

		// execute
		Optional<DomPopup> parent = tree.getParentPopup(beta);

		// assert result
		assertTrue(parent.isPresent());
		assertSame(alpha, parent.get());
	}

	@Test
	public void testGetParentPopupWithTopLevelPopup() {

		// setup
		var alpha = new DomPopup();
		var beta = new DomPopup();
		tree.add(alpha, beta);

		// execute
		Optional<DomPopup> parent = tree.getParentPopup(alpha);

		// assert result
		assertFalse(parent.isPresent());
	}

	@Test
	public void testGetParentPopupWithEmptyTree() {

		// execute
		Optional<DomPopup> parent = tree.getParentPopup(new DomPopup());

		// assert result
		assertFalse(parent.isPresent());
	}

	@Test(expected = NullPointerException.class)
	public void testGetParentPopupWithNull() {

		tree.getParentPopup(null);
	}

	@Test
	public void testAdd() {

		var parent = new DomPopup();
		var child = new DomPopup();
		tree.add(parent, child);
	}

	@Test(expected = NullPointerException.class)
	public void testAddWithNullParent() {

		tree.add(null, new DomPopup());
	}

	@Test(expected = NullPointerException.class)
	public void testAddWithNullChild() {

		tree.add(new DomPopup(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddWithSameObject() {

		var popup = new DomPopup();
		tree.add(popup, popup);
	}

	@Test
	public void testRemoveAllClosedLeaves() {

		// setup
		var alpha = new DomPopup();
		var alphaChild = new DomPopup();
		var beta = new DomPopup();
		var betaChild = new DomPopup();
		var gamma = new DomPopup();
		var gammaChild = new DomPopup();
		var delta = new DomPopup();
		var deltaChild = new DomPopup();
		var deltaChildChild = new DomPopup();
		var deltaChildChildChild = new DomPopup();

		var stateMap = new HashMap<DomPopup, Boolean>();
		stateMap.put(alpha, false);
		stateMap.put(alphaChild, false);
		stateMap.put(beta, true);
		stateMap.put(betaChild, true);
		stateMap.put(gamma, true);
		stateMap.put(gammaChild, false);
		stateMap.put(delta, false);
		stateMap.put(deltaChild, true);
		stateMap.put(deltaChildChild, false);
		stateMap.put(deltaChildChildChild, false);
		tree.add(alpha, alphaChild);
		tree.add(beta, betaChild);
		tree.add(gamma, gammaChild);
		tree.add(delta, deltaChild);
		tree.add(deltaChild, deltaChildChild);
		tree.add(deltaChildChild, deltaChildChildChild);

		// assert initial state
		var alphaChildren = tree.getAllChildPopups(alpha);
		assertEquals(1, alphaChildren.size());
		assertSame(alphaChild, alphaChildren.get(0));

		var betaChildren = tree.getAllChildPopups(beta);
		assertEquals(1, betaChildren.size());
		assertSame(betaChild, betaChildren.get(0));

		var gammaChildren = tree.getAllChildPopups(gamma);
		assertEquals(1, gammaChildren.size());
		assertSame(gammaChild, gammaChildren.get(0));

		var deltaChildren = tree.getAllChildPopups(delta);
		assertEquals(3, deltaChildren.size());
		assertSame(deltaChildChildChild, deltaChildren.get(0));
		assertSame(deltaChildChild, deltaChildren.get(1));
		assertSame(deltaChild, deltaChildren.get(2));

		// execute
		tree.removeAllClosedLeaves(stateMap::get);

		// assert result
		alphaChildren = tree.getAllChildPopups(alpha);
		assertEquals(0, alphaChildren.size());

		betaChildren = tree.getAllChildPopups(beta);
		assertEquals(1, betaChildren.size());
		assertSame(betaChild, betaChildren.get(0));

		gammaChildren = tree.getAllChildPopups(gamma);
		assertEquals(0, gammaChildren.size());

		deltaChildren = tree.getAllChildPopups(delta);
		assertEquals(1, deltaChildren.size());
		assertSame(deltaChild, deltaChildren.get(0));
	}

	@Test
	public void testRemoveAllClosedLeavesWithEmptyTree() {

		tree.removeAllClosedLeaves(it -> true);
		tree.removeAllClosedLeaves(it -> false);
		// expect no Exception
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveAllClosedLeavesWithNull() {

		tree.removeAllClosedLeaves(null);
	}

	@Test
	public void testClear() {

		// setup
		var alpha = new DomPopup();
		var alphaChild = new DomPopup();
		var beta = new DomPopup();
		var betaChild = new DomPopup();

		tree.add(alpha, alphaChild);
		tree.add(beta, betaChild);

		// assert initial state
		var alphaChildren = tree.getAllChildPopups(alpha);
		assertEquals(1, alphaChildren.size());
		assertSame(alphaChild, alphaChildren.get(0));
		var betaChildren = tree.getAllChildPopups(beta);
		assertEquals(1, betaChildren.size());
		assertSame(betaChild, betaChildren.get(0));

		// execute
		tree.clear();

		// assert result
		alphaChildren = tree.getAllChildPopups(alpha);
		assertEquals(0, alphaChildren.size());
		betaChildren = tree.getAllChildPopups(beta);
		assertEquals(0, betaChildren.size());
	}

	@Test
	public void testClearWithEmptyTree() {

		tree.clear();
		// expect no Exception
	}
}

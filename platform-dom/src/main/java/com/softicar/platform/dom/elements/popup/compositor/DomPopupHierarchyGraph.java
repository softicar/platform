package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A graph to track parent-child nesting relations between {@link DomPopup}
 * elements.
 *
 * @author Alexander Schmidt
 */
class DomPopupHierarchyGraph {

	private ChildMap childMap;

	/**
	 * Constructs a new {@link DomPopupHierarchyGraph}.
	 */
	public DomPopupHierarchyGraph() {

		this.childMap = new ChildMap();
	}

	/**
	 * Adds the given parent-child relation to this graph.
	 *
	 * @param parent
	 *            the parent {@link DomPopup} (never <i>null</i>)
	 * @param child
	 *            the child {@link DomPopup} (never <i>null</i>)
	 * @throws IllegalArgumentException
	 *             if the given parent and child references are the same
	 */
	public void add(DomPopup parent, DomPopup child) {

		childMap.add(parent, child);
	}

	/**
	 * Determines the transitive child {@link DomPopup} elements of the given
	 * parent {@link DomPopup}.
	 * <p>
	 * The returned {@link List} will be ordered according to descending depth
	 * in the hierarchy graph. That is, deeply-nested children will occur before
	 * less deeply-nested children.
	 * <p>
	 * Returns an empty {@link List} if the given {@link DomPopup} is not a
	 * parent element in this graph.
	 *
	 * @param parent
	 *            the parent {@link DomPopup} (never <i>null</i>)
	 * @return all child {@link DomPopup} elements (never <i>null</i>)
	 */
	public List<DomPopup> getAllChildPopups(DomPopup parent) {

		Objects.requireNonNull(parent);
		var childPopups = new ArrayList<DomPopup>();
		Optional.ofNullable(childMap.get(parent)).ifPresent(children -> {
			children.stream().map(this::getAllChildPopups).forEach(childPopups::addAll);
			childPopups.addAll(children);
		});
		return childPopups;
	}

	/**
	 * Removes all {@link DomPopup} elements from this
	 * {@link DomPopupHierarchyGraph}.
	 */
	public void clear() {

		childMap.clear();
	}

	/**
	 * Removes all {@link DomPopup} elements which are not open according to the
	 * given {@link Predicate}, and which also do not have child
	 * {@link DomPopup} elements.
	 *
	 * @param openStateFetcher
	 *            a {@link Predicate} to determine whether a {@link DomPopup} is
	 *            open (never <i>null</i>)
	 */
	public void removeAllClosedLeaves(Predicate<DomPopup> openStateFetcher) {

		Objects.requireNonNull(openStateFetcher);
		var newChildMap = deepCopy(childMap);
		boolean iterate = true;
		while (iterate) {
			iterate = false;
			for (var entry: childMap.entrySet()) {
				DomPopup parent = entry.getKey();
				for (DomPopup child: entry.getValue()) {
					if (isClosedLeaf(child, openStateFetcher, newChildMap)) {
						List<DomPopup> newChildren = newChildMap.get(parent);
						if (newChildren != null) {
							newChildren.remove(child);
							if (newChildren.isEmpty()) {
								// At least one popup no longer has children, so we need another iteration.
								newChildMap.remove(parent);
								iterate = true;
							}
						}
					}
				}
			}
		}

		this.childMap = newChildMap;
	}

	private boolean isClosedLeaf(DomPopup child, Predicate<DomPopup> openStateFetcher, ChildMap childMap) {

		return !openStateFetcher.test(child) && !childMap.containsKey(child);
	}

	private ChildMap deepCopy(ChildMap original) {

		var copy = new ChildMap();
		for (var entry: original.entrySet()) {
			DomPopup parent = entry.getKey();
			for (DomPopup child: entry.getValue()) {
				copy.add(parent, child);
			}
		}
		return copy;
	}

	private static class ChildMap extends HashMap<DomPopup, List<DomPopup>> {

		public void add(DomPopup parent, DomPopup child) {

			Objects.requireNonNull(parent);
			Objects.requireNonNull(child);
			if (parent == child) {
				throw new IllegalArgumentException();
			}
			computeIfAbsent(parent, dummy -> new ArrayList<>()).add(child);
		}
	}
}

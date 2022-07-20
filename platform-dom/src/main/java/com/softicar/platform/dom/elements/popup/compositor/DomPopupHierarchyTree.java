package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link IDomPopupHierarchyTree}.
 *
 * @author Alexander Schmidt
 */
class DomPopupHierarchyTree implements IDomPopupHierarchyTree {

	private ChildMap childMap;
	private final ParentMap parentMap;

	/**
	 * Constructs a new {@link DomPopupHierarchyTree}.
	 */
	public DomPopupHierarchyTree() {

		this.childMap = new ChildMap();
		this.parentMap = new ParentMap();
	}

	@Override
	public List<DomPopup> getAllChildPopups(DomPopup parent) {

		Objects.requireNonNull(parent);
		var childPopups = new ArrayList<DomPopup>();
		Optional.ofNullable(childMap.get(parent)).ifPresent(children -> {
			children.stream().map(this::getAllChildPopups).forEach(childPopups::addAll);
			childPopups.addAll(children);
		});
		return childPopups;
	}

	@Override
	public Optional<DomPopup> getParentPopup(DomPopup child) {

		Objects.requireNonNull(child);
		return Optional.ofNullable(parentMap.get(child));
	}

	/**
	 * Adds the given parent-child relation to this tree.
	 *
	 * @param parent
	 *            the parent {@link DomPopup} (never <i>null</i>)
	 * @param child
	 *            the child {@link DomPopup} (never <i>null</i>)
	 * @throws IllegalArgumentException
	 *             if the given parent and child references are the same
	 */
	public void add(DomPopup parent, DomPopup child) {

		childMap.put(parent, child);
		parentMap.put(child, parent);
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

		updateParentMap();
	}

	/**
	 * Removes all {@link DomPopup} elements from this
	 * {@link DomPopupHierarchyTree}.
	 */
	public void clear() {

		childMap.clear();
		parentMap.clear();
	}

	private boolean isClosedLeaf(DomPopup child, Predicate<DomPopup> openStateFetcher, ChildMap childMap) {

		return !openStateFetcher.test(child) && !childMap.containsKey(child);
	}

	private ChildMap deepCopy(ChildMap original) {

		var copy = new ChildMap();
		for (var entry: original.entrySet()) {
			DomPopup parent = entry.getKey();
			for (DomPopup child: entry.getValue()) {
				copy.put(parent, child);
			}
		}
		return copy;
	}

	private void updateParentMap() {

		HashSet<DomPopup> children = childMap//
			.values()
			.stream()
			.flatMap(it -> it.stream())
			.collect(Collectors.toCollection(() -> new HashSet<>()));
		parentMap.keySet().retainAll(children);
	}

	private static class ChildMap extends HashMap<DomPopup, List<DomPopup>> {

		public void put(DomPopup parent, DomPopup child) {

			Objects.requireNonNull(parent);
			Objects.requireNonNull(child);
			if (parent == child) {
				throw new IllegalArgumentException();
			}
			computeIfAbsent(parent, dummy -> new ArrayList<>()).add(child);
		}
	}

	private static class ParentMap extends HashMap<DomPopup, DomPopup> {

		// nothing
	}
}

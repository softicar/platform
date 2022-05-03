package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	private Map<DomPopup, List<DomPopup>> graph;

	/**
	 * Constructs a new {@link DomPopupHierarchyGraph}.
	 */
	public DomPopupHierarchyGraph() {

		this.graph = new HashMap<>();
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

		Objects.requireNonNull(parent);
		Objects.requireNonNull(child);
		if (parent == child) {
			throw new IllegalArgumentException();
		}
		graph.computeIfAbsent(parent, dummy -> new ArrayList<>()).add(child);
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
		Optional.ofNullable(graph.get(parent)).ifPresent(children -> {
			children.stream().map(this::getAllChildPopups).forEach(childPopups::addAll);
			childPopups.addAll(children);
		});
		return childPopups;
	}

	/**
	 * Removes all {@link DomPopup} elements from this
	 * {@link DomPopupHierarchyGraph}.
	 */
	public void removeAll() {

		graph.clear();
	}

	/**
	 * Removes all {@link DomPopup} elements which do not fulfill the given
	 * {@link Predicate}.
	 *
	 * @param filter
	 *            the filter predicate (never <i>null</i>)
	 */
	public void removeAllNonMatching(Predicate<DomPopup> filter) {

		Objects.requireNonNull(filter);
		var newGraph = new DomPopupHierarchyGraph();
		for (var entry: graph.entrySet()) {
			DomPopup parent = entry.getKey();
			for (DomPopup child: entry.getValue()) {
				if (filter.test(parent) && filter.test(child)) {
					newGraph.add(parent, child);
				}
			}
		}
		this.graph = newGraph.graph;
	}
}

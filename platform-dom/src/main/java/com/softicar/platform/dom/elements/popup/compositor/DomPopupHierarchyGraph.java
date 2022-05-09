package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * A graph to track parent-child nesting relations between {@link DomPopup}
 * nodes.
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
	 */
	public void add(DomPopup parent, DomPopup child) {

		Objects.requireNonNull(parent);
		Objects.requireNonNull(child);
		graph.computeIfAbsent(parent, dummy -> new ArrayList<>()).add(child);
	}

	/**
	 * Determines the transitive child {@link DomPopup} nodes of the given
	 * parent {@link DomPopup}.
	 * <p>
	 * The returned {@link List} will be ordered according to descending depth
	 * in the hierarchy graph. That is, deeply-nested children will occur before
	 * less deeply-nested children.
	 *
	 * @param parent
	 *            the parent {@link DomPopup} (never <i>null</i>)
	 * @return all child {@link DomPopup} nodes (never <i>null</i>)
	 */
	public List<DomPopup> getAllChildPopups(DomPopup parent) {

		var childPopups = new ArrayList<DomPopup>();
		List<DomPopup> children = graph.get(parent);
		if (children != null && !children.isEmpty()) {
			for (DomPopup child: children) {
				childPopups.addAll(getAllChildPopups(child));
			}
			childPopups.addAll(children);
		}
		return childPopups;
	}

	/**
	 * Removes all {@link DomPopup} nodes that are not appended from this graph.
	 */
	public void cleanup() {

		Map<DomPopup, List<DomPopup>> map = new HashMap<>();
		for (Entry<DomPopup, List<DomPopup>> entry: graph.entrySet()) {
			DomPopup parent = entry.getKey();
			for (DomPopup child: entry.getValue()) {
				if (parent.isAppended() && child.isAppended()) {
					map.computeIfAbsent(parent, dummy -> new ArrayList<>()).add(child);
				}
			}
		}
		this.graph = map;
	}
}

package com.softicar.platform.common.math.topology;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopologicalSorter<T> {

	private final Map<T, Node> nodes;
	private List<T> sortedElements;

	public TopologicalSorter() {

		this.nodes = new HashMap<>();
	}

	public TopologicalSorter(Collection<T> elements) {

		this();
		addAll(elements);
	}

	public TopologicalSorter<T> add(T element) {

		nodes.put(element, new Node(element));
		return this;
	}

	public TopologicalSorter<T> addAll(Collection<T> elements) {

		elements.forEach(it -> nodes.put(it, new Node(it)));
		return this;
	}

	public TopologicalSorter<T> addEdge(T elementA, T elementB) {

		nodes//
			.computeIfAbsent(elementA, Node::new)
			.addTargetNode(nodes.computeIfAbsent(elementB, Node::new));
		return this;
	}

	public Collection<T> getSorted() {

		this.sortedElements = new ArrayList<>();
		for (Node node: nodes.values()) {
			visit(node);
		}
		Collections.reverse(sortedElements);
		return sortedElements;
	}

	private void visit(Node node) {

		if (node.visited) {
			throw new CyclicTopologyException();
		}

		if (!node.done) {
			node.visited = true;
			for (Node targetNode: node.targetNodes) {
				visit(targetNode);
			}
			node.visited = false;
			node.done = true;
			sortedElements.add(node.element);
		}
	}

	private class Node {

		private final T element;
		private Collection<Node> targetNodes;
		private boolean visited;
		private boolean done;

		public Node(T element) {

			this.element = element;
			this.targetNodes = Collections.emptyList();
			this.visited = false;
			this.done = false;
		}

		public void addTargetNode(Node targetNode) {

			if (targetNodes.isEmpty()) {
				targetNodes = new ArrayList<>();
			}
			targetNodes.add(targetNode);
		}
	}

	public static class CyclicTopologyException extends SofticarDeveloperException {

		public CyclicTopologyException() {

			super("There are cycles in the dependeny graph.");
		}
	}
}

package com.softicar.platform.dom.document.marker;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.DomProperties;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Default implementation of {@link IDomDocumentMarkerHolder}.
 * <p>
 * Relies on {@link DomProperties#TEST_MODE} to determine whether the DOM
 * framework is in test mode.
 *
 * @author Alexander Schmidt
 */
public class DomDocumentMarkerHolder implements IDomDocumentMarkerHolder {

	private final IDomDocument document;
	private final Map<IStaticObject, Set<Integer>> markerToNodes;
	private final Map<Integer, Set<IStaticObject>> nodeToMarkers;
	private final boolean testMode;

	public DomDocumentMarkerHolder(IDomDocument document) {

		this.document = document;
		this.markerToNodes = new HashMap<>();
		this.nodeToMarkers = new HashMap<>();
		this.testMode = DomProperties.TEST_MODE.getValue();
	}

	@Override
	public void addMarker(IDomNode node, IStaticObject marker) {

		if (testMode) {
			markerToNodes//
				.computeIfAbsent(marker, dummy -> new TreeSet<>())
				.add(node.getNodeId());
			nodeToMarkers//
				.computeIfAbsent(node.getNodeId(), dummy -> new HashSet<>())
				.add(marker);
		}
	}

	@Override
	public Collection<IDomNode> getNodesWithMarker(IStaticObject marker) {

		if (testMode) {
			return Optional//
				.ofNullable(markerToNodes.get(marker))
				.map(this::getNodes)
				.orElse(Collections.emptyList());
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<IStaticObject> getMarkers(IDomNode node) {

		if (testMode && isAppended(node)) {
			return nodeToMarkers.getOrDefault(node.getNodeId(), Collections.emptySet());
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public boolean hasMarker(IDomNode node, IStaticObject...markers) {

		Collection<IStaticObject> existingMarkers = getMarkers(node);
		return Stream//
			.of(markers)
			.allMatch(existingMarkers::contains);
	}

	private Collection<IDomNode> getNodes(Set<Integer> nodeIds) {

		return getNodeMap(nodeIds).values();
	}

	private Map<Integer, IDomNode> getNodeMap(Set<Integer> nodeIds) {

		Map<Integer, IDomNode> nodeMap = new TreeMap<>();
		Iterator<Integer> iterator = nodeIds.iterator();
		while (iterator.hasNext()) {
			Integer id = iterator.next();
			IDomNode node = document.getNode(id);
			if (node != null && isAppended(node)) {
				nodeMap.put(id, node);
			} else {
				// node was removed by garbage collector
				iterator.remove();
			}
		}
		return nodeMap;
	}

	/**
	 * Determines whether given {@link IDomNode} is appended to the
	 * {@link DomDocument}. That is, whether the {@link DomBody} is a transitive
	 * parent of the given {@link IDomNode}.
	 *
	 * @param node
	 *            the {@link IDomNode} to check (never <i>null</i>)
	 * @return <i>true</i> if the given {@link IDomNode} is appended;
	 *         <i>false</i> otherwise
	 */
	private boolean isAppended(IDomNode node) {

		StringBuilder hierarchy = new StringBuilder();
		hierarchy.append(node.getClass().getSimpleName());
		while (true) {
			IDomParentElement parent = node.getParent();
			if (node instanceof DomBody) {
				return true;
			} else if (parent == null) {
				return false;
			} else {
				node = parent;
			}
		}
	}
}

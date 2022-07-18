package com.softicar.platform.dom.node.initialization;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.elements.popup.compositor.DomParentNodeFinder;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * The default implementation of an
 * {@link IDomDeferredInitializationController}.
 *
 * @author Alexander Schmidt
 */
public class DomDeferredInitializationController implements IDomDeferredInitializationController {

	private final IDomDocument document;
	private final List<IDomNode> appendedNodes;
	private final Map<IDomNode, List<INullaryVoidFunction>> initializers;

	/**
	 * Constructs a new {@link DomDeferredInitializationController}.
	 *
	 * @param document
	 *            the {@link IDomDocument} to which the appended nodes and the
	 *            initializers refer (never <i>null</i>)
	 */
	public DomDeferredInitializationController(IDomDocument document) {

		this.document = Objects.requireNonNull(document);
		this.appendedNodes = new ArrayList<>();
		this.initializers = new WeakHashMap<>();
	}

	@Override
	public void addDeferredInitializer(IDomNode node, INullaryVoidFunction initializer) {

		Objects.requireNonNull(node);
		Objects.requireNonNull(initializer);
		initializers.computeIfAbsent(node, dummy -> new ArrayList<>()).add(initializer);
	}

	@Override
	public void queueAppended(IDomNode node) {

		Objects.requireNonNull(node);
		this.appendedNodes.add(node);
	}

	@Override
	public void handleAllAppended() {

		var finder = new DomParentNodeFinder<>(IDomNode.class);

		for (var appendedNode: appendedNodes) {
			finder.findMostDistantParent(appendedNode).ifPresent(distantParent -> {
				Optional.ofNullable(initializers.remove(appendedNode)).ifPresent(initializerList -> {
					initializers.computeIfAbsent(distantParent, dummy -> new ArrayList<>()).addAll(initializerList);
				});
			});
		}

		Optional//
			.ofNullable(initializers.remove(document.getBody()))
			.orElse(Collections.emptyList())
			.forEach(INullaryVoidFunction::apply);

		appendedNodes.clear();
	}

	@Override
	public void clear() {

		appendedNodes.clear();
		initializers.clear();
	}
}

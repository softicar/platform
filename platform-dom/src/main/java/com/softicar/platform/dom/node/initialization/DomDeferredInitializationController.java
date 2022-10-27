package com.softicar.platform.dom.node.initialization;

import com.softicar.platform.common.container.iterable.concat.ConcatIterable;
import com.softicar.platform.common.container.list.HashList;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.testing.node.iterable.DomNodeRecursiveIterable;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * The default implementation of an
 * {@link IDomDeferredInitializationController}.
 *
 * @author Alexander Schmidt
 */
public class DomDeferredInitializationController implements IDomDeferredInitializationController {

	private final Collection<IDomNode> queuedNodes;
	private final Map<IDomNode, List<INullaryVoidFunction>> initializers;

	/**
	 * Constructs a new {@link DomDeferredInitializationController}.
	 */
	public DomDeferredInitializationController() {

		this.queuedNodes = new HashList<>();
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
		this.queuedNodes.add(node);
	}

	@Override
	public void clear() {

		queuedNodes.clear();
		initializers.clear();
	}

	@Override
	public void handleAllAppended() {

		while (!queuedNodes.isEmpty()) {
			new AppendedNodeInitializer().initialize(flushQueuedNodes());
		}
	}

	private Collection<IDomNode> flushQueuedNodes() {

		var copy = new ArrayList<>(queuedNodes);
		queuedNodes.clear();
		return copy;
	}

	private class AppendedNodeInitializer {

		private final Set<IDomNode> initializedNodes;

		public AppendedNodeInitializer() {

			this.initializedNodes = new HashSet<>();
		}

		public void initialize(Collection<IDomNode> appendedNodes) {

			for (var node: appendedNodes) {
				if (isNotInitialized(node) && node.isAppended()) {
					initializeSubTree(node);
				}
			}
		}

		private boolean isNotInitialized(IDomNode node) {

			return !initializedNodes.contains(node);
		}

		private void initializeSubTree(IDomNode root) {

			for (var node: getSubTree(root)) {
				Optional//
					.ofNullable(initializers.remove(node))
					.orElse(List.of())
					.forEach(INullaryVoidFunction::apply);
				initializedNodes.add(node);
			}
		}

		private ConcatIterable<IDomNode> getSubTree(IDomNode root) {

			return new ConcatIterable<>(Set.of(root), new DomNodeRecursiveIterable(root));
		}
	}
}

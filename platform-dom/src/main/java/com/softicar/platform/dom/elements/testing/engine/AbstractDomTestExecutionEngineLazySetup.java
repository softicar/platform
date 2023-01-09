package com.softicar.platform.dom.elements.testing.engine;

import com.softicar.platform.dom.DomProperties;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.engine.DomTestEngine;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Common base class of {@link IDomTestExecutionEngine} helper classes which set
 * up an {@link IDomNode} under test, on demand.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractDomTestExecutionEngineLazySetup implements IDomTestExecutionEngineLazySetup {

	private IDomNode node;
	private IDomParentElement bodyNode;
	private Supplier<IDomNode> nodeSupplier;
	private Function<Supplier<IDomNode>, IDomNode> nodeInitializer;

	/**
	 * Constructs a new {@link AbstractDomTestExecutionEngineLazySetup}.
	 * <p>
	 * The initializer allows for the customization of the retrieval of the
	 * {@link IDomNode} under test from a {@link Supplier} (as defined via
	 * {@link #setNodeSupplier(Supplier)}).
	 * <p>
	 * <b>Side effect warning:</b> Invokes
	 * {@link CurrentDomDocument#set(IDomDocument)} with a new
	 * {@link DomDocument}.
	 */
	protected AbstractDomTestExecutionEngineLazySetup() {

		System.setProperty(DomProperties.TEST_MODE.getPropertyName().toString(), "true");
		CurrentDomDocument.set(new DomDocument(new DomTestEngine()));
		this.node = null;
		this.bodyNode = null;
		this.nodeSupplier = null;
		this.nodeInitializer = Supplier::get;
	}

	@Override
	public IDomNode getBodyNode() {

		initializeNode();
		return bodyNode;
	}

	@Override
	public void setNodeSupplier(Supplier<IDomNode> nodeSupplier) {

		this.nodeSupplier = Objects.requireNonNull(nodeSupplier);
	}

	@Override
	public void setNodeInitializer(Function<Supplier<IDomNode>, IDomNode> nodeInitializer) {

		this.nodeInitializer = nodeInitializer;
	}

	private void initializeNode() {

		if (node == null) {
			// Discard all refresh bus events that predate the creation of the node-under-test.
			flushRefreshBus();

			// Create the node-under-test.
			this.node = createNode();
			this.bodyNode = node.getParent();

			// Explicitly perform deferred initialization for appended nodes, because we have no DOM event that would have triggered this automatically.
			CurrentDomDocument.get().getDeferredInitializationController().handleAllAppended();
		}
	}

	private void flushRefreshBus() {

		CurrentDomDocument.get().getRefreshBus().discardEvent();
	}

	private IDomNode createNode() {

		Objects.requireNonNull(nodeSupplier, "The node supplier must be defined before this method is called.");
		return nodeInitializer.apply(nodeSupplier);
	}
}

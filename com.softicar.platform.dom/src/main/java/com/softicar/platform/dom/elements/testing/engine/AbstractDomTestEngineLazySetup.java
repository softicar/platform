package com.softicar.platform.dom.elements.testing.engine;

import com.softicar.platform.dom.DomProperties;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Common base class of {@link IDomTestEngine} helper classes which set up an
 * {@link IDomNode} under test, on demand.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractDomTestEngineLazySetup implements IDomTestEngineLazySetup {

	private IDomNode node;
	private Supplier<IDomNode> nodeSupplier;
	private Function<Supplier<IDomNode>, IDomNode> nodeInitializer;

	/**
	 * Constructs a new {@link AbstractDomTestEngineLazySetup}.
	 * <p>
	 * The initializer allows for the customization of the retrieval of the
	 * {@link IDomNode} under test from a {@link Supplier} (as defined via
	 * {@link #setNodeSupplier(Supplier)}).
	 * <p>
	 * <b>Side effect warning:</b> Invokes
	 * {@link CurrentDomDocument#set(IDomDocument)} with a new
	 * {@link DomDocument}.
	 */
	protected AbstractDomTestEngineLazySetup() {

		System.setProperty(DomProperties.TEST_MODE.getPropertyName().toString(), "true");
		CurrentDomDocument.set(new DomDocument());
		this.node = null;
		this.nodeSupplier = null;
		this.nodeInitializer = Supplier::get;
	}

	@Override
	public IDomNode getBodyNode() {

		initializeNode();
		return node.getParent();
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
			flushRefreshBus();
			this.node = createNode();
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

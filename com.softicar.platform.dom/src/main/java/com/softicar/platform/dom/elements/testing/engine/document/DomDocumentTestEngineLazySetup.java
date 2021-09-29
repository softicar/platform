package com.softicar.platform.dom.elements.testing.engine.document;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.testing.engine.AbstractDomTestEngineLazySetup;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Supplier;

/**
 * Facilitates setting up an {@link IDomElement} under test, for an
 * {@link IDomTestEngine}.
 *
 * @author Alexander Schmidt
 */
class DomDocumentTestEngineLazySetup extends AbstractDomTestEngineLazySetup {

	/**
	 * Returns a new {@link DomDocumentTestEngineLazySetup}.
	 * <p>
	 * <b>Side effect warning:</b> Invokes
	 * {@link CurrentDomDocument#set(IDomDocument)} with a new
	 * {@link DomDocument}.
	 */
	public static DomDocumentTestEngineLazySetup createAndRegister() {

		return new DomDocumentTestEngineLazySetup();
	}

	private DomDocumentTestEngineLazySetup() {

		setNodeInitializer(DomDocumentTestEngineLazySetup::initialize);
	}

	/**
	 * Retrieves the {@link IDomNode} from the given {@link Supplier}, and
	 * appends it to a {@link DomBody} of the {@link CurrentDomDocument}.
	 *
	 * @param nodeSupplier
	 *            a {@link Supplier} of the {@link IDomNode} under test (never
	 *            <i>null</i>)
	 * @return the initialized {@link IDomNode} (never <i>null</i>)
	 */
	private static IDomNode initialize(Supplier<IDomNode> nodeSupplier) {

		return CurrentDomDocument.get().getBody().appendChild(nodeSupplier.get());
	}
}

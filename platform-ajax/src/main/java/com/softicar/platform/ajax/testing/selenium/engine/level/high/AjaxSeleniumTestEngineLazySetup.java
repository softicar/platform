package com.softicar.platform.ajax.testing.selenium.engine.level.high;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.elements.testing.engine.AbstractDomTestExecutionEngineLazySetup;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Facilitates setting up an {@link IDomNode} under test, for
 * {@link AjaxSeleniumTestExecutionEngine}.
 *
 * @author Alexander Schmidt
 */
public class AjaxSeleniumTestEngineLazySetup extends AbstractDomTestExecutionEngineLazySetup {

	/**
	 * Returns a new {@link AjaxSeleniumTestEngineLazySetup}, using the given
	 * {@link IDomNode} initializer.
	 * <p>
	 * The initializer allows for the customization of the retrieval of the
	 * {@link IDomNode} under test from a {@link Supplier} (as defined via
	 * {@link #setNodeSupplier(Supplier)}).
	 * <p>
	 * <b>Side effect warning:</b> Invokes
	 * {@link CurrentDomDocument#set(IDomDocument)} with a new
	 * {@link DomDocument}.
	 *
	 * @param nodeInitializer
	 *            retrieves the {@link IDomNode} under test from a
	 *            {@link Supplier}, and initializes it (never <i>null</i>)
	 */
	public static AjaxSeleniumTestEngineLazySetup createAndRegister(Function<Supplier<IDomNode>, IDomNode> nodeInitializer) {

		return new AjaxSeleniumTestEngineLazySetup(nodeInitializer);
	}

	private AjaxSeleniumTestEngineLazySetup(Function<Supplier<IDomNode>, IDomNode> nodeInitializer) {

		setNodeInitializer(nodeInitializer);
	}
}

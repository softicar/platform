package com.softicar.platform.dom.elements.testing.engine;

import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Common interface of {@link IDomTestEngine} helper classes which set up an
 * {@link IDomNode} under test, on demand.
 * <p>
 * The {@link IDomNode} under test is retrieved from a {@link Supplier} (as
 * defined via {@link #setNodeSupplier(Supplier)}), which is lazily invoked when
 * {@link #getBodyNode()} is called for the first time.
 * <p>
 * Supports node initializers which allow for customization of the mechanism
 * which retrieves an {@link IDomNode} under test from a {@link Supplier}.
 *
 * @author Alexander Schmidt
 */
public interface IDomTestEngineLazySetup {

	/**
	 * Returns a {@link DomBody} to which the supplied {@link IDomNode} is
	 * appended as the {@link IDomNode} under test.
	 *
	 * @return a {@link DomBody} to which the {@link IDomNode} under test is
	 *         appended (never <i>null</i>)
	 * @throws NullPointerException
	 *             if {@link #setNodeSupplier(Supplier)} was not invoked before
	 * @see #setNodeSupplier(Supplier)
	 */
	IDomNode getBodyNode();

	/**
	 * Specifies a {@link Supplier} that provides the {@link IDomNode} under
	 * test.
	 * <p>
	 * The {@link Supplier} is invoked when {@link #getBodyNode()} is called for
	 * the first time.
	 *
	 * @param nodeSupplier
	 *            a {@link Supplier} of the {@link IDomNode} under test (never
	 *            <i>null</i>)
	 * @see #getBodyNode()
	 */
	void setNodeSupplier(Supplier<IDomNode> nodeSupplier);

	/**
	 * Defines an initializer for the {@link IDomNode} under test.
	 * <p>
	 * The initializer allows for the customization of the retrieval of the
	 * {@link IDomNode} under test from a {@link Supplier} (as defined via
	 * {@link #setNodeSupplier(Supplier)}).
	 *
	 * @param nodeInitializer
	 *            retrieves the {@link IDomNode} under test from a
	 *            {@link Supplier}, and initializes it (never <i>null</i>)
	 * @see #setNodeSupplier(Supplier)
	 */
	void setNodeInitializer(Function<Supplier<IDomNode>, IDomNode> nodeInitializer);
}

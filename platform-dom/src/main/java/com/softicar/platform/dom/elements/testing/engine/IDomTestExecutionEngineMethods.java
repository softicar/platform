package com.softicar.platform.dom.elements.testing.engine;

import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.elements.testing.node.tester.IDomNodeTesterFindMethods;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Provides methods which are commonly used by {@link IDomTestExecutionEngine}
 * based UI test classes.
 * <p>
 * A test class may implement this interface, to provide convenient access to
 * the functionality of an {@link IDomTestExecutionEngine}:
 *
 * <pre>
 * public class MyTest extends Assert implements IDomTestEngineMethods {
 *
 * 	&#64;Rule public IDomTestEngine engine = new DomDocumentTestEngine();
 *
 * 	public MyTest() {
 *
 * 		setElementSupplier(SomeTestElement::new);
 * 	}
 *
 * 	&#64;Override
 * 	public IDomTestEngine getEngine() {
 *
 * 		return engine;
 * 	}
 *
 * 	&#64;Test
 * 	public void testSomething() {
 *
 *		find(...).click();
 *
 *		assertEquals(...);
 * 	}
 * }
 * </pre>
 *
 * Note that a {@code find*} method, as depicted above, typically returns a
 * {@link DomNodeTester} instance. That instance serves as a wrapper for an
 * {@link IDomNode} which is displayed in the web browser. It can be examined,
 * interacted with, or serve as a base to find further nodes below it, in the
 * {@link DomDocument}.
 *
 * @author Alexander Schmidt
 */
public interface IDomTestExecutionEngineMethods extends IDomNodeTesterFindMethods {

	/**
	 * A default implementation that returns the {@link DomBody} of the
	 * {@link IDomTestExecutionEngine} as the reference node to use when
	 * searching for children with other methods.
	 *
	 * @return the {@link DomBody} of the {@link IDomTestExecutionEngine} (never
	 *         <i>null</i>)
	 */
	@Override
	default IDomNode getNode() {

		return getEngine().getBodyNode();
	}

	/**
	 * Specifies the node-under-test.
	 *
	 * @param node
	 *            the node to test (never <i>null</i>)
	 */
	default <T extends IDomNode> T setNode(T node) {

		setNodeSupplier(() -> node);
		return node;
	}

	/**
	 * Specifies a {@link Supplier} that provides the node-under-test.
	 *
	 * @param nodeSupplier
	 *            a {@link Supplier} of the node to test (never <i>null</i>)
	 */
	default void setNodeSupplier(Supplier<IDomNode> nodeSupplier) {

		getEngine().setNodeSupplier(nodeSupplier);
	}

	/**
	 * Creates a {@link DomNodeTester} to test the given {@link IDomNode}.
	 *
	 * @param node
	 *            the {@link IDomNode} to test (never <i>null</i>)
	 * @return a {@link DomNodeTester} for the given {@link IDomNode} (never
	 *         <i>null</i>)
	 */
	default DomNodeTester asTester(IDomNode node) {

		return new DomNodeTester(getEngine(), Objects.requireNonNull(node));
	}
}

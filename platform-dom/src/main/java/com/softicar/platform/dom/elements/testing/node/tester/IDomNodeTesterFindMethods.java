package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.checkbox.DomCheckboxGroup;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.DomNodeAssertionError;
import com.softicar.platform.dom.elements.testing.node.iterable.DomNodeIterable;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.NoSuchElementException;

/**
 * A partial interface of {@link DomNodeTester} which contains the methods
 * related to finding nodes in a {@link DomDocument}.
 *
 * @author Alexander Schmidt
 */
public interface IDomNodeTesterFindMethods {

	// ------------------------------ basic methods ------------------------------ //

	/**
	 * Returns the currently employed {@link IDomTestExecutionEngine} instance.
	 *
	 * @return the {@link IDomTestExecutionEngine} instance (never <i>null</i>)
	 */
	IDomTestExecutionEngine getEngine();

	/**
	 * Returns the reference {@link IDomNode} which is used as root when
	 * searching for children.
	 *
	 * @return the reference {@link IDomNode} (never <i>null</i>)
	 */
	IDomNode getNode();

	/**
	 * Returns the typed reference {@link IDomNode} which is used as root when
	 * searching for children.
	 *
	 * @param <T>
	 * @param nodeClass
	 *            the expected class of the reference {@link IDomNode} (never
	 *            <i>null</i>)
	 * @return the reference {@link IDomNode} (never <i>null</i>)
	 * @throws NoSuchElementException
	 *             if the reference {@link IDomNode} is not of the given class
	 */
	default <T extends IDomNode> T getNode(Class<T> nodeClass) {

		return CastUtils.tryCast(getNode(), nodeClass).orElseThrow();
	}

	// ------------------------------ find methods for multiple nodes ------------------------------ //

	/**
	 * Creates an {@link IDomNodeIterable} over all transitive children of this
	 * {@link IDomNode}.
	 * <p>
	 * The order of the child nodes in the {@link IDomNodeIterable} will
	 * correspond to a "Pre-Order (NLR)" tree traversal pattern.
	 *
	 * @return all children as an {@link IDomNodeIterable} of {@link IDomNode}
	 *         (never <i>null</i>)
	 */
	default IDomNodeIterable<IDomNode> findChildren() {

		return DomNodeIterable.createWithoutRoot(getEngine(), getNode());
	}

	/**
	 * Creates an {@link IDomNodeIterable} over this {@link IDomNode} and all
	 * its transitive children.
	 * <p>
	 * The order of the child nodes in the {@link IDomNodeIterable} will
	 * correspond to a "Pre-Order (NLR)" tree traversal pattern.
	 *
	 * @return this {@link IDomNode} and all its children as an
	 *         {@link IDomNodeIterable} of {@link IDomNode} (never <i>null</i>)
	 */
	default IDomNodeIterable<IDomNode> findNodes() {

		return DomNodeIterable.createWithRoot(getEngine(), getNode());
	}

	/**
	 * Calls {@link #findNodes()} and filters for the given
	 * {@link ITestMarker}s.
	 *
	 * @param markers
	 *            the {@link ITestMarker}s to search for (never <i>null</i>)
	 * @return all matching {@link IDomNode} objects as an
	 *         {@link IDomNodeIterable} (never <i>null</i>)
	 */
	default IDomNodeIterable<IDomNode> findNodes(ITestMarker...markers) {

		return findNodes().withMarker(markers);
	}

	/**
	 * Calls {@link #findNodes()} and filters for the given type of
	 * {@link IDomNode} instances.
	 *
	 * @param nodeClass
	 *            the {@link Class} to filter for (never <i>null</i>)
	 * @return all matching {@link IDomNode} objects as an
	 *         {@link IDomNodeIterable} (never <i>null</i>)
	 */
	default <T extends IDomNode> IDomNodeIterable<T> findNodes(Class<T> nodeClass) {

		return findNodes().withType(nodeClass);
	}

	// ------------------------------ find methods for single nodes ------------------------------ //

	/**
	 * Calls {@link #findNodes} and asserts for a single {@link IDomNode} with
	 * all given marker.
	 *
	 * @param markers
	 *            the {@link ITestMarker}s to search for (never <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching {@link IDomNode} (never
	 *         <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomNodeTester findNode(ITestMarker...markers) {

		return findNodes(markers).assertOne();
	}

	/**
	 * Calls {@link #findNodes} and asserts for a single {@link IDomNode} of the
	 * given type.
	 *
	 * @param nodeClass
	 *            the {@link Class} of the {@link IDomNode} to search for (never
	 *            <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching {@link IDomNode} (never
	 *         <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomNodeTester findNode(Class<? extends IDomNode> nodeClass) {

		return findNodes(nodeClass).assertOne();
	}

	// ------------------------------ find methods for special nodes ------------------------------ //

	/**
	 * Retrieves the {@link DomBody} node from the
	 * {@link IDomTestExecutionEngine}, and returns it as a
	 * {@link DomNodeTester}.
	 *
	 * @return a {@link DomNodeTester} of the {@link DomBody} node (never
	 *         <i>null</i>)
	 */
	default DomNodeTester findBody() {

		return new DomNodeTester(getEngine(), getEngine().getBodyNode());
	}

	/**
	 * Searches for a {@link DomPopup} with the given {@link ITestMarker}s.
	 *
	 * @param markers
	 *            the {@link ITestMarker}s to search for (never <i>null</i>)
	 * @return a {@link DomPopupTester} of the matching {@link IDomNode}
	 *         instance (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomPopupTester findPopup(ITestMarker...markers) {

		return findNodes(markers)//
			.withType(DomPopup.class)
			.assertOne(node -> new DomPopupTester(getEngine(), node));
	}

	/**
	 * Searches for a {@link DomButton} with the given {@link ITestMarker} .
	 *
	 * @param marker
	 *            the {@link ITestMarker} to search for (never <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching {@link IDomNode} instance
	 *         (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomNodeTester findButton(ITestMarker marker) {

		return findNodes(marker)//
			.withType(DomButton.class)
			.assertOne();
	}

	/**
	 * Searches for the first {@link DomButton} with the given
	 * {@link ITestMarker}.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to search for (never <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching {@link IDomNode} instance
	 *         (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if no matching {@link DomButton} was found
	 */
	default DomNodeTester findFirstButton(ITestMarker marker) {

		return findNodes(marker)//
			.withType(DomButton.class)
			.assertSome()
			.iterator()
			.next();
	}

	/**
	 * Searches for a {@link IDomTextualInput} with the given
	 * {@link ITestMarker}.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to search for (never <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching {@link IDomNode} instance
	 *         (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomNodeTester findInput(ITestMarker marker) {

		return findNodes(marker)//
			.filter(node -> new DomNodeTester(getEngine(), node).findNodes(IDomTextualInput.class).size() > 0)
			.assertOne()
			.findNode(IDomTextualInput.class);
	}

	/**
	 * Searches for a {@link DomCheckbox} with the given {@link ITestMarker} .
	 *
	 * @param marker
	 *            the {@link ITestMarker} to search for (never <i>null</i>)
	 * @return a {@link DomCheckboxTester} of the matching {@link IDomNode}
	 *         instance (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomCheckboxTester findCheckbox(ITestMarker marker) {

		return findNodes(marker)//
			.withType(DomCheckbox.class)
			.assertOne(node -> new DomCheckboxTester(getEngine(), node));
	}

	/**
	 * Searches for a {@link DomCheckboxGroup} with the given
	 * {@link ITestMarker} .
	 *
	 * @param marker
	 *            the {@link ITestMarker} to search for (never <i>null</i>)
	 * @return a {@link DomCheckboxGroupTester} of the matching {@link IDomNode}
	 *         instance (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	@SuppressWarnings("unchecked")
	default <T> DomCheckboxGroupTester<T> findCheckboxGroup(ITestMarker marker) {

		return findNodes(marker)//
			.withType(DomCheckboxGroup.class)
			.assertOne(node -> new DomCheckboxGroupTester<T>(getEngine(), node));
	}

	/**
	 * Searches for a {@link DomTable} with the given {@link ITestMarker}.
	 * <p>
	 * Same as {@link #findTable()} but ignores all nodes without the
	 * {@link ITestMarker}.
	 */
	default DomTableTester findTable(ITestMarker marker) {

		return findNodes(marker)//
			.withType(DomTable.class)
			.assertOne(node -> new DomTableTester(getEngine(), node));
	}

	/**
	 * Searches for a single {@link DomTable} instance.
	 *
	 * @return a {@link DomTableTester} of the matching {@link IDomNode}
	 *         instance (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomTableTester findTable() {

		return findNodes()//
			.withType(DomTable.class)
			.assertOne(node -> new DomTableTester(getEngine(), node));
	}

	/**
	 * Recursively searches for a single {@link DomSelect} child node with the
	 * given {@link ITestMarker}, and returns it as an {@link DomSelectTester}.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to search for (never <i>null</i>)
	 * @return a {@link DomSelectTester} of the single matching child node
	 *         (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching child node, or none at all
	 */
	default <T> DomSelectTester<T> findSelect(ITestMarker marker) {

		return findNode(marker)//
			.findNodes(DomSelect.class)
			.assertOne(node -> new DomSelectTester<>(getEngine(), node));
	}

	/**
	 * Recursively searches for a single {@link DomAutoCompleteInput} child node
	 * with the given {@link ITestMarker}, and returns it as an
	 * {@link DomAutoCompleteTester}.
	 *
	 * @param marker
	 *            the {@link ITestMarker} to search for (never <i>null</i>)
	 * @return a {@link DomAutoCompleteTester} of the single matching child node
	 *         (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching child node, or none at all
	 */
	default <T> DomAutoCompleteTester<T> findAutoCompleteInput(ITestMarker marker) {

		return findNode(marker)//
			.findNodes(DomAutoCompleteInput.class)
			.assertOne(node -> new DomAutoCompleteTester<>(getEngine(), node));
	}

	// ------------------------------ find methods for parent nodes ------------------------------ //

	/**
	 * Recursively searches for a parent node with the given class, and returns
	 * it as a {@link DomNodeTester}.
	 *
	 * @param parentClass
	 *            the class to search for (never <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching parent node (never
	 *         <i>null</i>)
	 * @throws AssertionError
	 *             if there is no matching parent node
	 */
	default DomNodeTester findParentOfType(Class<? extends IDomNode> parentClass) {

		IDomParentElement parent = getNode().getParent();
		while (parent != null) {
			if (parentClass.isInstance(parent)) {
				return new DomNodeTester(getEngine(), parent);
			} else {
				parent = parent.getParent();
			}
		}
		throw new AssertionError(String.format("Expected node to have a parent of type %s.", parentClass.getCanonicalName()));
	}
}

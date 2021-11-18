package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.node.DomNodeAssertionError;
import com.softicar.platform.dom.elements.testing.node.iterable.DomNodeIterable;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.dom.elements.time.day.DomDayInput;
import com.softicar.platform.dom.elements.time.daytime.DomDayTimeInput;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.input.IDomInputNode;
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
	 * Returns the currently employed {@link IDomTestEngine} instance.
	 *
	 * @return the {@link IDomTestEngine} instance (never <i>null</i>)
	 */
	IDomTestEngine getEngine();

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
	 * {@link IStaticObject} markers.
	 *
	 * @param markers
	 *            the {@link IStaticObject} markers to search for (never
	 *            <i>null</i>)
	 * @return all matching {@link IDomNode} objects as an
	 *         {@link IDomNodeIterable} (never <i>null</i>)
	 */
	default IDomNodeIterable<IDomNode> findNodes(IStaticObject...markers) {

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
	 *            the {@link IStaticObject} markers to search for (never
	 *            <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching {@link IDomNode} (never
	 *         <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomNodeTester findNode(IStaticObject...markers) {

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
	 * Retrieves the {@link DomBody} node from the {@link IDomTestEngine}, and
	 * returns it as a {@link DomNodeTester}.
	 *
	 * @return a {@link DomNodeTester} of the {@link DomBody} node (never
	 *         <i>null</i>)
	 */
	default DomNodeTester findBody() {

		return new DomNodeTester(getEngine(), getEngine().getBodyNode());
	}

	/**
	 * Searches for a {@link DomPopup} with the given {@link IStaticObject}
	 * markers.
	 *
	 * @param markers
	 *            the {@link IStaticObject} markers to search for (never
	 *            <i>null</i>)
	 * @return a {@link DomPopupTester} of the matching {@link IDomNode}
	 *         instance (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomPopupTester findPopup(IStaticObject...markers) {

		return findNodes(markers)//
			.withType(DomPopup.class)
			.assertOne(node -> new DomPopupTester(getEngine(), node));
	}

	/**
	 * Searches for a {@link DomButton} with the given {@link IStaticObject}
	 * marker.
	 *
	 * @param marker
	 *            the {@link IStaticObject} marker to search for (never
	 *            <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching {@link IDomNode} instance
	 *         (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomNodeTester findButton(IStaticObject marker) {

		return findNodes(marker)//
			.withType(DomButton.class)
			.assertOne();
	}

	/**
	 * Searches for the first {@link DomButton} with the given
	 * {@link IStaticObject} marker.
	 *
	 * @param marker
	 *            the {@link IStaticObject} marker to search for (never
	 *            <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching {@link IDomNode} instance
	 *         (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if no matching {@link DomButton} was found
	 */
	default DomNodeTester findFirstButton(IStaticObject marker) {

		return findNodes(marker)//
			.withType(DomButton.class)
			.assertSome()
			.iterator()
			.next();
	}

	/**
	 * Searches for a {@link IDomInputNode} with the given {@link IStaticObject}
	 * marker.
	 *
	 * @param marker
	 *            the {@link IStaticObject} marker to search for (never
	 *            <i>null</i>)
	 * @return a {@link DomNodeTester} of the matching {@link IDomNode} instance
	 *         (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomNodeTester findInput(IStaticObject marker) {

		return findNodes(marker)//
			.withType(IDomInputNode.class)
			.assertOne();
	}

	/**
	 * Searches for a {@link DomDayInput} with the given {@link IStaticObject}
	 * marker.
	 *
	 * @param marker
	 *            the {@link IStaticObject} marker to search for (never
	 *            <i>null</i>)
	 * @return a {@link DomDayInputTester} of the matching {@link IDomNode}
	 *         instance (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomDayInputTester findDayInput(IStaticObject marker) {

		return findNodes(marker)//
			.withType(DomDayInput.class)
			.assertOne(node -> new DomDayInputTester(getEngine(), node));
	}

	/**
	 * Searches for a {@link DomDayTimeInput} with the given
	 * {@link IStaticObject} marker.
	 *
	 * @param marker
	 *            the {@link IStaticObject} marker to search for (never
	 *            <i>null</i>)
	 * @return a {@link DomDayTimeInputTester} of the matching {@link IDomNode}
	 *         instance (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomDayTimeInputTester findDayTimeInput(IStaticObject marker) {

		return findNodes(marker)//
			.withType(DomDayTimeInput.class)
			.assertOne(node -> new DomDayTimeInputTester(getEngine(), node));
	}

	/**
	 * Searches for a {@link DomCheckbox} with the given {@link IStaticObject}
	 * marker.
	 *
	 * @param marker
	 *            the {@link IStaticObject} marker to search for (never
	 *            <i>null</i>)
	 * @return a {@link DomCheckboxTester} of the matching {@link IDomNode}
	 *         instance (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching {@link IDomNode}, or none
	 *             at all
	 */
	default DomCheckboxTester findCheckbox(IStaticObject marker) {

		return findNodes(marker)//
			.withType(DomCheckbox.class)
			.assertOne(node -> new DomCheckboxTester(getEngine(), node));
	}

	/**
	 * Searches for a {@link DomTable} with the given {@link IStaticObject}
	 * marker.
	 * <p>
	 * Same as {@link #findTable()} but ignores all nodes without the marker.
	 */
	default DomTableTester findTable(IStaticObject marker) {

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
	 * given {@link IStaticObject} marker, and returns it as an
	 * {@link DomSelectTester}.
	 *
	 * @param marker
	 *            the marker to search for (never <i>null</i>)
	 * @return a {@link DomSelectTester} of the single matching child node
	 *         (never <i>null</i>)
	 * @throws DomNodeAssertionError
	 *             if there is more than one matching child node, or none at all
	 */
	default <T> DomSelectTester<T> findSelect(IStaticObject marker) {

		return findNode(marker)//
			.findNodes(DomSelect.class)
			.assertOne(node -> new DomSelectTester<>(getEngine(), node));
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

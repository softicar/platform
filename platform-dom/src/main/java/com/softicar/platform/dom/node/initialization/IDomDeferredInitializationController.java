package com.softicar.platform.dom.node.initialization;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.node.IDomNode;

/**
 * Provides the "deferred node initialization" feature to {@link IDomDocument}.
 *
 * @author Alexander Schmidt
 */
public interface IDomDeferredInitializationController {

	/**
	 * Adds the given deferred initializer (i.e. an initialization callback) for
	 * the given {@link IDomNode}.
	 * <p>
	 * The initializer is executed when the {@link IDomNode} obtains a
	 * transitive parent connection to the {@link DomBody}.
	 *
	 * @param node
	 *            the {@link IDomNode} that must be transitively appended to the
	 *            {@link DomBody}, for the initializer to be executed (never
	 *            <i>null</i>)
	 * @param initializer
	 *            the initialization callback to execute as soon as the
	 *            {@link IDomNode} is transitively appended to the
	 *            {@link DomBody} (never <i>null</i>)
	 */
	void addDeferredInitializer(IDomNode node, INullaryVoidFunction initializer);

	/**
	 * Adds the given {@link IDomNode} to the queue of recently appended nodes.
	 *
	 * @param node
	 *            the appended {@link IDomNode} (never <i>null</i>)
	 */
	void queueAppended(IDomNode node);

	/**
	 * Processes the queue of recently appended nodes, and execute initializers
	 * if appropriate.
	 * <p>
	 * Initializers are executed in the order in which the corresponding nodes
	 * were appended to any parent. This order might differ from the order in
	 * which the respective nodes obtained a transitive parent connection to the
	 * {@link DomBody}.
	 * <p>
	 * Clears the queue afterwards.
	 */
	void handleAllAppended();

	/**
	 * Resets all internal states.
	 */
	void clear();
}

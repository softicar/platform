package com.softicar.platform.dom.document;

import com.softicar.platform.common.container.map.instance.ClassInstanceMap;
import com.softicar.platform.dom.attribute.IDomAttributeRegistry;
import com.softicar.platform.dom.document.marker.IDomDocumentMarkerHolder;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.DomNode;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBus;
import java.lang.ref.WeakReference;

/**
 * Interface of the W3C DOM Document.
 *
 * @author Oliver Richers
 */
public interface IDomDocument extends IDomDocumentMarkerHolder {

	// -------------------------------- node methods -------------------------------- //

	/**
	 * Returns the node with the specified id.
	 * <p>
	 * This might return <i>null</i> if the node was collected by the garbage
	 * collector.
	 *
	 * @param nodeId
	 *            the id of the node
	 * @return the node with the specified id or <i>null</i> if no such node
	 *         exists
	 */
	IDomNode getNode(int nodeId);

	/**
	 * Returns the node with the specified id.
	 *
	 * @param nodeId
	 *            in the form 'n<id>' (e.g. n232)
	 * @return the node object with the specified id or <i>null</i> if no
	 *         matching node exists
	 */
	IDomNode getNode(String nodeId);

	/**
	 * Registers the specified node with this {@link IDomDocument} and allocates
	 * a unique ID.
	 * <p>
	 * This method is automatically called by the constructor of {@link DomNode}
	 * and may only be called once per node instance. Each {@link IDomNode} must
	 * be registered using this method before it may be used, e.g. inserted into
	 * the node tree.
	 *
	 * @param node
	 *            the node to register
	 * @return the allocated and unique ID of given node
	 */
	int registerNode(IDomNode node);

	/**
	 * This returns the number of registered and not collected {@link IDomNode}
	 * objects.
	 *
	 * @return number of active {@link IDomNode} objects
	 */
	int getActiveNodeCount();

	/**
	 * Removes all collected {@link IDomNode} objects from the internal
	 * registry.
	 * <p>
	 * The {@link IDomNode} objects, registered via the
	 * {@link #registerNode(IDomNode)} method are held using instances of
	 * {@link WeakReference}. If a registered node is not referenced by any
	 * strong reference anymore and thus collected by the garbage collector,
	 * this method removes the respective reference from the internal registry.
	 */
	void removeCollectedNodes();

	// -------------------------------- event -------------------------------- //

	/**
	 * Sets the current event.
	 * <p>
	 * This is an internal method and should not be called by normal program
	 * code.
	 *
	 * @param event
	 *            the current event
	 */
	void setCurrentEvent(IDomEvent event);

	/**
	 * Returns the current event.
	 *
	 * @return the current event object or <i>null</i>
	 */
	IDomEvent getCurrentEvent();

	// -------------------------------- special -------------------------------- //

	/**
	 * Returns the document head associated with this {@link IDomDocument}.
	 *
	 * @return the document head (never <i>null</i>)
	 */
	DomHead getHead();

	/**
	 * Returns the document body associated with this {@link IDomDocument}.
	 *
	 * @return the document body (never <i>null</i>)
	 */
	DomBody getBody();

	/**
	 * Returns the instance of the {@link IDomRefreshBus} for this
	 * {@link IDomDocument}.
	 *
	 * @return the refresh bus (never <i>null</i>)
	 */
	IDomRefreshBus getRefreshBus();

	/**
	 * Returns the data map for custom data.
	 *
	 * @return the data map (never <i>null</i>)
	 */
	ClassInstanceMap<Object> getDataMap();

	/**
	 * Returns the {@link IDomEngine} associated with this {@link IDomDocument}.
	 *
	 * @return the associated {@link IDomEngine}, never <i>null</i>
	 */
	IDomEngine getEngine();

	/**
	 * Returns a reference to the internal {@link IDomAttributeRegistry}.
	 * <p>
	 * This registry is used to enabled the {@link IDomNode} objects to share
	 * common node attributes and thus to reduce memory consumption.
	 *
	 * @return the internal {@link IDomAttributeRegistry}
	 */
	IDomAttributeRegistry getAttributeRegistry();
}

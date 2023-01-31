package com.softicar.platform.dom.document;

import com.softicar.platform.common.container.map.instance.ClassInstanceMap;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.dom.attribute.IDomAttributeRegistry;
import com.softicar.platform.dom.document.marker.IDomDocumentMarkerHolder;
import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.elements.DomScript;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.AbstractDomNode;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.node.initialization.IDomDeferredInitializationController;
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
	 * Registers the given {@link IDomNode} with this {@link IDomDocument} and
	 * allocates a unique ID for it.
	 * <p>
	 * Each {@link IDomNode} instance must be registered by calling this method,
	 * before it may be added to this {@link IDomDocument}, i.e. appended to the
	 * {@link DomBody}, {@link DomHead} or one of their children. Usually, this
	 * method needs not be called directly, because it is called by the
	 * constructor of {@link AbstractDomNode} automatically.
	 *
	 * @param node
	 *            the {@link IDomNode} to register (never <i>null</i>)
	 * @return the allocated and unique ID for the given {@link IDomNode}
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

	/**
	 * Returns the controller for deferred node initialization.
	 *
	 * @return the {@link IDomDeferredInitializationController} (never
	 *         <i>null</i>)
	 */
	IDomDeferredInitializationController getDeferredInitializationController();

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

	/**
	 * Adds a {@link DomScript} element to the {@link DomHead} if none with the
	 * same URL exists yet.
	 * <p>
	 * The source URL is compared to existing {@link DomScript} elements
	 * literally, so care must be taken to not add sightly different but
	 * effectively equivalent {@link DomScript} entries, pointing to the same
	 * resource. For example, the URLs <code>http://example.org/foo</code> and
	 * <code>http://example.org/foo/</code> will be considered to be different
	 * even though they point to the same resource.
	 * <p>
	 * The check for existing {@link DomScript} elements is efficient, so
	 * calling the method from the constructor of a {@link DomElement} is fine.
	 *
	 * @param scriptUrl
	 *            the script URL (never <i>null</i>)
	 * @param mimeType
	 *            the script {@link MimeType} (never <i>null</i>)
	 */
	void registerScript(String scriptUrl, MimeType mimeType);
}

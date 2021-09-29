package com.softicar.platform.dom.document;

import com.softicar.platform.common.container.map.instance.ClassInstanceMap;
import com.softicar.platform.common.container.map.weak.IWeakMap;
import com.softicar.platform.common.container.map.weak.WeakIntHashMap;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.attribute.DomAttributeRegistry;
import com.softicar.platform.dom.attribute.IDomAttributeRegistry;
import com.softicar.platform.dom.document.marker.DomDocumentMarkerHolder;
import com.softicar.platform.dom.document.marker.IDomDocumentMarkerHolder;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.refresh.bus.DomRefreshBus;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBus;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import java.util.Collection;

/**
 * Abstract implementation of {@link IDomDocument}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDomDocument implements IDomDocument {

	private final DomNodeIdGenerator nodeIdGenerator;
	private final IDomAttributeRegistry attributeRegistry;
	private final IWeakMap<Integer, IDomNode> activeNodes;
	private final IDomRefreshBus refreshBus;
	private final ClassInstanceMap<Object> dataMap;
	private final IDomDocumentMarkerHolder markerHolder;
	private final DomHead head;
	private final DomBody body;
	private int maximumExistingNodeCount;
	private IDomEvent currentEvent;

	public AbstractDomDocument() {

		this.nodeIdGenerator = new DomNodeIdGenerator();
		this.attributeRegistry = new DomAttributeRegistry(this);
		this.activeNodes = new WeakIntHashMap<>(50);
		this.refreshBus = new DomRefreshBus();
		this.dataMap = new ClassInstanceMap<>();
		this.markerHolder = new DomDocumentMarkerHolder(this);
		this.head = new DomHead(this);
		this.body = new DomBody(this);
		this.maximumExistingNodeCount = 0;
		this.currentEvent = null;
	}

	/**
	 * Defines the maximum number of existing {@link IDomNode} instances.
	 * <p>
	 * If the limit is exceeded, {@link #registerNode(IDomNode)} will throw an
	 * exception.
	 * <p>
	 * The default is <i>0</i>, which means no limit.
	 *
	 * @param count
	 *            a positive integer defining the maximum number of existing
	 *            {@link IDomNode} instances, or <i>0</i> to disable the limit
	 */
	public void setMaximumExistingNodeCount(int count) {

		this.maximumExistingNodeCount = count;
	}

	// -------------------------------- node methods -------------------------------- //

	@Override
	public final IDomNode getNode(int nodeId) {

		return activeNodes.get(nodeId);
	}

	@Override
	public final IDomNode getNode(String nodeIdString) {

		// removing the leading 'n' from the string
		int nodeId = Integer.parseInt(nodeIdString.substring(1));

		return getNode(nodeId);
	}

	@Override
	public final int registerNode(IDomNode node) {

		checkForTooManyExistingNodes();

		int nodeId = nodeIdGenerator.generateId();
		activeNodes.put(nodeId, node);

		// automatically add node to the refresh bus
		if (node instanceof IDomRefreshBusListener) {
			refreshBus.addListener((IDomRefreshBusListener) node);
		}

		return nodeId;
	}

	@Override
	public final int getActiveNodeCount() {

		return activeNodes.size();
	}

	@Override
	public final void removeCollectedNodes() {

		activeNodes.collect();
	}

	// -------------------------------- event -------------------------------- //

	@Override
	public void setCurrentEvent(IDomEvent currentEvent) {

		this.currentEvent = currentEvent;
	}

	@Override
	public IDomEvent getCurrentEvent() {

		return currentEvent;
	}

	// -------------------------------- marker -------------------------------- //

	@Override
	public void setMarker(IDomNode node, IStaticObject marker) {

		markerHolder.setMarker(node, marker);
	}

	@Override
	public boolean hasMarker(IDomNode node, IStaticObject marker) {

		return markerHolder.hasMarker(node, marker);
	}

	@Override
	public Collection<IDomNode> getNodesWithMarker(IStaticObject marker) {

		return markerHolder.getNodesWithMarker(marker);
	}

	@Override
	public Collection<IStaticObject> getMarkers(IDomNode node) {

		return markerHolder.getMarkers(node);
	}

	// -------------------------------- special -------------------------------- //

	@Override
	public DomHead getHead() {

		return head;
	}

	@Override
	public DomBody getBody() {

		return body;
	}

	@Override
	public IDomRefreshBus getRefreshBus() {

		return refreshBus;
	}

	@Override
	public ClassInstanceMap<Object> getDataMap() {

		return dataMap;
	}

	@Override
	public final IDomAttributeRegistry getAttributeRegistry() {

		return attributeRegistry;
	}

	// -------------------------------- private -------------------------------- //

	private void checkForTooManyExistingNodes() {

		if (maximumExistingNodeCount > 0 && activeNodes.size() > maximumExistingNodeCount) {
			throw new SofticarUserException(DomI18n.MEMORY_CONSUMPTION_IS_TOO_HIGH.concat(" ").concat(DomI18n.PLEASE_CONTACT_THE_SUPPORT_TEAM));
		}
	}
}

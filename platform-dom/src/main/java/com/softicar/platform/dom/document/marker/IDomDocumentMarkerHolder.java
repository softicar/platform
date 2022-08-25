package com.softicar.platform.dom.document.marker;

import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Collection;

/**
 * Holds references to {@link ITestMarker} based {@link IDomNode}s.
 * <p>
 * Implementations must check whether the DOM framework is in test mode. If the
 * framework is <b>not</b> in test mode, the implementation must serve as a
 * dummy. That is, setter methods must not have any effect, and getter methods
 * must return default values.
 *
 * @author Alexander Schmidt
 */
public interface IDomDocumentMarkerHolder {

	/**
	 * Adds an {@link ITestMarker} marker to the given {@link IDomNode}.
	 *
	 * @param node
	 *            the node (never <i>null</i>)
	 * @param marker
	 *            the marker to add (never <i>null</i>)
	 * @throws UnsupportedOperationException
	 *             if the {@link IDomDocument} does not support marking of nodes
	 */
	void addMarker(IDomNode node, ITestMarker marker);

	/**
	 * Checks whether the given {@link IDomNode} has all given
	 * {@link ITestMarker} markers.
	 * <p>
	 * Returns <i>false</i> if the given {@link IDomNode} is not appended to the
	 * {@link DomDocument} (that is, if the {@link DomBody} is not among the
	 * transitive parents of the given {@link IDomNode}).
	 * <p>
	 * If the DOM framework is <b>not</b> in test mode, <i>false</i> is
	 * returned.
	 * <p>
	 * TODO PLAT-568 The method name should be pluralized <i>hasMarkers</i>.
	 *
	 * @param node
	 *            the {@link IDomNode} to check (never <i>null</i>)
	 * @param markers
	 *            the {@link ITestMarker} markers to check for (never
	 *            <i>null</i>)
	 * @return <i>true</i> if the {@link IDomNode} has all given
	 *         {@link ITestMarker} markers; <i>false</i> otherwise
	 */
	boolean hasMarker(IDomNode node, ITestMarker...markers);

	/**
	 * Returns a {@link Collection} of all {@link IDomNode} instances in the
	 * {@link DomDocument} which have the given {@link ITestMarker} marker.
	 * <p>
	 * If the DOM framework is <b>not</b> in test mode, an empty
	 * {@link Collection} is returned.
	 *
	 * @param marker
	 *            the {@link ITestMarker} marker to find nodes for (never
	 *            <i>null</i>)
	 * @return all {@link IDomNode} instances which possess the given
	 *         {@link ITestMarker} marker (never <i>null</i>)
	 */
	Collection<IDomNode> getNodesWithMarker(ITestMarker marker);

	/**
	 * Returns all {@link ITestMarker} markers that the given {@link IDomNode}
	 * possesses.
	 * <p>
	 * Returns an empty {@link Collection} the given {@link IDomNode} is not
	 * appended to the {@link DomDocument} (that is, if the {@link DomBody} is
	 * not among the transitive parents of the given {@link IDomNode}).
	 * <p>
	 * If the DOM framework is <b>not</b> in test mode, an empty
	 * {@link Collection} is returned.
	 *
	 * @param node
	 *            the {@link IDomNode} to check (never <i>null</i>)
	 * @return all {@link ITestMarker} markers of the given {@link IDomNode}
	 *         (never <i>null</i>)
	 */
	Collection<ITestMarker> getMarkers(IDomNode node);
}

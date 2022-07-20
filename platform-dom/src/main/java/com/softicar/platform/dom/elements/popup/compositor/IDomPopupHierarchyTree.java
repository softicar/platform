package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.IDomNode;
import java.util.List;
import java.util.Optional;

/**
 * A tree that contains parent-child relations between {@link DomPopup}
 * elements.
 * <p>
 * Those relations:
 * <ul>
 * <li>Merely express which {@link DomPopup} was opened <i>from</i> which other
 * {@link DomPopup}.</li>
 * <li>Emerge from user-induced event handling.</li>
 * <li>Are <b>not</b> about the structure of the DOM tree, in which a
 * {@link DomPopup} would never be an actual child element of another
 * {@link DomPopup}.</li>
 * </ul>
 * <p>
 * More formally, a {@link DomPopup} p1 is assumed to be the parent of another
 * {@link DomPopup} p2 if p2 was opened while handling an {@link IDomEvent} that
 * emerged from an {@link IDomNode} in p1.
 * <p>
 * For example, if p1 contains a button that is clicked to open p2, we state
 * that p2 was opened <i>from</i> p1, and that p2 is a child of p1. We also
 * state that, in this tree, p2 has a greater depth than p1.
 *
 * @author Alexander Schmidt
 */
public interface IDomPopupHierarchyTree {

	/**
	 * Determines all transitive child {@link DomPopup} instances of the given
	 * parent {@link DomPopup}.
	 * <p>
	 * The elements in the returned {@link List} will be ordered according to a
	 * post-order traversal of this tree. That is, in the returned {@link List},
	 * {@link DomPopup} instances with a greater depth will occur before
	 * {@link DomPopup} instances with a smaller depth.
	 * <p>
	 * Returns an empty {@link List} if the given {@link DomPopup} is not a
	 * parent element in this tree.
	 *
	 * @param parent
	 *            the parent {@link DomPopup} (never <i>null</i>)
	 * @return all child {@link DomPopup} elements (never <i>null</i>)
	 */
	List<DomPopup> getAllChildPopups(DomPopup parent);

	/**
	 * Returns the {@link DomPopup} from which the given {@link DomPopup} was
	 * spawned.
	 * <p>
	 * Returns {@link Optional#empty()} if the given {@link DomPopup} was not
	 * spawned from within another {@link DomPopup}, or if the given
	 * {@link DomPopup} is not a child in this tree.
	 *
	 * @param child
	 *            the child {@link DomPopup} (never <i>null</i>)
	 * @return the parent {@link DomPopup}
	 */
	Optional<DomPopup> getParentPopup(DomPopup child);
}

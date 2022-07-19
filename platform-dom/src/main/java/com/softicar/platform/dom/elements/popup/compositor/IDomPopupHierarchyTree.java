package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.List;
import java.util.Optional;

/**
 * A tree that contains parent-child nesting relations between {@link DomPopup}
 * elements.
 *
 * @author Alexander Schmidt
 */
public interface IDomPopupHierarchyTree {

	/**
	 * Determines all transitive child {@link DomPopup} elements of the given
	 * parent {@link DomPopup}.
	 * <p>
	 * The returned {@link List} will be ordered according to descending depth
	 * in the hierarchy tree. That is, deeply-nested children will occur before
	 * less deeply-nested children.
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

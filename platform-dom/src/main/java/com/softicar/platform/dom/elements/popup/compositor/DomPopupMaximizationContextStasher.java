package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Temporarily removes the children from an
 * {@link IDomPopupMaximizationContext}, keeps references to them, and allows to
 * re-append them later-on.
 *
 * @author Alexander Schmidt
 */
class DomPopupMaximizationContextStasher {

	private final Deque<List<IDomNode>> stash;

	/**
	 * Constructs a new {@link DomPopupMaximizationContextStasher}.
	 */
	public DomPopupMaximizationContextStasher() {

		this.stash = new ArrayDeque<>();
	}

	/**
	 * Disappends all children from the given
	 * {@link IDomPopupMaximizationContext}, and pushes them to an internal
	 * stash.
	 * <p>
	 * The children can be restored later-on, via
	 * {@link #unstash(IDomPopupMaximizationContext)}.
	 *
	 * @param maximizationContext
	 *            the parent element to temporarily remove children from (never
	 *            <i>null</i>)
	 */
	public void stash(IDomPopupMaximizationContext maximizationContext) {

		Objects.requireNonNull(maximizationContext);
		stash.push(maximizationContext.getChildren());
		maximizationContext.removeChildren();
	}

	/**
	 * Re-appends the most recently stashed children to the given
	 * {@link IDomPopupMaximizationContext}.
	 *
	 * @param maximizationContext
	 *            the parent element to restore children to (never <i>null</i>)
	 * @throws NoSuchElementException
	 *             if the internal stash is empty
	 */
	public void unstash(IDomPopupMaximizationContext maximizationContext) {

		Objects.requireNonNull(maximizationContext);
		stash.pop().forEach(maximizationContext::appendChild);
	}

	/**
	 * Discards the entire content of the stash.
	 */
	public void clear() {

		stash.clear();
	}
}

package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.modal.DomModalPopupBackdrop;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Keeps track of {@link DomModalPopupBackdrop} elements and their association
 * with the respective {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
class DomPopupBackdropTracker {

	private final Map<DomPopup, DomModalPopupBackdrop> map;
	private final List<DomModalPopupBackdrop> stack;

	/**
	 * Constructs a new {@link DomPopupBackdropTracker}.
	 */
	public DomPopupBackdropTracker() {

		this.map = new WeakHashMap<>();
		this.stack = new ArrayList<>();
	}

	/**
	 * Adds the given {@link DomModalPopupBackdrop} for the given
	 * {@link DomPopup}.
	 * <p>
	 * Does <b>not</b> append the {@link DomModalPopupBackdrop} to the
	 * {@link CurrentDomDocument}.
	 *
	 * @param popup
	 *            the {@link DomPopup} to which the
	 *            {@link DomModalPopupBackdrop} belongs (never <i>null</i>)
	 * @param backdrop
	 *            the {@link DomModalPopupBackdrop} to add (never <i>null</i>)
	 */
	public void add(DomPopup popup, DomModalPopupBackdrop backdrop) {

		Objects.requireNonNull(popup);
		Objects.requireNonNull(backdrop);
		map.put(popup, backdrop);
		addToStack(backdrop);
	}

	/**
	 * Removes the {@link DomModalPopupBackdrop} of the given {@link DomPopup}.
	 * <p>
	 * Does <b>not</b> disappend the {@link DomModalPopupBackdrop} from the
	 * {@link CurrentDomDocument}.
	 * <p>
	 * If there is no known {@link DomModalPopupBackdrop} for the given
	 * {@link DomPopup}, nothing will happen, and {@link Optional#empty()} will
	 * be returned.
	 *
	 * @param popup
	 *            the {@link DomPopup} to which the
	 *            {@link DomModalPopupBackdrop} belongs (never <i>null</i>)
	 * @return the removed {@link DomModalPopupBackdrop}
	 */
	public Optional<DomModalPopupBackdrop> remove(DomPopup popup) {

		Objects.requireNonNull(popup);
		var backdrop = Optional.ofNullable(map.remove(popup));
		backdrop.ifPresent(this::removeFromStack);
		return backdrop;
	}

	/**
	 * Removes all {@link DomModalPopupBackdrop} elements.
	 * <p>
	 * Does <b>not</b> disappend them from the {@link CurrentDomDocument}.
	 * <p>
	 * The most recently-added {@link DomModalPopupBackdrop} will be the first
	 * element in the returned {@link List}.
	 *
	 * @return the removed {@link DomModalPopupBackdrop} elements (never
	 *         <i>null</i>)
	 */
	public List<DomModalPopupBackdrop> removeAll() {

		var backdrops = new ArrayList<>(getAllBackdrops());
		stack.clear();
		map.clear();
		return backdrops;
	}

	/**
	 * Returns all {@link DomModalPopupBackdrop} elements.
	 * <p>
	 * The most recently-added {@link DomModalPopupBackdrop} will be the first
	 * element in the returned {@link List}.
	 *
	 * @return all {@link DomModalPopupBackdrop} elements (never <i>null</i>)
	 */
	public List<DomModalPopupBackdrop> getAllBackdrops() {

		var reversedStack = new ArrayList<>(stack);
		Collections.reverse(reversedStack);
		return reversedStack;
	}

	/**
	 * Determines whether at least one {@link DomModalPopupBackdrop} is
	 * currently present.
	 *
	 * @return <i>true</i> if any {@link DomModalPopupBackdrop} is currently
	 *         present; <i>false</i> otherwise
	 */
	public boolean isAnyBackdropPresent() {

		return !stack.isEmpty();
	}

	private void addToStack(DomModalPopupBackdrop backdrop) {

		stack.add(backdrop);
	}

	private void removeFromStack(DomModalPopupBackdrop backdrop) {

		if (!stack.remove(backdrop)) {
			throw new IllegalStateException("Backdrop not found.");
		}
	}
}

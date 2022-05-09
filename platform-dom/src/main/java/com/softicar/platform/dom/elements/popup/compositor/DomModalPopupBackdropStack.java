package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.popup.modal.DomModalPopupBackdrop;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Facilitates handling a stack of {@link DomModalPopupBackdrop} nodes.
 *
 * @author Alexander Schmidt
 */
class DomModalPopupBackdropStack {

	private List<DomModalPopupBackdrop> stack;

	/**
	 * Constructs a new {@link DomModalPopupBackdropStack}.
	 */
	public DomModalPopupBackdropStack() {

		this.stack = new ArrayList<>();
	}

	/**
	 * Adds the given {@link DomModalPopupBackdrop} to this
	 * {@link DomModalPopupBackdropStack}.
	 *
	 * @param backdrop
	 *            the {@link DomModalPopupBackdrop} to add (never <i>null</i>)
	 */
	public void add(DomModalPopupBackdrop backdrop) {

		Objects.requireNonNull(backdrop);
		stack.add(backdrop);
	}

	/**
	 * Refreshes the visibility of all {@link DomModalPopupBackdrop} nodes in
	 * this {@link DomModalPopupBackdropStack}.
	 * <p>
	 * Removes non-appended {@link DomModalPopupBackdrop} nodes from the
	 * internal stack, prior to recalculating visibility.
	 */
	public void refreshVisibility() {

		cleanup();
		var reverseStack = createReverseStack();
		reverseStack.forEach(it -> it.removeCssClass(DomCssPseudoClasses.VISIBLE));
		reverseStack//
			.stream()
			.filter(DomModalPopupBackdrop::isAppended)
			.filter(DomModalPopupBackdrop::isVisible)
			.findFirst()
			.ifPresent(it -> it.addCssClass(DomCssPseudoClasses.VISIBLE));
	}

	private void cleanup() {

		this.stack = stack//
			.stream()
			.filter(DomModalPopupBackdrop::isAppended)
			.collect(Collectors.toList());
	}

	private List<DomModalPopupBackdrop> createReverseStack() {

		var reverseStack = new ArrayList<>(stack);
		Collections.reverse(reverseStack);
		return reverseStack;
	}
}

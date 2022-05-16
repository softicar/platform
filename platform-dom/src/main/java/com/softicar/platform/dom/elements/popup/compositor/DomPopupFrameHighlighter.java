package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Highlights or un-highlights the frames of given {@link DomPopup} elements.
 *
 * @author Alexander Schmidt
 */
class DomPopupFrameHighlighter {

	/**
	 * Highlights the frames of the given {@link DomPopup} element.
	 *
	 * @param popups
	 *            the {@link DomPopup} elements to highlight (never <i>null</i>)
	 * @return this
	 */
	public DomPopupFrameHighlighter highlight(List<DomPopup> popups) {

		popups.forEach(this::highlight);
		return this;
	}

	/**
	 * Highlights the frame of the given {@link DomPopup} element.
	 *
	 * @param popup
	 *            the {@link DomPopup} elements to highlight (never <i>null</i>)
	 * @return this
	 */
	public DomPopupFrameHighlighter highlight(DomPopup popup) {

		getFrame(popup).ifPresent(frame -> frame.addCssClass(DomCssPseudoClasses.HIGHLIGHTED));
		return this;
	}

	/**
	 * Un-highlights the frames of the given {@link DomPopup} elements.
	 *
	 * @param popups
	 *            the {@link DomPopup} elements to un-highlight (never
	 *            <i>null</i>)
	 * @return this
	 */
	public DomPopupFrameHighlighter unhighlight(Collection<DomPopup> popups) {

		popups.forEach(this::unhighlight);
		return this;
	}

	/**
	 * Un-highlights the frame of the given {@link DomPopup} element.
	 *
	 * @param popup
	 *            the {@link DomPopup} element to un-highlight (never
	 *            <i>null</i>)
	 * @return this
	 */
	public DomPopupFrameHighlighter unhighlight(DomPopup popup) {

		getFrame(popup).ifPresent(frame -> frame.removeCssClass(DomCssPseudoClasses.HIGHLIGHTED));
		return this;
	}

	private Optional<DomPopupFrame> getFrame(DomPopup popup) {

		return new DomParentNodeFinder<>(DomPopupFrame.class).findClosestParent(popup);
	}
}

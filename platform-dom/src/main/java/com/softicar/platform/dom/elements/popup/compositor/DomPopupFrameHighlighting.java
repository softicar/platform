package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import java.util.List;
import java.util.Optional;

class DomPopupFrameHighlighting {

	public void add(DomPopup popup, List<DomPopup> morePopups) {

		highlightFrame(popup);
		morePopups.forEach(this::highlightFrame);
	}

	public void remove(DomPopup popup, List<DomPopup> morePopups) {

		unhighlightFrame(popup);
		morePopups.forEach(child -> unhighlightFrame(child));
	}

	private void highlightFrame(DomPopup popup) {

		getFrame(popup).ifPresent(frame -> frame.addCssClass(DomCssPseudoClasses.HIGHLIGHTED));
	}

	private void unhighlightFrame(DomPopup popup) {

		getFrame(popup).ifPresent(frame -> frame.removeCssClass(DomCssPseudoClasses.HIGHLIGHTED));
	}

	private Optional<DomPopupFrame> getFrame(DomPopup popup) {

		return new DomParentNodeFetcher<>(DomPopupFrame.class).getClosestParent(popup);
	}
}

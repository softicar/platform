package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;

/**
 * The head element of a {@link DomPopupFrame}.
 * <p>
 * Can comprise caption, sub-caption and control elements.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
class DomPopupFrameHeader extends DomDiv {

	private final CaptionHolder captionHolder;
	private final DomPopupFrameCloseButton closeButton;

	public DomPopupFrameHeader(DomPopupFrame frame) {

		this.captionHolder = new CaptionHolder();
		this.closeButton = new DomPopupFrameCloseButton(frame);

		setCssClass(DomElementsCssClasses.DOM_POPUP_FRAME_HEADER);
		appendChild(captionHolder);
		appendChild(closeButton);
	}

	public void setCaption(IDisplayString text) {

		captionHolder.captionDiv.removeChildren();
		captionHolder.captionDiv.appendText(text);
	}

	public void setSubCaption(IDisplayString text) {

		captionHolder.subCaptionDiv.removeChildren();
		captionHolder.subCaptionDiv.appendText(text);
	}

	public DomPopupFrameCloseButton getCloseButton() {

		return closeButton;
	}

	private static class CaptionHolder extends DomDiv {

		private final DomDiv captionDiv;
		private final DomDiv subCaptionDiv;

		public CaptionHolder() {

			this.captionDiv = new DomDiv();
			this.captionDiv.setCssClass(DomElementsCssClasses.DOM_POPUP_CAPTION);
			this.subCaptionDiv = new DomDiv();
			this.subCaptionDiv.setCssClass(DomElementsCssClasses.DOM_POPUP_SUB_CAPTION);

			appendChild(captionDiv);
			appendChild(subCaptionDiv);
		}
	}
}

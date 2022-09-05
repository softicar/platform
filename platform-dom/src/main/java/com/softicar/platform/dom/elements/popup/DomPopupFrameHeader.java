package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;

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

	public DomPopupFrameHeader(INullaryVoidFunction closeCallback) {

		this.captionHolder = new CaptionHolder();
		this.closeButton = new DomPopupFrameCloseButton(closeCallback);

		setCssClass(DomCssClasses.DOM_POPUP_FRAME_HEADER);
		addMarker(DomTestMarker.POPUP_FRAME_HEADER);
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
			this.captionDiv.setCssClass(DomCssClasses.DOM_POPUP_CAPTION);
			this.subCaptionDiv = new DomDiv();
			this.subCaptionDiv.setCssClass(DomCssClasses.DOM_POPUP_SUB_CAPTION);

			appendChild(captionDiv);
			appendChild(subCaptionDiv);
		}
	}
}

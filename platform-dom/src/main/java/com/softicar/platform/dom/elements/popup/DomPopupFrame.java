package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.popup.compositor.CurrentDomPopupCompositor;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEscapeKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.styles.CssPosition;
import java.util.Collections;

/**
 * A frame for {@link DomPopup} objects.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomPopupFrame extends DomDiv implements IDomPopupFrame, IDomEscapeKeyEventHandler {

	private final DomPopupFrameHeader header;
	private final DomPopup popup;
	private boolean initialized;

	public DomPopupFrame(DomPopup popup) {

		this.header = new DomPopupFrameHeader(this);
		this.popup = popup;
		this.initialized = false;

		setCssClass(DomElementsCssClasses.DOM_POPUP_FRAME);
		makeDraggable(CssPosition.ABSOLUTE, header);
		setupEscapeHandler();
	}

	@Override
	public void handleEscapeKey(IDomEvent event) {

		closePopup();
	}

	@Override
	public void closePopup() {

		CurrentDomPopupCompositor.get().close(popup);
	}

	public void setCaption(IDisplayString text) {

		header.setCaption(text);
	}

	public void setSubCaption(IDisplayString text) {

		header.setSubCaption(text);
	}

	public void initialize(boolean displayHeader) {

		if (!initialized) {
			if (displayHeader) {
				appendChild(header);
			}
			appendChild(popup);
			this.initialized = true;
		}
	}

	private void setupEscapeHandler() {

		setTabIndex(0);
		getDomEngine().setFireOnKeyUp(this, DomEventType.ESCAPE, true);
		getDomEngine().setCssClassOnKeyDown(this, DomEventType.ESCAPE, header.getCloseButton(), Collections.singleton(DomCssPseudoClasses.ACTIVE));
	}
}

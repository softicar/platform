package com.softicar.platform.dom.elements.popup;

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
 * A frame for {@link DomPopup} elements.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomPopupFrame extends DomDiv implements IDomPopupFrame, IDomEscapeKeyEventHandler {

	private final DomPopup popup;
	private final DomPopupFrameHeader header;

	public DomPopupFrame(DomPopup popup) {

		this.popup = popup;
		this.header = new DomPopupFrameHeader(this);

		setCssClass(DomElementsCssClasses.DOM_POPUP_FRAME);
		setupEscapeHandler();

		var configuration = popup.getConfiguration();
		if (configuration.getDisplayMode().hasHeader()) {
			makeDraggable(CssPosition.ABSOLUTE, header);
			appendChild(header);
			refreshCaptions();
		}
		appendChild(popup);

		configuration.getFrameMarkers().forEach(this::setMarker);
	}

	@Override
	public void handleEscapeKey(IDomEvent event) {

		closePopup();
	}

	@Override
	public void closePopup() {

		CurrentDomPopupCompositor.get().close(popup);
	}

	public void refreshCaptions() {

		var configuration = popup.getConfiguration();
		header.setCaption(configuration.getCaption());
		header.setSubCaption(configuration.getSubCaption());
	}

	private void setupEscapeHandler() {

		setTabIndex(0);
		getDomEngine().setFireOnKeyUp(this, DomEventType.ESCAPE, true);
		getDomEngine().setCssClassOnKeyDown(this, DomEventType.ESCAPE, header.getCloseButton(), Collections.singleton(DomCssPseudoClasses.ACTIVE));
	}
}

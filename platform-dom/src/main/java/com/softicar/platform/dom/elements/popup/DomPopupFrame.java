package com.softicar.platform.dom.elements.popup;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.compositor.CurrentDomPopupCompositor;
import com.softicar.platform.dom.elements.popup.compositor.IDomPopupContext;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEscapeKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Collections;
import java.util.Objects;

/**
 * A frame for {@link DomPopup} elements.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomPopupFrame extends DomDiv implements IDomPopupFrame, IDomEscapeKeyEventHandler {

	private final DomPopup popup;
	private final DomPopupFrameHeader header;

	public DomPopupFrame(DomPopup popup, IDomPopupContext context) {

		Objects.requireNonNull(popup);
		Objects.requireNonNull(context);

		this.popup = popup;
		this.header = new DomPopupFrameHeader(this::closePopup);

		setCssClass(DomCssClasses.DOM_POPUP_FRAME);
		addMarker(DomTestMarker.POPUP_FRAME);
		setupEscapeHandler();

		var configuration = popup.getConfiguration();

		if (configuration.getDisplayMode().isMaximized()) {
			addCssClass(DomCssPseudoClasses.MAXIMIZED);
		} else {
			getDomEngine()//
				.makeDraggable(this, header)
				.setDragLimitNode(this, context);
		}

		if (configuration.getDisplayMode().isCompact()) {
			popup.addCssClass(DomCssPseudoClasses.COMPACT);
		}

		if (configuration.getDisplayMode().hasHeader()) {
			appendChild(header);
			refreshCaptions();
		}
		appendChild(popup);

		configuration.getFrameMarkers().forEach(this::addMarker);
	}

	@Override
	public void handleEscapeKey(IDomEvent event) {

		closePopup();
	}

	public void refreshCaptions() {

		var configuration = popup.getConfiguration();
		header.setCaption(configuration.getCaption().orElse(null));
		header.setSubCaption(configuration.getSubCaption().orElse(null));
	}

	private void setupEscapeHandler() {

		setTabIndex(0);
		getDomEngine().setFireOnKeyUp(this, DomEventType.ESCAPE, true);
		getDomEngine().setCssClassOnKeyDown(this, DomEventType.ESCAPE, header.getCloseButton(), Collections.singleton(DomCssPseudoClasses.ACTIVE));
	}

	private void closePopup() {

		CurrentDomPopupCompositor.get().closeInteractively(popup);
	}
}

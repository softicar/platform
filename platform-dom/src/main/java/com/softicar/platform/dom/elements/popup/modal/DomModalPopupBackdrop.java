package com.softicar.platform.dom.elements.popup.modal;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomContextMenuEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Objects;

/**
 * A background element that is displayed directly below (in terms of z-index) a
 * modal {@link DomPopup}.
 * <p>
 * Can be used to intercept mouse clicks on nodes outside of a modal
 * {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public class DomModalPopupBackdrop extends DomDiv implements IDomClickEventHandler, IDomContextMenuEventHandler {

	private final INullaryVoidFunction clickHandler;
	private final boolean visible;

	/**
	 * Constructs a new {@link DomModalPopupBackdrop}.
	 *
	 * @param clickHandler
	 *            the callback to be executed when this element is clicked
	 *            (never <i>null</i>)
	 * @param visible
	 *            <i>true</i> if this element shall be visible; <i>false</i>
	 *            otherwise
	 */
	public DomModalPopupBackdrop(INullaryVoidFunction clickHandler, boolean visible) {

		this.clickHandler = Objects.requireNonNull(clickHandler);
		this.visible = visible;
		setCssClass(DomCssClasses.DOM_MODAL_POPUP_BACKDROP);
		addMarker(DomTestMarker.POPUP_BACKDROP);
	}

	@Override
	public void handleClick(IDomEvent event) {

		clickHandler.apply();
	}

	@Override
	public void handleContextMenu(IDomEvent event) {

		clickHandler.apply();
	}

	public boolean isVisible() {

		return visible;
	}
}

package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomContextMenuEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Objects;

/**
 * A background element that is displayed directly below (in terms of z-index) a
 * custom modal dialog.
 * <p>
 * Can be used to intercept mouse clicks on nodes outside of the custom modal
 * dialog.
 *
 * @author Alexander Schmidt
 */
public class DomModalDialogBackdrop extends DomDiv implements IDomClickEventHandler, IDomContextMenuEventHandler {

	private final INullaryVoidFunction clickHandler;

	/**
	 * Constructs a new {@link DomModalDialogBackdrop}.
	 *
	 * @param clickHandler
	 *            the callback to be executed when this element is clicked
	 *            (never <i>null</i>)
	 */
	public DomModalDialogBackdrop(INullaryVoidFunction clickHandler) {

		this.clickHandler = Objects.requireNonNull(clickHandler);
		setCssClass(DomElementsCssClasses.DOM_MODAL_DIALOG_BACKDROP);
		addMarker(DomModalDialogMarker.BACKDROP);
	}

	@Override
	public void handleClick(IDomEvent event) {

		clickHandler.apply();
	}

	@Override
	public void handleContextMenu(IDomEvent event) {

		clickHandler.apply();
	}
}

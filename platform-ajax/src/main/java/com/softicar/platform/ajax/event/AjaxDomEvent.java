package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.request.AjaxRequestMessage;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomModifier;
import com.softicar.platform.dom.event.DomRect;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.IDomNode;

/**
 * The AJAX implementation of {@link IDomEvent}.
 *
 * @author Oliver Richers
 */
public class AjaxDomEvent implements IDomEvent {

	private final IDomDocument document;
	private final AjaxRequestMessage message;

	/**
	 * Constructs a new {@link AjaxDomEvent} based on the given
	 * {@link AjaxRequestMessage}.
	 *
	 * @param document
	 *            the associated {@link IDomDocument} that this event was
	 *            triggered on
	 * @param message
	 *            the {@link AjaxRequestMessage}, containing information about
	 *            this event
	 */
	public AjaxDomEvent(IDomDocument document, AjaxRequestMessage message) {

		this.document = document;
		this.message = message;
	}

	/**
	 * Returns the {@link AjaxRequestMessage} that this {@link AjaxDomEvent} is
	 * based on.
	 *
	 * @return associated {@link AjaxRequestMessage} (never <i>null</i>)
	 */
	public AjaxRequestMessage getRequestMessage() {

		return message;
	}

	@Override
	public DomEventType getType() {

		return message.getEventType();
	}

	@Override
	public IDomNode getCurrentTarget() {

		return message.getNode(document);
	}

	@Override
	public double getClientX() {

		return message.getCursor().getX();
	}

	@Override
	public double getClientY() {

		return message.getCursor().getY();
	}

	@Override
	public double getRelativeX() {

		return message.getCursorRelative().getX();
	}

	@Override
	public double getRelativeY() {

		return message.getCursorRelative().getY();
	}

	@Override
	public double getScrollX() {

		return message.getWindowPageOffset().getX();
	}

	@Override
	public double getScrollY() {

		return message.getWindowPageOffset().getY();
	}

	@Override
	public String getKey() {

		return message.getKey();
	}

	@Override
	public double getWindowWidth() {

		return message.getWindowInnerSize().getX();
	}

	@Override
	public double getWindowHeight() {

		return message.getWindowInnerSize().getY();
	}

	@Override
	public String getWindowSelection() {

		return message.getWindowSelection();
	}

	@Override
	public DomRect getBoundingClientRect() {

		return message.getNodeRect();
	}

	@Override
	public double getDeltaX() {

		return message.getWheelDelta().getX();
	}

	@Override
	public double getDeltaY() {

		return message.getWheelDelta().getY();
	}

	@Override
	public double getDeltaZ() {

		return message.getWheelDelta().getZ();
	}

	@Override
	public boolean isAltKey() {

		return message.isModifierKey(DomModifier.ALT);
	}

	@Override
	public boolean isCtrlKey() {

		return message.isModifierKey(DomModifier.CONTROL);
	}

	@Override
	public boolean isMetaKey() {

		return message.isModifierKey(DomModifier.META);
	}

	@Override
	public boolean isShiftKey() {

		return message.isModifierKey(DomModifier.SHIFT);
	}

	@Override
	public String toString() {

		return message.toString();
	}
}

package com.softicar.platform.dom.elements.testing.engine.document;

import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomRect;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.IDomNode;

/**
 * A dummy implementation of {@link IDomEvent} for testing.
 *
 * @author Oliver Richers
 */
public class DomTestEvent implements IDomEvent {

	private final IDomNode target;
	private final DomEventType eventType;

	public DomTestEvent(IDomNode target, DomEventType eventType) {

		this.target = target;
		this.eventType = eventType;
	}

	@Override
	public DomEventType getType() {

		return eventType;
	}

	@Override
	public IDomNode getCurrentTarget() {

		return target;
	}

	@Override
	public double getClientX() {

		return 0;
	}

	@Override
	public double getClientY() {

		return 0;
	}

	@Override
	public double getRelativeX() {

		return 0;
	}

	@Override
	public double getRelativeY() {

		return 0;
	}

	@Override
	public double getScrollX() {

		return 0;
	}

	@Override
	public double getScrollY() {

		return 0;
	}

	@Override
	public String getKey() {

		return "";
	}

	@Override
	public double getWindowWidth() {

		return 0;
	}

	@Override
	public double getWindowHeight() {

		return 0;
	}

	@Override
	public String getWindowSelection() {

		return "";
	}

	@Override
	public DomRect getBoundingClientRect() {

		return new DomRect();
	}

	@Override
	public double getDeltaX() {

		return 0;
	}

	@Override
	public double getDeltaY() {

		return 0;
	}

	@Override
	public double getDeltaZ() {

		return 0;
	}

	@Override
	public boolean isAltKey() {

		return false;
	}

	@Override
	public boolean isCtrlKey() {

		return false;
	}

	@Override
	public boolean isMetaKey() {

		return false;
	}

	@Override
	public boolean isShiftKey() {

		return false;
	}

	@Override
	public String toString() {

		return "DomTestEvent [target=" + target + ", eventType=" + eventType + "]";
	}
}

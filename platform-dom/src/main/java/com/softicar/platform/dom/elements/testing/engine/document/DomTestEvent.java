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
	public int getClientX() {

		return 0;
	}

	@Override
	public int getClientY() {

		return 0;
	}

	@Override
	public Double getRelativeX() {

		return null;
	}

	@Override
	public Double getRelativeY() {

		return null;
	}

	@Override
	public Double getScrollX() {

		return 0.0;
	}

	@Override
	public Double getScrollY() {

		return 0.0;
	}

	@Override
	public String getKey() {

		return "";
	}

	@Override
	public Integer getKeyCode() {

		return null;
	}

	@Override
	public int getWindowWidth() {

		return 0;
	}

	@Override
	public int getWindowHeight() {

		return 0;
	}

	@Override
	public DomRect getBoundingClientRect() {

		return new DomRect();
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
}

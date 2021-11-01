package com.softicar.platform.dom.elements.anchor;

import com.softicar.platform.dom.elements.DomAnchor;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.node.IDomNode;

/**
 * A hidden {@link DomAnchor} that can be used to open external URLs.
 *
 * @author Oliver Richers
 */
public class DomHiddenLinkAnchor extends DomAnchor {

	public DomHiddenLinkAnchor() {

		setTabIndex(-1);
	}

	@Override
	public DomHiddenLinkAnchor setHRef(String href) {

		super.setHRef(href);
		return this;
	}

	@Override
	public DomHiddenLinkAnchor setOpenInNewTab(boolean openInNewTab) {

		super.setOpenInNewTab(openInNewTab);
		return this;
	}

	public DomHiddenLinkAnchor enableEventDelegation(IDomNode triggerNode) {

		getDomEngine().setClickTargetForEventDelegation(triggerNode, DomEventType.CLICK, this);
		getDomEngine().setClickTargetForEventDelegation(triggerNode, DomEventType.ENTER, this);
		getDomEngine().setClickTargetForEventDelegation(triggerNode, DomEventType.SPACE, this);
		return this;
	}

	public DomHiddenLinkAnchor disableEventDelegation(IDomNode triggerNode) {

		getDomEngine().unsetClickTargetForEventDelegation(triggerNode, DomEventType.CLICK);
		getDomEngine().unsetClickTargetForEventDelegation(triggerNode, DomEventType.ENTER);
		getDomEngine().unsetClickTargetForEventDelegation(triggerNode, DomEventType.SPACE);
		return this;
	}
}

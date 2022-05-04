package com.softicar.platform.ajax.timeout;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.timeout.IDomTimeoutNode;

public class AjaxDomTimeoutEventTestDiv extends DomDiv implements IDomTimeoutNode {

	private boolean timeoutReceived;

	public AjaxDomTimeoutEventTestDiv() {

		this.timeoutReceived = false;
	}

	public boolean isTimeoutReceived() {

		return timeoutReceived;
	}

	@Override
	public void handleTimeout() {

		this.timeoutReceived = true;
	}
}

package com.softicar.platform.dom.input;

import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.node.IDomNode;

public interface IDomListeningNode extends IDomNode {

	default void listenToEvent(DomEventType type) {

		getDomEngine().listenToEvent(this, type);
	}

	default void unlistenToEvent(DomEventType type) {

		getDomEngine().unlistenToEvent(this, type);
	}
}

function handleDomEvent(event: Event) {
	sendOrDelegateEvent(event.currentTarget as HTMLElement, event, event.type);
}

function sendEventToServer(event: Event, eventType: string) {
	if(AJAX_REQUEST_LOCK.lock()) {
		let element = event.currentTarget as HTMLElement;
		let boundingRect = element.getBoundingClientRect();
		let message = new AjaxRequestMessage()
			.setAction(AJAX_REQUEST_DOM_EVENT)
			.setNode(element)
			.setEventType(eventType)
			.setWindowPageOffset(new Point(window.pageXOffset, window.pageYOffset))
			.setWindowInnerSize(new Point(window.innerWidth, window.innerHeight));
		
		if(event instanceof MouseEvent) {
			message.setMousePosition(new Point(event.clientX, event.clientY));
			message.setMouseRelativePosition(new Point(event.clientX - boundingRect.left, event.clientY - boundingRect.top));
		} else {
			message.setMousePosition(new Point(boundingRect.x + boundingRect.width / 2, boundingRect.y + boundingRect.height / 2));
		}

		if(event instanceof KeyboardEvent) {
			message.setKeyCode(event.keyCode);
		}

		if(event instanceof KeyboardEvent || event instanceof MouseEvent) {
			message.setModifierKey('altKey', event.altKey);
			message.setModifierKey('ctrlKey', event.ctrlKey);
			message.setModifierKey('metaKey', event.metaKey);
			message.setModifierKey('shiftKey', event.shiftKey);
		}

		new AjaxRequest(message).send();
	} else {
		alert(LOCK_MESSAGE);
	}
}

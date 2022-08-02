	
function listenToDomEvent(nodeId: number, event: string, doListen: boolean) {
	let element = AJAX_ENGINE.getElement(nodeId);
	if(element == null)
		return;

	let handler = doListen? handleDomEvent : null;

	switch(event)
	{
	case 'CLICK':       element.onclick = handler; break;
	case 'CHANGE':      CHANGE_EVENT_MANAGER.setListenToChangeEvent(element, doListen); break;
	case 'CONTEXTMENU': element.oncontextmenu = doListen? (event => {handleDomEvent(event); event.preventDefault();}) : null; break;
	case 'DBLCLICK':    element.ondblclick = handler; break;
	case 'ENTER':       KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
	case 'ESCAPE':      KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
	case 'INPUT':       element.oninput = handler; break;
	case 'KEYDOWN':     KEYBOARD_EVENT_MANAGER.setListenToKeyDown(element, doListen); break;
	case 'KEYUP':       KEYBOARD_EVENT_MANAGER.setListenToKeyUp(element, doListen); break;
	case 'SPACE':       KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
	case 'TAB':         KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
	default: alert('Unknown event ' + event + '.');
	}
}

function setListenToKeys(element: HTMLElement, keys: string[]) {
	KEYBOARD_EVENT_MANAGER.setListenToKeys(element, keys);
}

function handleDomEvent(event: Event) {
	sendOrDelegateEvent(event.currentTarget as HTMLElement, event, event.type);
}

function sendDomEventToServer(event: Event, eventType: string) {
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
		message.setKey(event.key);
		message.setKeyCode(event.keyCode);
	}

	if(event instanceof KeyboardEvent || event instanceof MouseEvent) {
		message.setModifierKey('altKey', event.altKey);
		message.setModifierKey('ctrlKey', event.ctrlKey);
		message.setModifierKey('metaKey', event.metaKey);
		message.setModifierKey('shiftKey', event.shiftKey);
	}

	AJAX_REQUEST_QUEUE.submit(message);
}

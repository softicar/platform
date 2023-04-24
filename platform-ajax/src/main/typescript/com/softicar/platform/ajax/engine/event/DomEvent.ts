
function listenToDomEvent(nodeId: number, event: string, doListen: boolean) {
	let element = AJAX_ENGINE.getElement(nodeId);
	if(element == null)
		return;

	let handler = doListen? handleDomEvent : null;

	switch(event)
	{
	case 'BLUR':        element.onblur = handler; break;
	case 'CLICK':       element.onclick = handler; break;
	case 'CHANGE':      element.onchange = handler; break;
	case 'CONTEXTMENU': element.oncontextmenu = doListen? (event => {handleDomEvent(event); event.preventDefault();}) : null; break;
	case 'DBLCLICK':    element.ondblclick = handler; break;
	case 'ENTER':       KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
	case 'ESCAPE':      KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
	case 'FOCUS':       element.onfocus = handler; break;
	case 'INPUT':       element.oninput = handler; break;
	case 'KEYDOWN':     KEYBOARD_EVENT_MANAGER.setListenToKeyDown(element, doListen); break;
	case 'KEYUP':       KEYBOARD_EVENT_MANAGER.setListenToKeyUp(element, doListen); break;
	case 'SPACE':       KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
	case 'TAB':         KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
	case 'WHEEL':       WHEEL_EVENT_MANAGER.setListenToWheel(element, doListen); break;
	default: alert('Unknown event: ' + event);
	}
}

function setPreventDefaultOnMouseDown(element: HTMLElement, enabled: boolean) {
	element.onmousedown = enabled? (event) => event.preventDefault() : null;
}

function setPreventDefaultOnWheel(element: HTMLElement, modifiers: string[], enabled: boolean) {
	WHEEL_EVENT_MANAGER.setPreventDefaultBehavior(element, new Set(modifiers), enabled);
}

function setListenToKeys(element: HTMLElement, keys: string[]) {
	KEYBOARD_EVENT_MANAGER.setListenToKeys(element, keys);
}

function setHeightAndWidthToComputedValues(node: HTMLElement) {
	setTimeout(() => {
		let computedStyle = window.getComputedStyle(node);
		node.style.height = computedStyle.height;
		node.style.width = computedStyle.width;
	});
}

function setHeightAndWidthOnLoad(image: HTMLImageElement, targetNode: HTMLElement) {
	if(image.complete) {
		setHeightAndWidthToComputedValues(targetNode);
	} else {
		image.addEventListener('load', _ => setHeightAndWidthToComputedValues(targetNode));
	}
}

function handleDomEvent(event: Event) {
	sendOrDelegateEvent(event.currentTarget as HTMLElement, event, event.type);
}

function sendDomEventToServer(event: Event, eventType: string) {
	let element = event.currentTarget as HTMLElement;
	let boundingRect = element.getBoundingClientRect();
	let message = new AjaxRequestMessage()
		.setActionType(AJAX_REQUEST_DOM_EVENT)
		.setNode(element)
		.setEventType(eventType)
		.setWindowPageOffset(new Vector2d(window.pageXOffset, window.pageYOffset))
		.setWindowInnerSize(new Vector2d(window.innerWidth, window.innerHeight))
		.setWindowSelection()
		.setNodeRect(boundingRect);
	
	if(event instanceof MouseEvent) {
		message.setMousePosition(new Vector2d(event.clientX, event.clientY));
		message.setMouseRelativePosition(new Vector2d(event.clientX - boundingRect.left, event.clientY - boundingRect.top));
	} else {
		message.setMousePosition(new Vector2d(boundingRect.x + boundingRect.width / 2, boundingRect.y + boundingRect.height / 2));
	}

	if(event instanceof KeyboardEvent) {
		message.setKey(event.key);
	}

	if(event instanceof KeyboardEvent || event instanceof MouseEvent) {
		message.setModifierKey(DOM_MODIFIER_ALT, event.altKey);
		message.setModifierKey(DOM_MODIFIER_CONTROL, event.ctrlKey);
		message.setModifierKey(DOM_MODIFIER_META, event.metaKey);
		message.setModifierKey(DOM_MODIFIER_SHIFT, event.shiftKey);
	}

	if(event instanceof WheelEvent) {
		message.setWheelDelta(new Vector3d(event.deltaX, event.deltaY, event.deltaZ));
	}

	AJAX_REQUEST_QUEUE.submit(message);
}

function handleDomEvent(event: Event) {
	sendOrDelegateEvent(event.currentTarget as HTMLElement, event, event.type);
}

function sendEventToServer(event: Event, eventType: string) {
	if(AJAX_REQUEST_LOCK.lock()) {
		let element = event.currentTarget as HTMLElement;
		let boundingRect = element.getBoundingClientRect();
		let parameters: any = {
			'a' : AJAX_REQUEST_DOM_EVENT,
			'n' : element.id,
			'e' : eventType,
			'sx': window.pageXOffset,
			'sy': window.pageYOffset,
			'ww': window.innerWidth,
			'wh': window.innerHeight,
		};
		
		if(event instanceof MouseEvent) {
			parameters['cx'] = event.clientX;
			parameters['cy'] = event.clientY;
			parameters['rx'] = event.clientX - boundingRect.left;
			parameters['ry'] = event.clientY - boundingRect.top;
		} else {
			parameters['cx'] = boundingRect.x + boundingRect.width / 2;
			parameters['cy'] = boundingRect.y + boundingRect.height / 2;
		}
		
		if(event instanceof KeyboardEvent) {
			parameters['k'] = event.keyCode;
		}

		if(event instanceof KeyboardEvent || event instanceof MouseEvent) {
			addOptionalFlag(parameters, 'altKey', event.altKey);
			addOptionalFlag(parameters, 'ctrlKey', event.ctrlKey);
			addOptionalFlag(parameters, 'metaKey', event.metaKey);
			addOptionalFlag(parameters, 'shiftKey', event.shiftKey);
		}

		VALUE_NODE_MAP.copyNodeValues(parameters);
		ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
		ACTION_QUEUE.executeNextAction();
	} else {
		alert(LOCK_MESSAGE);
	}
}

function addOptionalFlag(parameters: any, name: string, flag: boolean) {
	if(flag) {
		parameters[name] = 1;
	}
}

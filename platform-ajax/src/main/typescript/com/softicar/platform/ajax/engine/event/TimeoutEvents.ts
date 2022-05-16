/**
 * Schedules a timeout event on the given HTML element.
 */
function scheduleTimeout(timeoutNode: HTMLElement, milliseconds: number) {
	setTimeout(() => handleTimeout(timeoutNode), milliseconds);
}

/**
 * Handles a schedule timeout event on the given HTML element.
 */
function handleTimeout(timeoutNode: HTMLElement) {
	if(AJAX_REQUEST_LOCK.lock()) {
		let message = new AjaxRequestMessage()
			.setAction(AJAX_REQUEST_TIMEOUT)
			.setNode(timeoutNode);
		new AjaxRequest(message).send();
	} else {
		scheduleTimeout(timeoutNode, TIMEOUT_RETRY_DELAY);
	}
}

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
	let message = new AjaxRequestMessage()
		.setActionType(AJAX_REQUEST_TIMEOUT)
		.setNode(timeoutNode);
	AJAX_REQUEST_QUEUE.submit(message);
}

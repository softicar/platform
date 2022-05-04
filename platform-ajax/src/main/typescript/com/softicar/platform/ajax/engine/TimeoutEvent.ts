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
		let parameters = {
			a: AJAX_REQUEST_TIMEOUT,
			n: timeoutNode.id
		};
		GLOBAL.copyNodeValues(parameters);

		ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
		ACTION_QUEUE.executeNextAction();
	} else {
		scheduleTimeout(timeoutNode, TIMEOUT_RETRY_DELAY);
	}
}

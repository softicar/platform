/**
 * Sends an AJAX upload request through the given form element.
 */
function sendUploadRequestThroughForm(form: HTMLFormElement) {
	if(AJAX_REQUEST_LOCK.lock()) {
		let parameters = {
			a: AJAX_REQUEST_UPLOAD,
			n: form.id
		};
		VALUE_NODE_MAP.copyNodeValues(parameters);
		ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters, form));
		ACTION_QUEUE.executeNextAction();
	} else {
		alert(LOCK_MESSAGE);
	}
}

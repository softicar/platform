/**
 * Sends an AJAX upload request through the given form element.
 */
function sendUploadRequestThroughForm(form: HTMLFormElement) {
	if(AJAX_REQUEST_LOCK.lock()) {
		let message = new AjaxRequestMessage()
			.setAction(AJAX_REQUEST_UPLOAD)
			.setNode(form);
		new AjaxRequest(message, form).send();
	} else {
		alert(LOCK_MESSAGE);
	}
}

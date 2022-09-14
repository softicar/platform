/**
 * Sends an AJAX upload request through the given form element.
 */
function sendUploadRequestThroughForm(form: HTMLFormElement) {
	let message = new AjaxRequestMessage()
		.setActionType(AJAX_REQUEST_UPLOAD)
		.setNode(form);
	AJAX_REQUEST_QUEUE.submit(message, form);
}

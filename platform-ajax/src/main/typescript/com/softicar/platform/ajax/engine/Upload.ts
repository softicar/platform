/**
 * Sends an AJAX upload request through the given form element.
 */
function sendUploadRequestThroughForm(form: HTMLFormElement) {
	let formRect = form.getBoundingClientRect();
	let message = new AjaxRequestMessage()
		.setActionType(AJAX_REQUEST_UPLOAD)
		.setNode(form)
		.setMousePosition(new Vector2d(formRect.x, formRect.y))
		.setWindowPageOffset(new Vector2d(window.pageXOffset, window.pageYOffset))
		.setWindowInnerSize(new Vector2d(window.innerWidth, window.innerHeight));
	AJAX_REQUEST_QUEUE.submit(message, form);
}

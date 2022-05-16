
function AutoCompleteRequest(inputContext, pattern, callback) {

	this.send = send;

	function send() {

		let message = new AjaxRequestMessage()
			.setAction(AJAX_REQUEST_AUTO_COMPLETE)
			.setNode(inputContext.getInput())
			.setAutoCompletePattern(pattern);
		new HttpRequest()
			.setMessage(message.encode())
			.sendAsync(handleResponse);
	}

	function getPattern() {

		return pattern;
	}

	function handleResponse(request) {

		if(request.status == HTTP_STATUS_SUCCESS && request.responseText) {
			callback(pattern, eval(request.responseText));
		}
	}
}
